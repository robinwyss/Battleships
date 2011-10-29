package com.jostrobin.battleships.service.network.rmi;

import java.rmi.Remote;

public interface ApplicationInterface extends Remote
{
	/**
	 * Returns the current state of the game.
	 * @return
	 */
	public GameState getGameState();
}
