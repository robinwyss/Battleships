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

import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import com.jostrobin.battleships.service.network.rmi.chat.server.DefaultChatServer;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 17:10
 */
public class RmiManager
{
    private static final Logger logger = LoggerFactory.getLogger(RmiManager.class);
    
    private static final RmiManager rmiManager = new RmiManager();
    
    private DefaultChatServer chat;
    
    private Map<String, ApplicationInterface> remoteInterfaces = new HashMap<String, ApplicationInterface>();
    
    private Map<String, Chat> remoteChats = new HashMap<String, Chat>();

    private List<ServerInformation> servers = new ArrayList<ServerInformation>();
    
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
    
    public List<ServerInformation> getServers()
    {
    	return servers;
    }
    
    public ServerInformation getServerById(String id)
    {
    	for (ServerInformation server : servers)
    	{
    		if (server.getId().equals(id))
    		{
    			return server;
    		}
    	}
    	return null;
    }
    
    /**
     * Locates the ApplicationInterface for the remote server at the specified address with the specified id.
     * @param address
     * @param id
     * @return
     * @throws RemoteException 
     */
    public synchronized ApplicationInterface findApplicationInterface(InetAddress address, String id)
    {
		ApplicationInterface appInterface = null;
		try
		{
			Registry registry = LocateRegistry.getRegistry(address.getHostName());
			appInterface = (ApplicationInterface) registry.lookup("ApplicationInterface");
			this.remoteInterfaces.put(id, appInterface);
		}
		catch (Exception e)
		{
			logger.error("Can't get remote app interface", e);
		}
		return appInterface;
    }
    
    /**
     * Returns the ApplicationInterface belonging to the specified id or null if it does not exist.
     * @param id
     * @return
     */
    public ApplicationInterface getApplicationInterface(String id)
    {
    	if (remoteInterfaces.containsKey(id))
    	{
    		return remoteInterfaces.get(id);
    	}
    	return null;
    }
    
    /**
     * Locates the chat for the remote server at the specified address with the specified id.
     * @param address
     * @param id
     * @return
     * @throws RemoteException 
     */
    public synchronized Chat findChat(InetAddress address, String id)
    {
		Chat chat = null;
		try
		{
			Registry registry = LocateRegistry.getRegistry(address.getHostName());
			chat = (Chat) registry.lookup("Chat");
			this.remoteChats.put(id, chat);
		}
		catch (Exception e)
		{
			logger.error("Can't get remote app interface", e);
		}
		return chat;
    }
    
    /**
     * Returns the chat belonging to the specified id or null if it does not exist.
     * @param id
     * @return
     */
    public Chat getChat(String id)
    {
    	if (remoteChats.containsKey(id))
    	{
    		return remoteChats.get(id);
    	}
    	return null;
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
