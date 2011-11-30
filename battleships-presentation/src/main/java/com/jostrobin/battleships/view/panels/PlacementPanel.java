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

package com.jostrobin.battleships.view.panels;

import java.awt.*;
import javax.swing.*;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.model.PlacementModel;
import com.jostrobin.battleships.view.listeners.EventListener;
import com.jostrobin.battleships.view.listeners.SelectionListener;
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
    private PlacementModel placementModel;

    public PlacementPanel()
    {
        initUi();
    }

    private void initUi()
    {
        setLayout(new GridBagLayout());

        battleField = new BattleFieldPanel();
        GridBagConstraints gamePanelConstraints = new GridBagConstraints();
        gamePanelConstraints.weightx = 0.6;
        gamePanelConstraints.weighty = 1.0;
        gamePanelConstraints.gridy = y;
        gamePanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gamePanelConstraints.fill = GridBagConstraints.BOTH;
        add(battleField, gamePanelConstraints);

        shipsPanel = new ShipsPanel();
        GridBagConstraints shipsPanelConstraints = new GridBagConstraints();
        shipsPanelConstraints.weightx = 0.1;
        shipsPanelConstraints.weighty = 1.0;
        shipsPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
        shipsPanelConstraints.fill = GridBagConstraints.BOTH;
        shipsPanelConstraints.gridy = y;
        shipsPanelConstraints.gridx = 1;
        add(shipsPanel, shipsPanelConstraints);
    }

    public void addShipSelectionListener(SelectionListener<Ship> shipSelectionListener)
    {
        shipsPanel.addSelectionListener(shipSelectionListener);
    }

    public void addCellSelectionListener(SelectionListener<Cell> cellSelectionListener)
    {
        battleField.addSelectionListener(cellSelectionListener);
    }

    public void addRotationListener(EventListener listener)
    {
        shipsPanel.addRotationListener(listener);
    }

    public void updateShips()
    {
        shipsPanel.updateShips(placementModel.getShips());
    }

    public Cell findCellAt(int x, int y)
    {
        return battleField.findCellAt(x, y);
    }

    public PlacementModel getPlacementModel()
    {
        return placementModel;
    }

    public void setPlacementModel(PlacementModel placementModel)
    {
        this.placementModel = placementModel;
        updateShips();
    }
}
