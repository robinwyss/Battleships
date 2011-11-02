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

package com.jostrobin.battleships.ui.frames;

import java.awt.*;
import javax.swing.*;

import com.jostrobin.battleships.ui.controller.ChatController;
import com.jostrobin.battleships.ui.panels.ChatPanel;
import com.jostrobin.battleships.ui.panels.GamePanel;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 18:00
 */
public class GameFrame extends JFrame
{
    private int y;

    public GameFrame() throws HeadlessException
    {
        initUI();
    }

    private void initUI()
    {
        setLayout(new GridBagLayout());

        GamePanel gamePanel = new GamePanel();
        GridBagConstraints battlefieldConstraints = new GridBagConstraints();
        battlefieldConstraints.weightx = 1.0;
        battlefieldConstraints.weighty = 0.8;
        battlefieldConstraints.gridy = y++;
        battlefieldConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        battlefieldConstraints.fill = GridBagConstraints.BOTH;
        add(gamePanel, battlefieldConstraints);

        GridBagConstraints chatPanelConstraints = new GridBagConstraints();
        chatPanelConstraints.gridy = y++;
        chatPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        chatPanelConstraints.fill = GridBagConstraints.BOTH;
        chatPanelConstraints.weightx = 1.0;
        chatPanelConstraints.weighty = 0.2;
        add(new ChatPanel(new ChatController()), chatPanelConstraints);

        setVisible(true);
        setSize(600, 600);
    }

}
