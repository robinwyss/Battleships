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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.model.ShipsModel;
import com.jostrobin.battleships.view.listeners.EventListener;
import com.jostrobin.battleships.view.listeners.SelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author rowyss
 *         Date: 05.11.11 Time: 18:51
 */
public class PlacementPanel extends JPanel implements ActionListener, InitializingBean
{
    public static final Logger LOG = LoggerFactory.getLogger(PlacementPanel.class);
    private BattleFieldPanel battleField;
    private ShipsPanel shipsPanel;
    private JButton rotate;
    private JButton ready;
    private int y;
    private ShipsModel shipsModel;
    private List<EventListener<Object>> rotationListeners = new ArrayList<EventListener<Object>>();
    private List<EventListener<Object>> readyListeners = new ArrayList<EventListener<Object>>();
    private List<EventListener<Object>> randomListeners = new ArrayList<EventListener<Object>>();
    private JButton random;
    private int fieldLength;
    private int fieldWidth;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        setLayout(new GridBagLayout());

        battleField = new BattleFieldPanel("");
        GridBagConstraints gamePanelConstraints = new GridBagConstraints();
        gamePanelConstraints.weightx = 0.6;
        gamePanelConstraints.weighty = 1.0;
        gamePanelConstraints.gridy = y;
        gamePanelConstraints.gridheight = 2;
        gamePanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gamePanelConstraints.fill = GridBagConstraints.BOTH;
        add(battleField, gamePanelConstraints);

        rotate = new JButton("Rotate ship");
        rotate.addActionListener(this);
        GridBagConstraints leftButtonConstraints = new GridBagConstraints();
        leftButtonConstraints.gridy = y++;
        leftButtonConstraints.gridx = 1;
        leftButtonConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        add(rotate, leftButtonConstraints);

        random = new JButton("Random");
        random.addActionListener(this);
        GridBagConstraints randomButtonConstraints = new GridBagConstraints();
        randomButtonConstraints.gridy = y++;
        randomButtonConstraints.gridx = 1;
        randomButtonConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        random.addActionListener(this);
        add(random, randomButtonConstraints);

        shipsPanel = new ShipsPanel();
        GridBagConstraints shipsPanelConstraints = new GridBagConstraints();
        shipsPanelConstraints.weightx = 0.1;
        shipsPanelConstraints.weighty = 1.0;
        shipsPanelConstraints.anchor = GridBagConstraints.BASELINE_LEADING;
        shipsPanelConstraints.fill = GridBagConstraints.BOTH;
        shipsPanelConstraints.gridy = y++;
        shipsPanelConstraints.gridx = 1;
        add(shipsPanel, shipsPanelConstraints);

        ready = new JButton("I'm ready");
        ready.addActionListener(this);
        GridBagConstraints readyButtonConstraints = new GridBagConstraints();
        readyButtonConstraints.gridy = y++;
        readyButtonConstraints.gridx = 1;
        readyButtonConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        ready.setEnabled(false);
        add(ready, readyButtonConstraints);

        updateShips();
    }

    public void reset()
    {
        battleField.setSelectable(true);
    }

    public void addShipSelectionListener(SelectionListener<Ship> shipSelectionListener)
    {
        shipsPanel.addSelectionListener(shipSelectionListener);
    }

    public void addCellSelectionListener(SelectionListener<Cell> cellSelectionListener)
    {
        battleField.addSelectionListener(cellSelectionListener);
    }

    public void updateShips()
    {
        shipsPanel.updateShips(shipsModel.getShips());
    }

    public Cell findCellAt(int x, int y)
    {
        return battleField.findCellAt(x, y);
    }

    public ShipsModel getShipsModel()
    {
        return shipsModel;
    }

    public void setShipsModel(ShipsModel shipsModel)
    {
        this.shipsModel = shipsModel;
    }

    public void enableReadyButton(boolean enable)
    {
        ready.setEnabled(enable);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(rotate))
        {
            for (EventListener<Object> listener : rotationListeners)
            {
                listener.actionPerformed(null);
            }
        }
        else if (actionEvent.getSource().equals(ready))
        {
            LOG.info("player is ready");
            for (EventListener<Object> listener : readyListeners)
            {
                listener.actionPerformed(null);
            }
            battleField.setSelectable(false);
            ready.setEnabled(false);
            deselectShips();
        }
        else if (actionEvent.getSource().equals(random))
        {
            for (EventListener<Object> randomListener : randomListeners)
            {
                randomListener.actionPerformed(null);
            }
        }

    }

    private void deselectShips()
    {
        for (Ship ship : shipsModel.getShips())
        {
            ship.setSelected(false);
        }
    }

    public void addRotationListener(EventListener<Object> listener)
    {
        rotationListeners.add(listener);
    }

    public void removeRotationListener(EventListener<Object> listener)
    {
        rotationListeners.remove(listener);
    }

    public void addReadyListener(EventListener<Object> listener)
    {
        readyListeners.add(listener);
    }

    public void removeReadyListener(EventListener<Object> listener)
    {
        readyListeners.remove(listener);
    }

    public void addRandomListener(EventListener<Object> listener)
    {
        randomListeners.add(listener);
    }

    public void removeRandomListener(EventListener<Object> listener)
    {
        randomListeners.remove(listener);
    }


    public void setFieldSize(int length, int width)
    {
        this.fieldWidth = width;
        this.fieldLength = length;
        battleField.initializeFieldSize(length, width);
        revalidate();
    }

    public BattleFieldPanel getBattleField()
    {
        return battleField;
    }

    public int getFieldWidth()
    {
        return fieldWidth;
    }

    public int getFieldLength()
    {
        return fieldLength;
    }
}
