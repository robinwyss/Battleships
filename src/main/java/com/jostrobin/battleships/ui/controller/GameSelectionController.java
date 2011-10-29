package com.jostrobin.battleships.ui.controller;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.service.network.rmi.GameState;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;

public class GameSelectionController implements ServerDetectionListener
{
	private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);
	
	private ServerDetectionManager serverDetectionManager;
	
	private GameSelectionFrame gameSelectionFrame;
	
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
		ApplicationInterface applicationInterface;

		try
		{
			Registry registry = LocateRegistry.getRegistry(address.getHostName());
			applicationInterface = (ApplicationInterface) registry.lookup("ApplicationInterface");
			GameState state = applicationInterface.getGameState();
			
			Object[] row = new Object[4];
			row[0] = state.getUsername();
			gameSelectionFrame.addServer(row);
		}
		catch (RemoteException e)
		{
			logger.error("Failed to connect to the server at {}", address, e);
		}
		catch (NotBoundException e)
		{
			logger.error("Failed to connect to the server at {}", address, e);
		}
	}

	public GameSelectionFrame getGameSelectionFrame()
	{
		return gameSelectionFrame;
	}

	public void setGameSelectionFrame(GameSelectionFrame gameSelectionFrame)
	{
		this.gameSelectionFrame = gameSelectionFrame;
	}
}
