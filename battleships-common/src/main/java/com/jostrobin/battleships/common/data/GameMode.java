package com.jostrobin.battleships.common.data;

public enum GameMode
{
	CLASSIC("Classic"),
	HARDCORE("Hardcore"),
	CUSTOM("Custom");
	
	private String text;
	
	private GameMode(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}

	public static GameMode fromString(String mode)
	{
		for (GameMode m : GameMode.values())
		{
			if (m.name().equals(mode))
			{
				return m;
			}
		}
		return null;
	}
}
