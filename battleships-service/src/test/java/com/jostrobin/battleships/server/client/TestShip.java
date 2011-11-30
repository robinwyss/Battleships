package com.jostrobin.battleships.server.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jostrobin.battleships.common.data.Orientation;

public class TestShip
{
	private Ship hor;
	
	private Ship small;
	
	@Before
	public void setup()
	{
		hor = new Ship();
		hor.setX(3);
		hor.setY(3);
		hor.setOrientation(Orientation.HORIZONTAL);
		hor.setSize(3);

		small = new Ship();
		small.setX(4);
		small.setY(8);
		small.setOrientation(Orientation.HORIZONTAL);
		small.setSize(1);
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
