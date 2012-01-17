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

import java.util.List;
import java.util.Random;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.PlacementHelper;
import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.model.ShipsModel;
import com.jostrobin.battleships.view.listeners.EventListener;
import com.jostrobin.battleships.view.listeners.SelectionListener;
import com.jostrobin.battleships.view.panels.PlacementPanel;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author rowyss
 *         Date: 09.11.11 Time: 16:44
 */
public class PlacementController implements InitializingBean, NetworkListener
{
    private PlacementPanel placementPanel;
    private ShipsModel model;
    private ApplicationController applicationController;
    private PlacementHelper placementHelper;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        applicationController.addNetworkListener(this);

        placementPanel.addCellSelectionListener(new CellSelectionListener());
        placementPanel.addShipSelectionListener(new ShipSelectionListener());
        placementPanel.addRotationListener(new RotationListener());
        placementPanel.addReadyListener(new ReadyListener());
        placementPanel.addRandomListener(new RandomListener());
        placementHelper = new PlacementHelper(placementPanel.getBattleField());
    }

    public void initializeShips(List<Ship> ships)
    {
        model.setShips(ships);
        placementPanel.updateShips();
        placementPanel.reset();
    }

    private void rotateShip()
    {
        Ship ship = findSelectedShip();
        placementHelper.rotateShip(ship);
        placementPanel.updateShips();
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
            Ship ship = findSelectedShip();
            if (ship != null && placeShip(ship, cell.getBoardX(), cell.getBoardY()))
            {
                placementPanel.updateShips();
                checkAllShipsPlaced();
            }
        }
    }

    private void checkAllShipsPlaced()
    {
        boolean ready = true;
        for (Ship ship : model.getShips())
        {
            ready &= ship.isPlaced();
        }
        placementPanel.enableReadyButton(ready);
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


    private boolean placeShip(Ship ship, int x, int y)
    {
        return placementHelper.placeShip(ship, x, y);
    }


    private Ship findSelectedShip()
    {
        for (Ship ship : model.getShips())
        {
            if (ship.isSelected())
            {
                return ship;
            }
        }
        return null;
    }

    public boolean placeShipsRandomly()
    {
        boolean allShipsPlaced = true;
        for (Ship ship : model.getShips())
        {
            boolean placed = false;
            int tries = 0;
            while (!placed && tries < 100)
            {
                int x = new Random().nextInt(placementPanel.getFieldWidth());
                int y = new Random().nextInt(placementPanel.getFieldLength());
                Orientation orientation = Math.random() > 0.5 ? Orientation.HORIZONTAL : Orientation.VERTICAL;
                ship.setOrientation(orientation);
                placed = placeShip(ship, x, y);
                tries++;
            }
            allShipsPlaced &= placed;
            if (!placed)
            {
                ship.clearCells();
                ship.setPlaced(false);
                ship.setOrientation(Orientation.HORIZONTAL);
            }
            ship.setSelected(false);
        }
        placementPanel.updateShips();
        checkAllShipsPlaced();
        return allShipsPlaced;
    }

    public ShipsModel getModel()
    {
        return model;
    }

    public void setModel(ShipsModel model)
    {
        this.model = model;
    }

    public PlacementPanel getPlacementPanel()
    {
        return placementPanel;
    }

    public void setPlacementPanel(PlacementPanel placementPanel)
    {
        this.placementPanel = placementPanel;
    }

    public ApplicationController getApplicationController()
    {
        return applicationController;
    }

    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
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


    private class RotationListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            rotateShip();
        }
    }

    private class ReadyListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            applicationController.shipsPlaced(model.getShips());
        }
    }

    @Override
    public void notify(Command command)
    {
        switch (command.getCommand())
        {
            case Command.START_GAME:
                applicationController.showGameView(command.getStartingPlayer());
                break;
        }
    }

    private class RandomListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            placeShipsRandomly();
        }
    }
}
