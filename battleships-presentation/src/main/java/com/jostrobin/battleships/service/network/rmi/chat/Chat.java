package com.jostrobin.battleships.service.network.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:16
 */
public interface Chat extends Remote
{
    /**
     * Verifies if the host can receive messages.
     *
     * @return
     * @throws RemoteException
     */
    boolean isAvailable() throws RemoteException;

    /**
     * Sends a message to the host
     *
     * @param username
     * @param message
     * @throws RemoteException
     * @throws IllegalStateException when the host can not receive messages
     */
    void sendMessage(String username, String message) throws RemoteException, IllegalStateException;

}
