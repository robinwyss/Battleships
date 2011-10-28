package com.jostrobin.battleships.service.network.rmi.chat.Client;

import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:30
 */
public class ChatClient
{
	private Logger logger = LoggerFactory.getLogger(ChatClient.class);

	Chat chat;

	public ChatClient(String hostname)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry(hostname);
			chat = (Chat) registry.lookup("Chat");

		} catch (RemoteException e)
		{
			logger.error("Failed to initialize chat client", e);
		} catch (NotBoundException e)
		{
			logger.error("Failed to initialize chat client", e);
		}
	}

	public void sendMessage(String message)
	{
		try
		{
			chat.sendMessage("Robin", message);
		} catch (RemoteException e)
		{
			logger.error("Could not send message", e);
		}
	}
}
