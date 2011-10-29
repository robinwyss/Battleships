package com.jostrobin.battleships.service.network.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.session.ApplicationState;

public class DefaultApplicationInterface implements ApplicationInterface
{
	private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationInterface.class);
	
	public DefaultApplicationInterface()
	{
		try
		{
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.bind("ApplicationInterface", this);
			logger.trace("Exported ApplicationInterface to RMI registry");

		}
		catch (RemoteException e)
		{
			logger.error("Failed to initialize ApplicationInterface", e);
		}
		catch (AlreadyBoundException e)
		{
			logger.error("Failed to initialize ApplicationInterface", e);
		}
	}
	
	@Override
	public GameState getGameState()
	{
		ApplicationState state = ApplicationState.getInstance();
		GameState gameState = new GameState();
		gameState.setUsername(state.getUsername());
		return gameState;
	}
}
