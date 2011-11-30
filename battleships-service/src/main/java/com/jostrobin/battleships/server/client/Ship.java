package com.jostrobin.battleships.server.client;

import com.jostrobin.battleships.common.data.Orientation;

public class Ship implements Attackable
{
	private int size;
	
	private int x;
	
	private int y;
	
	private Orientation orientation = Orientation.HORIZONTAL;

	@Override
	public AttackResult attack(int x, int y)
	{
		if (orientation == Orientation.HORIZONTAL)
		{
			if (x >= this.x && x < (this.x+size) && y == this.y)
			{
				return AttackResult.HIT;
			}
		}
		else
		{
			if (y >= this.y && y < (this.y+size) && x == this.x)
			{
				return AttackResult.HIT;
			}
		}
		return AttackResult.NO_HIT;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
}
