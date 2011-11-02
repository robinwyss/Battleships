package com.jostrobin.battleships.ui.frames;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.service.network.rmi.GameState;

public class BattleshipTableModel extends AbstractTableModel
{
    private String[] columnNames;

    private List<ServerInformation> servers = new ArrayList<ServerInformation>();

    public BattleshipTableModel(String[] columnNames, List<ServerInformation> servers)
    {
        this.columnNames = columnNames;
        this.servers = servers;
    }

    @Override
    public int getRowCount()
    {
        return servers.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ServerInformation info = servers.get(rowIndex);
        GameState state = info.getState();
        if (columnIndex == 0)
        {
            return state.getUsername();
        }
        else if (columnIndex == 3)
        {
            return info.getAddress().getCanonicalHostName();
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

    public void setServers(List<ServerInformation> servers)
    {
        this.servers = servers;
        fireTableDataChanged();
    }
    
    public ServerInformation getServerAtRow(int row)
    {
    	if (servers != null && row >= 0 && row < servers.size())
    	{
    		return servers.get(row);
    	}
    	return null;
    }
}
