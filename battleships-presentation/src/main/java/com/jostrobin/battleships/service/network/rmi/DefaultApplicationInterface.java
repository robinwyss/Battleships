package com.jostrobin.battleships.service.network.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.data.enums.State;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import com.jostrobin.battleships.session.ApplicationState;
import com.jostrobin.battleships.view.controller.UIController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultApplicationInterface extends UnicastRemoteObject implements ApplicationInterface, Serializable
{
    private static final long serialVersionUID = 1L;

    public DefaultApplicationInterface() throws RemoteException
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
    public GameSettings joinGame(String id) throws RemoteException
    {
        ApplicationState state = ApplicationState.getInstance();
        GameSettings settings = state.getSettings();
        if (settings.getCurrentNumberOfPlayers() < settings.getNumberOfPlayers())
        {
            // add the client
            settings.setCurrentNumberOfPlayers(settings.getCurrentNumberOfPlayers() + 1);

            // if there are enough players for this game, start up the screens
            if (settings.getCurrentNumberOfPlayers() == settings.getNumberOfPlayers())
            {
                state.setState(State.RUNNING);

                RmiManager manager = RmiManager.getInstance();
                ApplicationInterface applicationInterface = manager.getApplicationInterface(id);
                ServerInformation server = manager.getServerById(id);
                Chat chatClient = manager.findChat(server.getAddress(), id);

                UIController.getInstance().showGameFrame(chatClient, applicationInterface);
            }

            // broadcast about the new player
            state.getServerDetectionManager().broadcastUpdate();

            return settings;
        }
        return null;
    }
}
