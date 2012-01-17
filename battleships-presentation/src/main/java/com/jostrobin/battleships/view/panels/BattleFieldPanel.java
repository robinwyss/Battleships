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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.enums.CellType;
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
    public static final Logger LOG = LoggerFactory.getLogger(BattleFieldPanel.class);
    private List<SelectionListener<Cell>> selectionListeners = new ArrayList<SelectionListener<Cell>>();
    private JPanel contentPanel;
    private Cell[][] cellArray;
    private final JLabel nameLabel;
    private boolean current;
    private boolean selectable = true;

    public BattleFieldPanel(String participantName)
    {
        setLayout(new GridBagLayout());
        nameLabel = new JLabel(participantName);
    }

    private void drawField(int width, int height)
    {
        removeAll();
        GridBagConstraints nameLabelConstraints = new GridBagConstraints();
        nameLabelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        add(nameLabel, nameLabelConstraints);

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(width, height));
        cellArray = new Cell[width][height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                CellComponent cell = new CellComponent(x, y);
                cell.setAlwaysDisplayShips(true);
                cell.setSelectable(selectable);
                cell.addActionListener(this);
                cellArray[x][y] = cell;
                contentPanel.add(cell);
            }
        }
        Dimension dimension = new Dimension(width * CellComponent.CELL_SIZE, height * CellComponent.CELL_SIZE);
        contentPanel.setPreferredSize(dimension);
        setMinimumSize(dimension);
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridy = 1;
        fieldConstraints.anchor = GridBagConstraints.BASELINE;
        fieldConstraints.fill = GridBagConstraints.BOTH;
        add(contentPanel, fieldConstraints);
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
        try
        {
            return cellArray[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }

    public void hitCell(int x, int y, AttackResult result)
    {
        Cell cell = findCellAt(x, y);
        cell.setHit(true);
        if (result == AttackResult.SHIP_DESTROYED || result == AttackResult.PLAYER_DESTROYED)
        {
            if (cell.getShip() != null)
            {
                cell.getShip().setShipDestroyed(true);
            }
        }
        else if (result != AttackResult.NO_HIT)
        {
            cell.setType(CellType.SHIP);
        }
    }

    public void addSelectionListener(SelectionListener<Cell> selectionListener)
    {
        selectionListeners.add(selectionListener);
    }

    public void initializeFieldSize(int length, int width)
    {
        drawField(length, width);
    }

    public void setCurrent(boolean current)
    {
        this.current = current;
        if (current)
        {
            nameLabel.setForeground(Color.BLUE);
            contentPanel.setBorder(new LineBorder(Color.RED));
        }
        else
        {
            nameLabel.setForeground(Color.BLACK);
            contentPanel.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
        }
    }

    public boolean isCurrent()
    {
        return current;
    }

    public boolean isSelectable()
    {
        return selectable;
    }

    public void setSelectable(boolean selectable)
    {
        if (cellArray != null)
        {

            for (Cell[] cells : cellArray)
            {
                for (Cell cell : cells)
                {
                    if (cell instanceof CellComponent)
                    {
                        ((CellComponent) cell).setSelectable(selectable);
                    }
                }
            }
        }
        this.selectable = selectable;
    }
}