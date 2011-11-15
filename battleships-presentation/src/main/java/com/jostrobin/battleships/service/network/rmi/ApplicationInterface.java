package com.jostrobin.battleships.service.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.session.ApplicationState;

public interface ApplicationInterface extends Remote
{
    /**
     * Returns the current state of the game.
     *
     * @return
     */
    public ApplicationState getApplicationState() throws RemoteException;

    /**
     * Tries to join a game. If the remote client is allowed to join, returns the GameSettings, null if he's not allowed
     * (game full for example).
     *
     * @param identification The identification of the remote client.
     * @return
     * @throws RemoteException
     */
    public GameSettings joinGame(String id) throws RemoteException;
}
