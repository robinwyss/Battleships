package com.jostrobin.battleships.service.network.rmi.chat.Client;

import java.net.InetAddress;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.service.network.rmi.RmiManager;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 22:30
 */
public class ChatClient
{
	private Logger logger = LoggerFactory.getLogger(ChatClient.class);

	Chat chat;

	/**
	 * 
	 * @param address The remote address from where we get rmi objects.
	 * @param id The id of the remote server.
	 */
	public ChatClient(InetAddress address, String id)
	{
		RmiManager manager = RmiManager.getInstance();
		chat = manager.findChat(address, id);
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
