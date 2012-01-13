package com.jostrobin.battleships.server.client;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.DefaultCell;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.ShipType;

public class TestShip
{
	private Ship hor;
	
	private Ship small;
	
	@Before
	public void setup()
	{
		Set<Cell> cells = new HashSet<Cell>();
		Cell cell = new DefaultCell();
		cell.setBoardX(3);
		cell.setBoardY(3);
		cells.add(cell);
		
		cell = new DefaultCell();
		cell.setBoardX(4);
		cell.setBoardY(3);
		cells.add(cell);
		
		cell = new DefaultCell();
		cell.setBoardX(5);
		cell.setBoardY(3);
		cells.add(cell);
		
		hor = new Ship(ShipType.DESTROYER);
		hor.setCells(cells);
		hor.setPosition(3, 3);
		hor.setOrientation(Orientation.HORIZONTAL);

		cells = new HashSet<Cell>();
		cell = new DefaultCell();
		cell.setBoardX(4);
		cell.setBoardY(8);
		cells.add(cell);
		
		small = new Ship(ShipType.PATROL_BOAT);
		small.setCells(cells);
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
		assertEquals(AttackResult.INVALID, hor.attack(4, 3)); // double attack
		assertEquals(AttackResult.SHIP_DESTROYED, hor.attack(5, 3));
		assertEquals(AttackResult.INVALID, hor.attack(5, 3)); // double attack
	}
	
	@Test
	public void testSmallShip()
	{
		assertEquals(AttackResult.HIT, small.attack(5, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(3, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 9));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(5, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(3, 7));
		assertEquals(AttackResult.NO_HIT, small.attack(4, -1));
		assertEquals(AttackResult.NO_HIT, small.attack(-1, 8));
		assertEquals(AttackResult.NO_HIT, small.attack(4, 8000));

		assertEquals(AttackResult.SHIP_DESTROYED, small.attack(4, 8));
	}
}
