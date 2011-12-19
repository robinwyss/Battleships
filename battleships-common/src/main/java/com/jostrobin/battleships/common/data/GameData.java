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
	
	private int nrOfAircraftCarriers;
	
	private int nrOfDestroyers;
	
	private int nrOfBattleships;
	
	private int nrOfSubmarines;
	
	private int nrOfPatrolBoats;

	public GameData(Long id, GameMode mode, int maxPlayers, int fieldWidth,
			int fieldLength)
	{
		this.id = id;
		this.mode = mode;
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

	public int getNrOfAircraftCarriers()
	{
		return nrOfAircraftCarriers;
	}

	public void setNrOfAircraftCarriers(int nrOfAircraftCarriers)
	{
		this.nrOfAircraftCarriers = nrOfAircraftCarriers;
	}

	public int getNrOfDestroyers()
	{
		return nrOfDestroyers;
	}

	public void setNrOfDestroyers(int nrOfDestroyers)
	{
		this.nrOfDestroyers = nrOfDestroyers;
	}

	public int getNrOfBattleships()
	{
		return nrOfBattleships;
	}

	public void setNrOfBattleships(int nrOfBattleships)
	{
		this.nrOfBattleships = nrOfBattleships;
	}

	public int getNrOfSubmarines()
	{
		return nrOfSubmarines;
	}

	public void setNrOfSubmarines(int nrOfSubmarines)
	{
		this.nrOfSubmarines = nrOfSubmarines;
	}

	public int getNrOfPatrolBoats()
	{
		return nrOfPatrolBoats;
	}

	public void setNrOfPatrolBoats(int nrOfPatrolBoats)
	{
		this.nrOfPatrolBoats = nrOfPatrolBoats;
	}

}
