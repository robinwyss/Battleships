package com.jostrobin.battleships.ui.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;

public class GameSelectionController implements ServerDetectionListener
{
	private ServerDetectionManager serverDetectionManager;
	
	public GameSelectionController()
	{
		List<ServerDetectionListener> listeners = new ArrayList<ServerDetectionListener>();
		listeners.add(this);
		
		serverDetectionManager = new ServerDetectionManager(listeners);
		
		Thread thread = new Thread(serverDetectionManager);
		thread.start();
	}
	
	public void refresh()
	{
		serverDetectionManager.sendBroadcast();
	}
	
	public void exit()
	{
		System.exit(0);
	}

	@Override
	public void addServer(InetAddress address)
	{
		System.out.println("Server found at " + address);
	}
}
