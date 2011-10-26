package com.jostrobin.battleships.service.network.rmi.game;

import com.jostrobin.battleships.data.Cell;
import com.jostrobin.battleships.data.Game;
import com.jostrobin.battleships.data.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 22:31
 */
public interface GameCommunication extends Remote
{
	/**
	 * Attacks the provided play on the specified field. If there is no game running between the caller and the
	 * receiver
	 * of the method an IllegalStateException is thrown.
	 *
	 * @param cell
	 * @param player
	 * @throws RemoteException
	 * @throws IllegalStateException
	 */
	HitType attack(Cell cell, Player player) throws RemoteException, IllegalStateException;

	/**
	 * Returns the game, if the host on which the method is invoked has created one, otherwise returns null.
	 *
	 * @return the game, if there is one or null otherwise
	 * @throws RemoteException
	 */
	Game findGame() throws RemoteException;

	/**
	 * The caller resigns from the current game, if there is no game between the two hosts an IllegalStateException is
	 * thrown.
	 *
	 * @throws RemoteException
	 * @throws IllegalStateException
	 */
	void abortGame() throws RemoteException, IllegalStateException;
}
