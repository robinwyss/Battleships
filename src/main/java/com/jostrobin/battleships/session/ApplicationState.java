package com.jostrobin.battleships.session;

import java.io.Serializable;

import com.jostrobin.battleships.enumerations.State;


public class ApplicationState implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final ApplicationState applicationState = new ApplicationState();
	
	private String username;
	
	private int currentPlayers;
	
	private int maxPlayers;
	
	private State state = State.NEW;
	
	private boolean debug;

    private ApplicationState()
    {
    }

    public static ApplicationState getInstance()
    {
        return applicationState;
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

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public boolean isDebug()
	{
		return debug;
	}

	public void setDebug(boolean debug)
	{
		this.debug = debug;
	}
}
