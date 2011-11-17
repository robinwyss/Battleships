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

package com.jostrobin.battleships.view.frames;

import java.awt.*;
import javax.swing.*;

import com.jostrobin.battleships.controller.ChatController;
import com.jostrobin.battleships.controller.PlacementController;
import com.jostrobin.battleships.controller.PlacementModel;
import com.jostrobin.battleships.view.panels.BattleFieldPanel;
import com.jostrobin.battleships.view.panels.ChatPanel;
import com.jostrobin.battleships.view.panels.PlacementPanel;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 18:00
 */
public class GameFrame extends JPanel
{
    private int y;
    private BattleFieldPanel gamePanel;
    private PlacementPanel placementPanel;

    public GameFrame() throws HeadlessException
    {
        initUI();
    }

    private void initUI()
    {
        setLayout(new GridBagLayout());

        gamePanel = new BattleFieldPanel();
        GridBagConstraints battlefieldConstraints = new GridBagConstraints();
        battlefieldConstraints.weightx = 1.0;
        battlefieldConstraints.weighty = 0.8;
        battlefieldConstraints.gridy = y;
        battlefieldConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        battlefieldConstraints.fill = GridBagConstraints.BOTH;
        add(gamePanel, battlefieldConstraints);
        gamePanel.setVisible(false);

        placementPanel = new PlacementPanel();
        GridBagConstraints placementPanelConstraints = new GridBagConstraints();
        placementPanelConstraints.weightx = 1.0;
        placementPanelConstraints.weighty = 0.8;
        placementPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        placementPanelConstraints.fill = GridBagConstraints.BOTH;
        placementPanelConstraints.gridy = y++;
        add(placementPanel, placementPanelConstraints);
        placementPanel.setVisible(true);

        setVisible(true);
        setSize(600, 600);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addChatPanel(ChatController chatController)
    {
        ChatPanel chatPanel = new ChatPanel(chatController);
        GridBagConstraints chatPanelConstraints = new GridBagConstraints();
        chatPanelConstraints.gridy = 1;
        chatPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        chatPanelConstraints.fill = GridBagConstraints.BOTH;
        chatPanelConstraints.weightx = 1.0;
        chatPanelConstraints.weighty = 0.2;
        add(chatPanel, chatPanelConstraints);
    }

    public void showGameWindow()
    {
        gamePanel.setVisible(true);
        placementPanel.setVisible(false);
    }

    public void showPlacementWindow()
    {
        gamePanel.setVisible(false);
        placementPanel.setVisible(true);
        PlacementModel placementModel = new PlacementModel();
        placementPanel.setPlacementModel(placementModel);
        new PlacementController(placementPanel).setModel(placementModel);
    }

}
