package com.jostrobin.battleships;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.service.network.rmi.RmiService;
import com.jostrobin.battleships.service.network.rmi.chat.server.ChatServer;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;
import com.jostrobin.battleships.ui.controller.RegistrationController;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class ApplicationController
{

    private RmiService rmiService;

    private void initializeApplication()
    {
        rmiService = new RmiService();
    }


    public static void main(String... args) throws Exception
    {

        new RegistrationController().showRegistrationDialog();


        new ChatServer();


        List<ServerDetectionListener> listeners = new ArrayList<ServerDetectionListener>();
        listeners.add(new ServerDetectionListener()
        {
            @Override
            public void addServer(InetAddress address)
            {
                System.out.println("Found server at " + address.getHostName());
            }
        });
        ServerDetectionManager detection = new ServerDetectionManager(listeners);
        Thread thread = new Thread(detection);
        thread.start();
        detection.sendBroadcast();
    }

}
