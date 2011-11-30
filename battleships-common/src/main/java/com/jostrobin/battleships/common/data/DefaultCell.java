package com.jostrobin.battleships.common.data;

import com.jostrobin.battleships.common.data.enums.CellType;

public class DefaultCell implements Attackable, Cell
{
	private Ship ship;
	
	private int boardX;
	
	private int boardY;
	
	private boolean selected;
	
	private boolean hit = false;
	
	private CellType type;

	@Override
	public AttackResult attack(int x, int y)
	{
		if (ship != null)
		{
			return ship.attack(x, y);
		}
		return AttackResult.NO_HIT;
	}

	public Ship getShip()
	{
		return ship;
	}

	public void setShip(Ship ship)
	{
		this.ship = ship;
	}

	public int getBoardX()
	{
		return boardX;
	}

	public void setBoardX(int boardX)
	{
		this.boardX = boardX;
	}

	public int getBoardY()
	{
		return boardY;
	}

	public void setBoardY(int boardY)
	{
		this.boardY = boardY;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isHit()
	{
		return hit;
	}

	public void setHit(boolean hit)
	{
		this.hit = hit;
	}

	public CellType getType()
	{
		return type;
	}

	public void setType(CellType type)
	{
		this.type = type;
	}

}
