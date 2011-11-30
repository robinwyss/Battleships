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

package com.jostrobin.battleships.common.data;

import com.jostrobin.battleships.common.data.enums.CellType;

/**
 * @author rowyss
 *         Date: 08.11.11 Time: 19:09
 */
public interface Cell
{
    int getBoardX();

    void setBoardX(int boardX);

    int getBoardY();

    void setBoardY(int boardY);

    boolean isHit();

    void setHit(boolean hit);

    boolean isSelected();

    void setSelected(boolean selected);

    Ship getShip();

    void setShip(Ship ship);

    CellType getType();

    void setType(CellType type);
}
