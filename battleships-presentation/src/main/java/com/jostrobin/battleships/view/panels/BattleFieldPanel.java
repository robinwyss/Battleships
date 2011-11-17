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

import com.jostrobin.battleships.data.Cell;
import com.jostrobin.battleships.view.components.CellComponent;
import com.jostrobin.battleships.view.listeners.SelectionListener;
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
    private List<SelectionListener<Cell>> selectionListeners = new ArrayList<SelectionListener<Cell>>();
    private final JPanel contentPanel;
    private List<Cell> cells = new ArrayList<Cell>();

    public BattleFieldPanel()
    {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(size, size));

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                CellComponent cell = new CellComponent(x, y);
                cell.addActionListener(this);
                cells.add(cell);
                contentPanel.add(cell);
            }
        }
        Dimension dimension = new Dimension(size * CellComponent.CELL_SIZE, size * CellComponent.CELL_SIZE);
        contentPanel.setPreferredSize(dimension);
        setMinimumSize(dimension);
        setLayout(new FlowLayout());
        add(contentPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Cell cell = (Cell) actionEvent.getSource();
        LOG.debug("user clicked on cell {} {}", cell.getBoardX(), cell.getBoardY());
        for (SelectionListener<Cell> selectionListener : selectionListeners)
        {
            selectionListener.selected(cell);
        }
    }

    public Cell findCellAt(int x, int y)
    {
        for (Cell cell : cells)
        {
            if (cell.getBoardX() == x && cell.getBoardY() == y)
            {
                return cell;
            }
        }
        return null;
    }

    public void addSelectionListener(SelectionListener<Cell> selectionListener)
    {
        selectionListeners.add(selectionListener);
    }

}