package com.jostrobin.battleships.data;

public class Configuration
{
	private static final Configuration configuration = new Configuration();
	
	private String id;
	
	public static Configuration getInstance()
	{
		return configuration;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
