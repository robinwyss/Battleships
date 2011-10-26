package com.jostrobin.battleships;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.service.network.rmi.chat.Client.ChatClient;
import com.jostrobin.battleships.service.network.rmi.chat.server.ChatServer;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class App {
    public static void main(String... args) throws Exception {
        new ChatServer();

        new ChatClient().sendMessage("Hello");
        
        
        List<ServerDetectionListener> listeners = new ArrayList<ServerDetectionListener>();
        listeners.add(new ServerDetectionListener()
		{
			@Override
			public void addServer(InetAddress address)
			{
				System.out.println("Found server at " + address);
			}
		});
        ServerDetectionManager detection = new ServerDetectionManager(listeners);
        detection.findGames();
    }

}
