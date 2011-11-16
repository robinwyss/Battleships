package com.jostrobin.battleships;
import java.net.InetAddress;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.controller.ServerDetectionController;
import com.jostrobin.battleships.view.frames.ServerDetectionFrame;


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
		
		// connect to the specified server. as this is for debug purposes only, we can stop the application if theres an error
		if (ip != null)
		{
			InetAddress address;
			try
			{
				address = InetAddress.getByName(ip);
				new ApplicationController(address);
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
			// start the detection controller in its own thread to wait for server answers
			// the server detection controller will start the application controller once a server is ready
			ServerDetectionController controller = new ServerDetectionController();
			controller.addObserver(new ServerDetectionFrame(controller));
			Thread detectionThread = new Thread(controller);
			detectionThread.start();
			
			controller.broadcastFindServer();
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
            logger.warn("Could not set system look and fell", e);
        }
    }
}
