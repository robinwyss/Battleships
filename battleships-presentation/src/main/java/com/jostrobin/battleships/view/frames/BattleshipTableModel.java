package com.jostrobin.battleships.view.frames;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.data.enums.State;
import com.jostrobin.battleships.service.network.rmi.DefaultApplicationInterface;
import com.jostrobin.battleships.session.ApplicationState;

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
        ServerInformation serverInfo = servers.get(rowIndex);
        ApplicationState state = serverInfo.getState();
        State gameState = serverInfo.getState().getState();
        if (columnIndex == 0)
        {
            return state.getUsername();
        }
        else if (columnIndex == 1)
        {
            GameSettings remoteSettings = state.getSettings();
            if (remoteSettings != null)
            {
                return remoteSettings.getMode();
            }
        }
        else if (columnIndex == 2)
        {
            if (gameState == State.WAITING_FOR_PLAYERS || gameState == State.RUNNING)
            {
                GameSettings settings = state.getSettings();
                return settings.getCurrentNumberOfPlayers() + " / " + settings.getNumberOfPlayers();
            }
            else
            {
                return "N/A";
            }
        }
        else if (columnIndex == 3)
        {
            return serverInfo.getAddress().getCanonicalHostName();
        }
        else if (columnIndex == 4)
        {
            return serverInfo.getState().getState();
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
        ApplicationState state = ApplicationState.getInstance();
        if (state.isDebug() && servers.size() < 2)
        {
            // add a dummyserver if there is no other so far
            try
            {
                servers.add(new ServerInformation(InetAddress.getLocalHost(), state, new DefaultApplicationInterface(), "fakeId"));
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
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
