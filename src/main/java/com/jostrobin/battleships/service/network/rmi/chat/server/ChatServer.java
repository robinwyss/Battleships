package com.jostrobin.battleships.service.network.rmi.chat.server;

import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:25
 */
public class ChatServer
{
	private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

	private Chat chat;

	public ChatServer()
	{
		try
		{
			chat = new ChatImpl();
			Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.bind("Chat", new ChatImpl());
			logger.debug("Initialized Chat Server");

		} catch (RemoteException e)
		{
			logger.error("Failed to initialize chat server", e);
		} catch (AlreadyBoundException e)
		{
			logger.error("Failed to initialize chat server", e);
		}
	}
}
