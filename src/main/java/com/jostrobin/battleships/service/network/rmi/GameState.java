package com.jostrobin.battleships.service.network.rmi;

import java.io.Serializable;

public class GameState implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private static final GameState gameState = new GameState();
	
	private String username;
	
	private int currentPlayers;
	
	private int maxPlayers;
	
	private String state = "new";
	
	private GameState()
	{
	}
	
	public static GameState getInstance()
	{
		return gameState;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public int getCurrentPlayers()
	{
		return currentPlayers;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public void setCurrentPlayers(int currentPlayers)
	{
		this.currentPlayers = currentPlayers;
	}

	public int getMaxPlayers()
	{
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers)
	{
		this.maxPlayers = maxPlayers;
	}
}
