package com.jostrobin.battleships.common.data;


import java.util.HashSet;
import java.util.Set;

import com.jostrobin.battleships.common.data.enums.CellType;
import com.jostrobin.battleships.common.data.enums.ShipType;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:33
 */
public class Ship 
{
    private ShipType type;
    private int size;
    private int positionX = -1;
    private int positionY = -1;
    private Set<Cell> cells = new HashSet<Cell>();
    private boolean selected;
    private boolean placed;
    private Orientation orientation = Orientation.HORIZONTAL;

    public Ship(int size)
    {
        this.size = size;
    }

    public void setPosition(int positionX, int positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void addCell(Cell cell)
    {
        cell.setType(CellType.SHIP);
        cell.setShip(this);
        cells.add(cell);
    }

    public void clearCells()
    {
        for (Cell cell : cells)
        {
            cell.setSelected(false);
            cell.setType(CellType.WATER);
            cell.setShip(null);
        }
        cells.clear();
    }


    public void setSelected(boolean selected)
    {
        for (Cell cell : cells)
        {
            cell.setSelected(selected);
        }
        this.selected = selected;
    }
    
	public AttackResult attack(int x, int y)
	{
		if (orientation == Orientation.HORIZONTAL)
		{
			if (x >= this.positionX && x < (this.positionX+size) && y == this.positionY)
			{
				return AttackResult.HIT;
			}
		}
		else
		{
			if (y >= this.positionY && y < (this.positionY+size) && x == this.positionX)
			{
				return AttackResult.HIT;
			}
		}
		return AttackResult.NO_HIT;
	}

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getPositionX()
    {
        return positionX;
    }

    public void setPositionX(int positionX)
    {
        this.positionX = positionX;
    }

    public int getPositionY()
    {
        return positionY;
    }

    public void setPositionY(int positionY)
    {
        this.positionY = positionY;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public Set<Cell> getCells()
    {
        return cells;
    }

    public void setCells(Set<Cell> cells)
    {
        this.cells = cells;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }

    public boolean isPlaced()
    {
        return placed;
    }

    public void setPlaced(boolean placed)
    {
        this.placed = placed;
    }

	public ShipType getType()
	{
		return type;
	}

	public void setType(ShipType type)
	{
		this.type = type;
	}
}