package com.jostrobin.battleships.data;


import com.jostrobin.battleships.data.enums.ShipType;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:33
 */
public class Ship
{
	private ShipType type;
	private Set<Cell> cells = new LinkedHashSet<Cell>();
}