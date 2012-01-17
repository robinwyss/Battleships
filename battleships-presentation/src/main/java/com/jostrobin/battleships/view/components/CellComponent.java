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

package com.jostrobin.battleships.view.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.CellType;
import com.jostrobin.battleships.view.theme.ConfigurableTheme;
import com.jostrobin.battleships.view.theme.Theme;

/**
 * @author rowyss
 *         Date: 04.11.11 Time: 17:06
 */
public class CellComponent extends JComponent implements Cell
{

    public static final int CELL_SIZE = 25;
    public static final int TILE_SIZE = 25;
    private boolean hover;
    private Color grey = new Color(127, 127, 127, 64);
    private List<ActionListener> actionListeners = new ArrayList<ActionListener>();
    private boolean highlight = true;
    private int boardX;
    private int boardY;
    private boolean selected;
    private boolean hit;
    private CellType type = CellType.WATER;
    private Ship ship;
    private boolean selectable = true;
    private Theme theme = ConfigurableTheme.getInstance();
    private boolean alwaysDisplayShips = false;

    public CellComponent(int boardX, int boardY)
    {
        this.boardX = boardX;
        this.boardY = boardY;
        BattleFieldMouseAdapter mouseAdapter = new BattleFieldMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void paint(Graphics graphics)
    {
        drawBackground(graphics);
        if (hover)
        {
            graphics.setColor(grey);
            graphics.fillRect(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);
        }
        if (hit)
        {
            Image dot = null;
            // there is nothing to be hit
            if (type == CellType.WATER)
            {
                dot = theme.getGreendDot();
//                graphics.setColor(Color.BLACK);
            }
            // this is a part of a ship
            else
            {
                dot = theme.getRedDot();
//                graphics.setColor(Color.RED);
            }
            graphics.drawImage(dot, 0, 0, CELL_SIZE, CELL_SIZE, 0, 0, TILE_SIZE, TILE_SIZE, null);
//            graphics.drawLine(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);
//            graphics.drawLine(1, CELL_SIZE - 1, CELL_SIZE - 1, 1);
        }
        if (selected)
        {
            graphics.setColor(new Color(255, 255, 128, 128));
            graphics.fillRect(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);
        }
//        graphics.setColor(Color.BLACK);
//        graphics.drawRect(0, 0, CELL_SIZE - 1, CELL_SIZE - 1);
    }

    private void drawBackground(Graphics graphics)
    {
        // draw the water first
        graphics.drawImage(theme.getBackground(), 0, 0, CELL_SIZE, CELL_SIZE, boardX * CELL_SIZE, boardY * CELL_SIZE, (boardX + 1) * CELL_SIZE, (boardY + 1) * CELL_SIZE, null);

        if (CellType.SHIP.equals(type))
        {
//            graphics.setColor(Color.GRAY);
//            graphics.fillRect(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);

            // if either the ship has been completely destroyed or this board may always display ships
            if (ship != null && ((hit && ship.isShipDestroyed()) || alwaysDisplayShips))
            {
                int x = ship.getPositionX();
                int y = ship.getPositionY();
                Integer tileNumber = null; // which part of the ship is this component?

                for (int i = 0; i < ship.getSize(); i++)
                {
                    if ((x + i == boardX && boardY == y) || (y + i == boardY && boardX == x))
                    {
                        tileNumber = i;
                        break;
                    }
                }
                if (tileNumber != null)
                {
                    Image shipImage = theme.getByShipType(ship.getType());
                    if (shipImage != null)
                    {
                        if (ship.getOrientation() == Orientation.HORIZONTAL)
                        {
                            graphics.drawImage(shipImage, 0, 0, CELL_SIZE, CELL_SIZE, tileNumber * TILE_SIZE, 0,
                                    (tileNumber + 1) * TILE_SIZE, TILE_SIZE, null);
                        }
                        else if (ship.getOrientation() == Orientation.VERTICAL)
                        {
                            graphics.drawImage(shipImage, 0, 0, CELL_SIZE, CELL_SIZE, 0, (tileNumber + 1) * TILE_SIZE,
                                    TILE_SIZE, (tileNumber + 2) * TILE_SIZE, null);
                        }
                        //                    graphics.fillRect(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);
                    }
                }

                // if the ship has been destroyed, make it greeeey
                if (ship.isShipDestroyed())
                {
                    graphics.setColor(grey);
                    graphics.fillRect(1, 1, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }
    }

    public void addActionListener(ActionListener actionListener)
    {
        actionListeners.add(actionListener);
    }

    public int getBoardX()
    {
        return boardX;
    }

    public void setBoardX(int boardX)
    {
        this.boardX = boardX;
    }

    public int getBoardY()
    {
        return boardY;
    }

    public void setBoardY(int boardY)
    {
        this.boardY = boardY;
    }

    public boolean isHighlight()
    {
        return highlight;
    }

    public void setHighlight(boolean highlight)
    {
        this.highlight = highlight;
    }

    public CellType getType()
    {
        return type;
    }

    public void setType(CellType type)
    {
        this.type = type;
        repaint();
    }

    public boolean isHit()
    {
        return hit;
    }

    public void setHit(boolean hit)
    {
        this.hit = hit;
        repaint();
    }

    public Ship getShip()
    {
        return ship;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
        if (ship != null)
        {
            type = CellType.SHIP;
        }
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
        repaint();
    }

    public boolean isSelectable()
    {
        return selectable;
    }

    public void setSelectable(boolean selectable)
    {
        this.selectable = selectable;
    }

    public boolean isAlwaysDisplayShips()
    {
        return alwaysDisplayShips;
    }

    public void setAlwaysDisplayShips(boolean alwaysDisplayShips)
    {
        this.alwaysDisplayShips = alwaysDisplayShips;
    }

    private class BattleFieldMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseExited(MouseEvent mouseEvent)
        {
            if (highlight && selectable)
            {
                hover = false;
                repaint();
            }
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent)
        {
            if (selectable)
            {
                for (ActionListener actionListener : actionListeners)
                {
                    actionListener.actionPerformed(new ActionEvent(CellComponent.this, mouseEvent.hashCode(), "cellClicked"));
                }
            }
        }


        @Override
        public void mouseEntered(MouseEvent mouseEvent)
        {
            if (highlight && selectable)
            {
                hover = true;
                repaint();
            }
        }
    }

}
