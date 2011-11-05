package com.jostrobin.battleships.service.network.rmi;

import java.rmi.Remote;

import com.jostrobin.battleships.session.ApplicationState;

public interface ApplicationInterface extends Remote
{
	/**
	 * Returns the current state of the game.
	 * @return
	 */
	public ApplicationState getApplicationState();
}
