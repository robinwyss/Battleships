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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.view.components.CellComponent;
import com.jostrobin.battleships.view.listeners.SelectionListener;

/**
 * @author rowyss
 *         Date: 05.11.11 Time: 18:52
 */
public class ShipsPanel extends JPanel
{
    private int y;
    private List<SelectionListener<Ship>> selectionListeners = new ArrayList<SelectionListener<Ship>>();
    private Map<Ship, JPanel> shipPanelMap = new HashMap<Ship, JPanel>();
    private static final Logger LOG = LoggerFactory.getLogger(ShipsPanel.class);

    public ShipsPanel()
    {
        initUi();
    }

    private void initUi()
    {
        setLayout(new GridBagLayout());
    }

    public void updateShips(List<Ship> ships)
    {
        removeAllShips();
        MouseListener mouseListener = new MouseListener();
        for (Ship ship : ships)
        {
            if (ship.isPlaced())
            {
                continue;
            }
            JPanel shipPanel = new JPanel();
            if (ship.getOrientation().equals(Orientation.HORIZONTAL))
            {
                shipPanel.setLayout(new GridLayout(1, ship.getSize()));
                shipPanel.setPreferredSize(new Dimension(ship.getSize() * CellComponent.CELL_SIZE, CellComponent.CELL_SIZE));
            }
            else
            {
                shipPanel.setLayout(new GridLayout(ship.getSize(), 1));
                shipPanel.setPreferredSize(new Dimension(CellComponent.CELL_SIZE, ship.getSize() * CellComponent.CELL_SIZE));
            }
            for (int i = 0; i < ship.getSize(); i++)
            {
                CellComponent cell = new CellComponent(0, i);
                cell.setAlwaysDisplayShips(true);
                cell.setShip(ship);
                cell.addMouseListener(mouseListener);
                ship.addCell(cell);
                shipPanel.add(cell);
            }
            ship.setPositionX(0);
            ship.setPositionY(0);
            
            GridBagConstraints shipConstraints = new GridBagConstraints();
            shipConstraints.gridy = y++;
            shipConstraints.anchor = GridBagConstraints.BASELINE;
            shipConstraints.insets = new Insets(2, 2, 2, 2);
            shipPanelMap.put(ship, shipPanel);
            add(shipPanel, shipConstraints);
        }
        repaint();
        revalidate();
    }

    private void removeAllShips()
    {

        for (JPanel shipPanel : shipPanelMap.values())
        {
            remove(shipPanel);
            y--;
        }
        shipPanelMap.clear();
    }

    public void addSelectionListener(SelectionListener<Ship> selectionListener)
    {
        selectionListeners.add(selectionListener);
    }


    private class MouseListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            if (mouseEvent.getSource() instanceof CellComponent)
            {
                CellComponent cell = (CellComponent) mouseEvent.getSource();
                cell.setSelected(true);
                Ship ship = cell.getShip();

                LOG.debug("Ship with size {} was selected", ship.getSize());
                for (SelectionListener<Ship> selectionListener : selectionListeners)
                {
                    selectionListener.selected(ship);
                }
            }
        }

    }
}
