package com.jostrobin.battleships.service.network.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.session.ApplicationState;

public class DefaultApplicationInterface extends UnicastRemoteObject implements ApplicationInterface, Serializable
{
	private static final long serialVersionUID = 1L;
	
    protected DefaultApplicationInterface() throws RemoteException
	{
		super();
	}

    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationInterface.class);

    @Override
    public ApplicationState getApplicationState()
    {
        ApplicationState state = ApplicationState.getInstance();
        return state;
    }

	@Override
	public GameSettings joinGame() throws RemoteException
	{
		ApplicationState state = ApplicationState.getInstance();
		GameSettings settings = state.getSettings();
		if (settings.getCurrentNumberOfPlayers() < settings.getNumberOfPlayers())
		{
			// add the client
			settings.setCurrentNumberOfPlayers(settings.getCurrentNumberOfPlayers()+1);
			
			// broadcast about the new player
			state.getServerDetectionManager().broadcastUpdate();
			
			return settings;
		}
		return null;
	}
}
