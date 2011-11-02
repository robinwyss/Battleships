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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import com.jostrobin.battleships.data.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author rowyss
 *         Date: 02.11.11 Time: 17:20
 */
public class BattleFieldComponent extends JComponent
{
    int size = 10;
    int cellSize = 25;
    private static final Logger LOG = LoggerFactory.getLogger(BattleFieldComponent.class);
    private Cell currentCell;

    public BattleFieldComponent()
    {
        setDoubleBuffered(true);
        BattleFieldMouseAdapter adapter = new BattleFieldMouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    public void paint(Graphics graphics)
    {

        graphics.setColor(Color.BLACK);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                graphics.drawRect(i * cellSize, j * cellSize, cellSize, cellSize);
                if (currentCell != null && currentCell.getX() == i && currentCell.getY() == j)
                {
                    Color initialColor = graphics.getColor();
                    graphics.setColor(Color.LIGHT_GRAY);
                    graphics.fillRect(i * cellSize + 1, j * cellSize + 1, cellSize - 1, cellSize - 1);
                    graphics.setColor(initialColor);
                }
            }
        }
        graphics.drawString("Hello", 0, 0);
    }

    private Cell getCell(int x, int y)
    {
        int cellX = x / cellSize;
        int cellY = y / cellSize;
        return new Cell(cellX, cellY);
    }


    private class BattleFieldMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {
            currentCell = null;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            Cell cell = getCell(mouseEvent.getX(), mouseEvent.getY());
            LOG.debug("user clicked on cell {} {}", cell.getX(), cell.getY());
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent)
        {
            currentCell = getCell(mouseEvent.getX(), mouseEvent.getY());
            repaint();
        }
    }
}
