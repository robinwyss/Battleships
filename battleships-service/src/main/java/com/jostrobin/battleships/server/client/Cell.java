package com.jostrobin.battleships.server.client;


public class Cell implements Attackable
{
	private Ship ship;

	@Override
	public AttackResult attack(int x, int y)
	{
		if (ship != null)
		{
			return ship.attack(x, y);
		}
		return AttackResult.NO_HIT;
	} 
}
