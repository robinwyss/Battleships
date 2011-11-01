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
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;

public class GameSelectionController implements ServerDetectionListener
{
    private ServerDetectionManager serverDetectionManager;

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
        System.out.println("Server found at " + address);
    }
}
