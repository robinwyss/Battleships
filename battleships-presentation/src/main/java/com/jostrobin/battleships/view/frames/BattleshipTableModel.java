package com.jostrobin.battleships.view.frames;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;

public class BattleshipTableModel extends AbstractTableModel
{
    private String[] columnNames;

    private List<Player> players = new ArrayList<Player>();

    public BattleshipTableModel(String[] columnNames, List<Player> players)
    {
        this.columnNames = columnNames;
        this.players = players;
    }

    @Override
    public int getRowCount()
    {
        return players.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Player player = players.get(rowIndex);
        if (columnIndex == 0)
        {
            return player.getUsername();
        }
        else if (columnIndex == 1)
        {
        	GameData gameData = player.getGameData();
        	if (gameData != null)
        	{
        		return gameData.getMode().name();
        	}
        }
        else if (columnIndex == 2)
        {
        	if (player.getState() == GameState.WAITING_FOR_PLAYERS || player.getState() == GameState.RUNNING)
        	{
        		GameData gameData = player.getGameData();
        		if (gameData != null)
        		{
        			return gameData.getCurrentPlayers() + " / " + gameData.getMaxPlayers();
        		}
        	}
        	else
        	{
        		return "N/A";
        	}
        }
        else if (columnIndex == 3)
        {
            return player.getState();
        }
        return "";
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col].toString();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        // ignore this, we are not editable
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
        fireTableDataChanged();
    }
    
    public Player getPlayerAtRow(int row)
    {
    	if (players != null && row >= 0 && row < players.size())
    	{
    		return players.get(row);
    	}
    	return null;
    }
}
