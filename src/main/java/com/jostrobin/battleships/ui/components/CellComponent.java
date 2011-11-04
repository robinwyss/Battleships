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

package com.jostrobin.battleships.ui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;
import javax.swing.*;

/**
 * @author rowyss
 *         Date: 04.11.11 Time: 17:06
 */
public class CellComponent extends JComponent
{

    private int cellSize = 25;
    private boolean hover;
    private Color grey = new Color(127, 127, 127, 64);
    private int x, y;
    private ActionListener actionListener;

    public CellComponent(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics graphics)
    {
        graphics.drawRect(0, 0, cellSize, cellSize);
        if (hover)
        {
            graphics.setColor(grey);
            graphics.fillRect(0, 0, cellSize, cellSize);
        }
    }

    public void addActionListener(ActionListener actionListener)
    {
        this.actionListener = actionListener;
    }

    private class BattleFieldMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {
            hover = true;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            actionListener.actionPerformed(new ActionEvent(this, UUID.randomUUID().clockSequence(), "cellClicked"));
        }


        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {
            hover = true;
            repaint();
        }
    }

}
