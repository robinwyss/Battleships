package com.jostrobin.battleships.common.data;

public enum GameMode
{
	CLASSIC,
	HARDCORE,
	CUSTOM;
	
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
