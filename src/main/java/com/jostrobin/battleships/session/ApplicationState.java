package com.jostrobin.battleships.session;

public class ApplicationState
{
	private static final ApplicationState applicationState = new ApplicationState();
	
	private String username = "joscht";

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

}
