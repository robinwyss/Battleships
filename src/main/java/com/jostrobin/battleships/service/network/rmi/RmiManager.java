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

package com.jostrobin.battleships.service.network.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.jostrobin.battleships.service.network.rmi.chat.server.DefaultChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 17:10
 */
public class RmiManager
{
    private static final Logger logger = LoggerFactory.getLogger(RmiManager.class);
    
    private static final RmiManager rmiManager = new RmiManager();
    
    private DefaultChatServer chat;
    
    private RmiManager()
    {
    	try
		{
			chat = new DefaultChatServer();
		}
    	catch (RemoteException e)
		{
            logger.error("Failed to initialize RMI object", e);
		}
    }

    /**
     * Creates the RMI registry and exports all the used RMI
     */
    public void startupRmiServices()
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            registry.bind("ApplicationInterface", new DefaultApplicationInterface());
            registry.bind("Chat", chat);
            logger.trace("Exported objects to RMI registry");
        }
        catch (RemoteException e)
        {
            logger.error("Failed to initialize RMI object", e);
        }
        catch (AlreadyBoundException e)
        {
            logger.error("Failed to initialize RMI objects", e);
        }
    }
    
    public DefaultChatServer getChat()
    {
    	return chat;
    }
    
    public static RmiManager getInstance()
    {
    	return rmiManager;
    }
}
