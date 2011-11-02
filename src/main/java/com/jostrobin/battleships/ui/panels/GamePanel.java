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

import com.jostrobin.battleships.ui.components.BattleFieldComponent;

/**
 * @author rowyss
 *         Date: 02.11.11 Time: 18:08
 */
public class GamePanel extends JPanel
{
    private BattleFieldComponent battleFieldComponent;

    public GamePanel()
    {
        initUI();
    }

    private void initUI()
    {
        setLayout(new GridBagLayout());

        addGameComponent();

        this.setVisible(true);
    }

    private void addGameComponent()
    {
        battleFieldComponent = new BattleFieldComponent();
        GridBagConstraints gameConstraints = new GridBagConstraints();
        gameConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        gameConstraints.fill = GridBagConstraints.BOTH;
        gameConstraints.weightx = 1.0;
        gameConstraints.weighty = 1.0;
        add(battleFieldComponent, gameConstraints);
    }
}
