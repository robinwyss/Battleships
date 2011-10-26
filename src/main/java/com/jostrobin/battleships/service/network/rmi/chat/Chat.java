package com.jostrobin.battleships.service.network.rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:16
 */
public interface Chat extends Remote {
    boolean isAvailable() throws RemoteException;

    void sendMessage(String username, String message) throws RemoteException;

}
