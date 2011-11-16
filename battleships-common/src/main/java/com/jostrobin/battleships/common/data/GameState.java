package com.jostrobin.battleships.common.data;

/**
 * The diferent states a client/game can have.
 * @author joscht
 *
 */
public enum GameState
{
	/**
	 * There is a new client, but we no nothing about him so far (no username etc)
	 */
	INIT,
	
	/**
	 * There is a new client, registered with his username.
	 */
	NEW,
	
	/**
	 * A client has created a new game and is waiting for players.
	 */
	WAITING_FOR_PLAYERS,
	
	/**
	 * A client is currently running a game.
	 */
	RUNNING;

	public static GameState fromString(String mode)
	{
		for (GameState m : GameState.values())
		{
			if (m.name().equals(mode))
			{
				return m;
			}
		}
		return null;
	}
}
