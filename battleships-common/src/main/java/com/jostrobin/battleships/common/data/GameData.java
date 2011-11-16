package com.jostrobin.battleships.common.data;

/**
 * Contains the data which define a game.
 * @author joscht
 *
 */
public class GameData
{
	private Long id;
	
	private GameMode mode;
	
	private int currentPlayers;
	
	private int maxPlayers;
	
	private int fieldWidth;
	
	private int fieldLength;

	public GameData(Long id, GameMode mode, int currentPlayers, int maxPlayers, int fieldWidth,
			int fieldLength)
	{
		this.id = id;
		this.mode = mode;
		this.currentPlayers = currentPlayers;
		this.maxPlayers = maxPlayers;
		this.fieldWidth = fieldWidth;
		this.fieldLength = fieldLength;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public GameMode getMode()
	{
		return mode;
	}

	public void setMode(GameMode mode)
	{
		this.mode = mode;
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

	public int getFieldWidth()
	{
		return fieldWidth;
	}

	public void setFieldWidth(int fieldWidth)
	{
		this.fieldWidth = fieldWidth;
	}

	public int getFieldLength()
	{
		return fieldLength;
	}

	public void setFieldLength(int fieldLength)
	{
		this.fieldLength = fieldLength;
	}

}
