package com.jostrobin.battleships.data;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.jostrobin.battleships.data.enums.ShipType;
import com.jostrobin.battleships.enumerations.GameMode;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:31
 */
public class GameSettings implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Map<ShipType, Integer> boats = new TreeMap<ShipType, Integer>();
	
	private int fieldWidth;
	
	private int fieldHeight;
	
	private boolean canMove;
	
	private int currentNumberOfPlayers;
	
	private int numberOfPlayers;
	
	private GameMode mode;

	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers)
	{
		this.numberOfPlayers = numberOfPlayers;
	}

	public int getCurrentNumberOfPlayers()
	{
		return currentNumberOfPlayers;
	}

	public void setCurrentNumberOfPlayers(int currentNumberOfPlayers)
	{
		this.currentNumberOfPlayers = currentNumberOfPlayers;
	}

	public Map<ShipType, Integer> getBoats()
	{
		return boats;
	}

	public void setBoats(Map<ShipType, Integer> boats)
	{
		this.boats = boats;
	}

	public int getFieldWidth()
	{
		return fieldWidth;
	}

	public void setFieldWidth(int fieldWidth)
	{
		this.fieldWidth = fieldWidth;
	}

	public int getFieldHeight()
	{
		return fieldHeight;
	}

	public void setFieldHeight(int fieldHeight)
	{
		this.fieldHeight = fieldHeight;
	}

	public boolean isCanMove()
	{
		return canMove;
	}

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public GameMode getMode()
	{
		return mode;
	}

	public void setMode(GameMode mode)
	{
		this.mode = mode;
	}
}
