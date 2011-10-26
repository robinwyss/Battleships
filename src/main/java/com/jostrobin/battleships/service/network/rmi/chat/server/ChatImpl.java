package com.jostrobin.battleships.service.network.rmi.chat.server;

import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:20
 */
public class ChatImpl extends UnicastRemoteObject implements Chat {
    private static final Logger logger = LoggerFactory.getLogger(ChatImpl.class);

    protected ChatImpl() throws RemoteException {
        super();
    }

    public boolean isAvailable() throws RemoteException {
        return true;
    }

    public void sendMessage(String username, String message) throws RemoteException {
        logger.debug(username + ": " + message);
    }
}
