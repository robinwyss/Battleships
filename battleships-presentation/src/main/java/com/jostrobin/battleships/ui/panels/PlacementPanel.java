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

package com.jostrobin.battleships.ui.panels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.jostrobin.battleships.data.Cell;
import com.jostrobin.battleships.data.Ship;
import com.jostrobin.battleships.ui.listeners.SelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 05.11.11 Time: 18:51
 */
public class PlacementPanel extends JPanel
{
    public static final Logger LOG = LoggerFactory.getLogger(PlacementPanel.class);
    private BattleFieldPanel battleField;
    private ShipsPanel shipsPanel;
    private int y;
    private List<Ship> ships = new ArrayList<Ship>()
    {{
            add(new Ship(2));
            add(new Ship(3));
            add(new Ship(4));
            add(new Ship(1));
        }};

    public PlacementPanel()
    {
        setLayout(new GridBagLayout());

        battleField = new BattleFieldPanel();
        GridBagConstraints gamePanelConstraints = new GridBagConstraints();
        gamePanelConstraints.weightx = 0.6;
        gamePanelConstraints.weighty = 1.0;
        gamePanelConstraints.gridy = y;
        gamePanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gamePanelConstraints.fill = GridBagConstraints.BOTH;
        battleField.addSelectionListener(new CellSelectionListener());
        add(battleField, gamePanelConstraints);

        shipsPanel = new ShipsPanel();
        shipsPanel.addShips(ships);
        GridBagConstraints shipsPanelConstraints = new GridBagConstraints();
        shipsPanelConstraints.weightx = 0.1;
        shipsPanelConstraints.weighty = 1.0;
        shipsPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
        shipsPanelConstraints.fill = GridBagConstraints.BOTH;
        shipsPanelConstraints.gridy = y;
        shipsPanelConstraints.gridx = 1;
        shipsPanel.addSelectionListener(new ShipSelectionListener());
        add(shipsPanel, shipsPanelConstraints);

    }

    private void rotateShip()
    {

    }


    private void deselectOtherShips(Ship ship)
    {
        ship.setSelected(true);
        for (Ship otherShip : ships)
        {
            if (otherShip != ship)
            {
                otherShip.setSelected(false);
            }
        }
    }

    private class ShipSelectionListener implements SelectionListener<Ship>
    {

        @Override
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
    }

    private class CellSelectionListener implements SelectionListener<Cell>
    {

        @Override
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
                for (Ship ship : ships)
                {
                    if (ship.isSelected())
                    {
                        if (battleField.placeShip(ship, cell.getBoardX(), cell.getBoardY()))
                        {
                            shipsPanel.removeShip(ship);
                        }
                    }
                }
            }
        }
    }
}
