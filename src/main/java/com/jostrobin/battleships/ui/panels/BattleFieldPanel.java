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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.jostrobin.battleships.ui.components.CellComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 04.11.11 Time: 17:28
 */
public class BattleFieldPanel extends JPanel implements ActionListener
{
    private int size = 10;
    public static final Logger LOG = LoggerFactory.getLogger(BattleFieldPanel.class);

    public BattleFieldPanel()
    {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(size, size));

        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                CellComponent cell = new CellComponent(x, y);
                cell.addActionListener(this);
                contentPanel.add(cell);
            }
        }
        contentPanel.setPreferredSize(new Dimension(size * 25, size * 25));

        setLayout(new FlowLayout());
        add(contentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        CellComponent cell = (CellComponent) actionEvent.getSource();
        LOG.debug("user clicked on cell {} {}", cell.getBoardX(), cell.getBoardY());
    }
}
