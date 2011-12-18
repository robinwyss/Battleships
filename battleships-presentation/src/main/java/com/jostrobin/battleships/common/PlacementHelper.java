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

package com.jostrobin.battleships.common;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.CellType;
import com.jostrobin.battleships.view.panels.BattleFieldPanel;

/**
 * @author rowyss
 *         Date: 14.12.11 Time: 17:22
 */
public class PlacementHelper
{

    private BattleFieldPanel battleFieldPanel;

    public PlacementHelper(BattleFieldPanel battleFieldPanel)
    {
        this.battleFieldPanel = battleFieldPanel;
    }

    public boolean placeShip(Ship ship, int x, int y)
    {
        boolean placed = false;
        if (canBePlaced(ship, x, y, ship.getOrientation()))
        {
            ship.setPosition(x, y);
            ship.clearCells();
            for (int i = 0; i < ship.getSize(); i++)
            {
                ship.addCell(battleFieldPanel.findCellAt(x, y));
                if (ship.getOrientation() == Orientation.HORIZONTAL)
                {
                    x++;
                }
                else
                {
                    y++;
                }
            }
            ship.setSelected(true);
            ship.setPlaced(true);
            placed = true;
        }
        return placed;
    }

    private boolean canBePlaced(Ship ship, int x, int y, Orientation orientation)
    {
        boolean ok = true;
        for (int i = 0; i < ship.getSize(); i++)
        {
            Cell cell = battleFieldPanel.findCellAt(x, y);
            ok &= (cell != null && (cell.getType().equals(CellType.WATER) || ship.getCells().contains(cell)));
            if (orientation == Orientation.HORIZONTAL)
            {
                x++;
            }
            else
            {
                y++;
            }
        }
        return ok;
    }

    public void rotateShip(Ship ship)
    {
        if (ship != null)
        {
            Orientation orientation = switchOrientation(ship.getOrientation());
            if (ship.isPlaced())
            {
                if (canBePlaced(ship, ship.getPositionX(), ship.getPositionY(), orientation))
                {
                    ship.setOrientation(orientation);
                    placeShip(ship, ship.getPositionX(), ship.getPositionY());
                }
            }
            else
            {
                ship.setOrientation(orientation);
                ship.setSelected(true);
            }
        }
    }

    private Orientation switchOrientation(Orientation orientation)
    {
        if (Orientation.HORIZONTAL.equals(orientation))
        {
            return Orientation.VERTICAL;
        }
        else
        {
            return Orientation.HORIZONTAL;
        }
    }
}
