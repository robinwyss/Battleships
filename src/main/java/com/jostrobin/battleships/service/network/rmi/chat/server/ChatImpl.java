package com.jostrobin.battleships.service.network.rmi.chat.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.listener.ChatListener;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:20
 */
public class ChatImpl extends UnicastRemoteObject implements Chat
{
    private static final Logger logger = LoggerFactory.getLogger(ChatImpl.class);
    
    private List<ChatListener> listeners = new ArrayList<ChatListener>();

    public ChatImpl() throws RemoteException
    {
        super();
    }

    public boolean isAvailable() throws RemoteException
    {
        return true;
    }
    
    public void addListener(ChatListener listener)
    {
    	listeners.add(listener);
    }

    public void sendMessage(String username, String message) throws RemoteException
    {
    	logger.trace(username + ": " + message);
    	for (ChatListener listener : listeners)
    	{
    		listener.receiveMessage(username, message);
    	}
    }
}
