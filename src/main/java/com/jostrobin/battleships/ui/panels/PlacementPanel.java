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
import javax.swing.*;

/**
 * @author rowyss
 *         Date: 05.11.11 Time: 18:51
 */
public class PlacementPanel extends JPanel
{
    private BattleFieldPanel gamePanel;
    private ShipsPanel shipsPanel;
    private int y;

    public PlacementPanel()
    {
        setLayout(new GridBagLayout());

        gamePanel = new BattleFieldPanel();
        GridBagConstraints gamePanelConstraints = new GridBagConstraints();
        gamePanelConstraints.weightx = 0.6;
        gamePanelConstraints.weighty = 1.0;
        gamePanelConstraints.gridy = y;
        gamePanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gamePanelConstraints.fill = GridBagConstraints.BOTH;
        gamePanel.setBackground(Color.BLUE);
        add(gamePanel, gamePanelConstraints);

        shipsPanel = new ShipsPanel();
        GridBagConstraints shipsPanelConstraints = new GridBagConstraints();
        shipsPanelConstraints.weightx = 0.1;
        shipsPanelConstraints.weighty = 1.0;
        shipsPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
        shipsPanelConstraints.fill = GridBagConstraints.BOTH;
        shipsPanelConstraints.gridy = y;
        shipsPanelConstraints.gridx = 1;
        shipsPanel.setBackground(Color.GRAY);
        add(shipsPanel, shipsPanelConstraints);

        setSize(300, 300);

    }
}
