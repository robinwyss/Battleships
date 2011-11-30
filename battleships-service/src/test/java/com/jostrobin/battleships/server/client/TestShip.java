package com.jostrobin.battleships.server.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;

public class TestShip
{
	private Ship hor;
	
	private Ship small;
	
	@Before
	public void setup()
	{
		hor = new Ship(3);
		hor.setPosition(3, 3);
		hor.setOrientation(Orientation.HORIZONTAL);

		small = new Ship(1);
		small.setPosition(4, 8);
		small.setOrientation(Orientation.HORIZONTAL);
	}
	
	@Test
	public void testHit()
	{
		assertEquals(AttackResult.NO_HIT, hor.attack(2, 3));
		assertEquals(AttackResult.NO_HIT, hor.attack(6, 3));
		assertEquals(AttackResult.NO_HIT, hor.attack(7, 3));

		assertEquals(AttackResult.NO_HIT, hor.attack(2, 2));
		assertEquals(AttackResult.NO_HIT, hor.attack(3, 2));
		assertEquals(AttackResult.NO_HIT, hor.attack(4, 2));
		assertEquals(AttackResult.NO_HIT, hor.attack(5, 2));
		assertEquals(AttackResult.NO_HIT, hor.attack(6, 2));

		assertEquals(AttackResult.NO_HIT, hor.attack(2, 4));
		assertEquals(AttackResult.NO_HIT, hor.attack(3, 4));
		assertEquals(AttackResult.NO_HIT, hor.attack(4, 4));
		assertEquals(AttackResult.NO_HIT, hor.attack(5, 4));
		assertEquals(AttackResult.NO_HIT, hor.attack(6, 4));

		assertEquals(AttackResult.HIT, hor.attack(3, 3));
		assertEquals(AttackResult.HIT, hor.attack(4, 3));
		assertEquals(AttackResult.HIT, hor.attack(5, 3));
	}
	
	@Test
	public void testSmallShip()
	{
		assertEquals(AttackResult.NO_HIT, small.attack(5, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(3, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 9));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(5, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(3, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(4, -1));
		assertEquals(AttackResult.NO_HIT, small.attack(-1, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 8000));

		assertEquals(AttackResult.HIT, small.attack(4, 8));
	}
}
