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

package com.jostrobin.battleships.controller;

import javax.annotation.PostConstruct;

import com.jostrobin.battleships.data.Cell;
import com.jostrobin.battleships.data.Ship;
import com.jostrobin.battleships.data.enums.CellType;
import com.jostrobin.battleships.data.enums.Orientation;
import com.jostrobin.battleships.model.PlacementModel;
import com.jostrobin.battleships.view.listeners.SelectionListener;
import com.jostrobin.battleships.view.panels.PlacementPanel;

/**
 * @author rowyss
 *         Date: 09.11.11 Time: 16:44
 */
public class PlacementController
{
    private PlacementPanel placementPanel;
    private PlacementModel model;

    @PostConstruct
    public void init()
    {
        placementPanel.addCellSelectionListener(new CellSelectionListener());
        placementPanel.addShipSelectionListener(new ShipSelectionListener());
    }

    private void rotateShip()
    {

    }


    private void deselectOtherShips(Ship ship)
    {
        ship.setSelected(true);
        for (Ship otherShip : model.getShips())
        {
            if (otherShip != ship)
            {
                otherShip.setSelected(false);
            }
        }
    }

    public void selected(Cell cell)
    {
        if (cell.getShip() != null)
        {
            Ship ship = cell.getShip();
            if (ship.isSelected())
            {
                ship.setSelected(false);
            }
            else
            {
                cell.getShip().setSelected(true);
                deselectOtherShips(ship);
            }
        }
        else
        {
            for (Ship ship : model.getShips())
            {
                if (ship.isSelected())
                {
                    if (placeShip(ship, cell.getBoardX(), cell.getBoardY()))
                    {
                        placementPanel.updateShips();
                    }
                }
            }
        }
    }

    public void selected(Ship ship)
    {
        if (ship.isSelected())
        {
            ship.setSelected(false);
        }
        else
        {
            deselectOtherShips(ship);
        }
    }


    public boolean placeShip(Ship ship, int x, int y)
    {
        boolean placed = false;
        if (canBePlaced(ship, x, y))
        {
            ship.setPosition(x, y);
            ship.clearCells();
            for (int i = 0; i < ship.getSize(); i++)
            {
                ship.addCell(placementPanel.findCellAt(x, y));
                x++;
            }
            ship.setSelected(false);
            ship.setPlaced(true);
            placed = true;
        }
        return placed;
    }

    private boolean canBePlaced(Ship ship, int x, int y)
    {
        boolean ok = true;
        for (int i = 0; i < ship.getSize(); i++)
        {
            Cell cell = placementPanel.findCellAt(x, y);
            ok &= (cell != null && (cell.getType().equals(CellType.WATER) || ship.getCells().contains(cell)));
            if (ship.getOrientation() == Orientation.HORIZONTAL)
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

    public PlacementModel getModel()
    {
        return model;
    }

    public void setModel(PlacementModel model)
    {
        this.model = model;
    }

    private class ShipSelectionListener implements SelectionListener<Ship>
    {

        @Override
        public void selected(Ship ship)
        {
            PlacementController.this.selected(ship);
        }
    }

    private class CellSelectionListener implements SelectionListener<Cell>
    {

        @Override
        public void selected(Cell cell)
        {
            PlacementController.this.selected(cell);
        }
    }

    public PlacementPanel getPlacementPanel()
    {
        return placementPanel;
    }

    public void setPlacementPanel(PlacementPanel placementPanel)
    {
        this.placementPanel = placementPanel;
    }
}
