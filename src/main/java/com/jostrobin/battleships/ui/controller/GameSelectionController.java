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

package com.jostrobin.battleships.ui.controller;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.service.network.rmi.GameState;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameSelectionController implements ServerDetectionListener
{
    private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);

    private ServerDetectionManager serverDetectionManager;

    private GameSelectionFrame gameSelectionFrame;

    private List<ServerInformation> servers = new ArrayList<ServerInformation>();

    public GameSelectionController()
    {
        List<ServerDetectionListener> listeners = new ArrayList<ServerDetectionListener>();
        listeners.add(this);

        serverDetectionManager = new ServerDetectionManager(listeners);

        Thread thread = new Thread(serverDetectionManager);
        thread.start();
    }

    public void refresh()
    {
        serverDetectionManager.sendBroadcast();
    }

    public void exit()
    {
        System.exit(0);
    }

    @Override
    public void addServer(InetAddress address)
    {
        ApplicationInterface applicationInterface;

        try
        {
            Registry registry = LocateRegistry.getRegistry(address.getHostName());
            applicationInterface = (ApplicationInterface) registry.lookup("ApplicationInterface");
            GameState state = applicationInterface.getGameState();

            ServerInformation newServer = new ServerInformation(address, state);
            int index = servers.indexOf(newServer);
            if (index > -1)
            {
                // update existing information
                ServerInformation oldServer = servers.get(index);
                oldServer.setState(newServer.getState());
            }
            else
            {
                servers.add(newServer);
            }

            gameSelectionFrame.setServers(servers);
        }
        catch (RemoteException e)
        {
            logger.error("Failed to connect to the server at {}", address, e);
        }
        catch (NotBoundException e)
        {
            logger.error("Failed to connect to the server at {}", address, e);
        }
    }

    public GameSelectionFrame getGameSelectionFrame()
    {
        return gameSelectionFrame;
    }

    public void setGameSelectionFrame(GameSelectionFrame gameSelectionFrame)
    {
        this.gameSelectionFrame = gameSelectionFrame;
    }
}
