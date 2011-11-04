package com.jostrobin.battleships.service.network.rmi.chat.server;

import java.net.InetAddress;

/**
 * Used to transmit detected servers in the network to the listeners. Implementors of this listener must be aware that
 * the methods
 * will be called concurrently. The implementor must handle concurrency issues.
 *
 * @author joscht
 */
public interface ServerDetectionListener
{
	/**
	 * Called when a new server has been found in the network.
	 *
	 * @param address The address of the server. At this address the RMI registry should be available.
	 */
	public void addServer(InetAddress address);
	
	/**
	 * Called when a server has new information which we need to fetch.
	 * @param address
	 */
	public void updateServer(InetAddress address);
}
