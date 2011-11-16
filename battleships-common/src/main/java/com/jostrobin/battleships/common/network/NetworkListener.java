package com.jostrobin.battleships.common.network;


/**
 * A NetworkListener will be notified about messages coming from the network.
 * @author joscht
 *
 */
public interface NetworkListener
{
	/**
	 * Called when a command arrives via network. 
	 * @param command The parsed command or null if there was an error in the network connection.
	 */
	public void notify(Command command);
}
