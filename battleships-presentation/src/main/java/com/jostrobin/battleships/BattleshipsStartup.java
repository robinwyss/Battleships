package com.jostrobin.battleships;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jostrobin.battleships.controller.ServerDetectionController;
import com.jostrobin.battleships.server.BattleshipsServer;


public class BattleshipsStartup
{
    private static final Logger logger = LoggerFactory.getLogger(BattleshipsStartup.class);

    public static void main(String... args)
    {
        setLookAndFeel();
        // if an ip is given, use this one
        String ip = null;
        for (String arg : args)
        {
            if (arg.startsWith("ip"))
            {
                ip = arg.substring("ip=".length());
            }
        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        ServerDetectionController serverDetectionController = applicationContext.getBean(ServerDetectionController.class);

        // connect to the specified server. as this is for debug purposes only, we can stop the application if theres an error
        if (ip != null)
        {
            InetAddress address;
            try
            {
                address = InetAddress.getByName(ip);
                serverDetectionController.startApplication(address);
            }
            catch (Exception e)
            {
                logger.error("Could not connect", e);
                System.exit(1);
            }
        }
        // send a udp broadcast to find a server
        else
        {
            serverDetectionController.init();
            // start the detection controller in its own thread to wait for server answers
            // the server detection controller will start the application controller once a server is ready
            serverDetectionController.addObserver(new ServerObserver(serverDetectionController));
            logger.info("Send Broadcast...");
            Thread detectionThread = new Thread(serverDetectionController);
            detectionThread.start();

            serverDetectionController.broadcastFindServer();
        }
    }

    private static void setLookAndFeel()
    {
        // set the name of the application menu item
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Battleships");

        // set the look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static class ServerObserver implements Observer
    {

        private static final int TIMEOUT = 3000;
        private ServerDetectionController serverDetectionController;
        private boolean serverFound;

        private ServerObserver(final ServerDetectionController serverDetectionController)
        {
            this.serverDetectionController = serverDetectionController;
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!serverFound)
                    {
                        logger.info("No server has been found, the client will start a new server");
                        BattleshipsServer.startNewServer();
                        serverDetectionController.broadcastFindServer();
                    }
                }
            }, TIMEOUT);
        }

        @Override
        public void update(Observable observable, Object o)
        {
            if (observable instanceof ServerDetectionController && o instanceof InetAddress)
            {
                // we found a server
                serverFound = true;
                serverDetectionController.startApplication((InetAddress) o);

            }
        }
    }
}
