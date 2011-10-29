package com.jostrobin.battleships.service.network.rmi;

import java.io.Serializable;

public class GameState implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private int currentPlayers;
	
	private int maxPlayers;

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
