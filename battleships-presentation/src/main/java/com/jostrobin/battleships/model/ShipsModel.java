/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jostrobin.battleships.model;

import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.ShipType;

/**
 * @author rowyss
 *         Date: 16.11.11 Time: 20:18
 */
public class ShipsModel
{
    private List<Ship> ships = new ArrayList<Ship>()
    {{
    	add(new Ship(ShipType.BATTLESHIP));
    	add(new Ship(ShipType.DESTROYER));
    	add(new Ship(ShipType.SUBMARINE));
    	add(new Ship(ShipType.PATROL_BOAT));
    }};

    private List<Cell> cells = new ArrayList<Cell>();

    public List<Cell> getCells()
    {
        return cells;
    }

    public void setCells(List<Cell> cells)
    {
        this.cells = cells;
    }

    public List<Ship> getShips()
    {
        return ships;
    }

    public void setShips(List<Ship> ships)
    {
        this.ships = ships;
    }
}
