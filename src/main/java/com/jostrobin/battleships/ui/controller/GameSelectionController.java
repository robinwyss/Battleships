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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.data.Configuration;
import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.enumerations.State;
import com.jostrobin.battleships.exception.BattleshipServiceException;
import com.jostrobin.battleships.service.network.detection.ServerDetectionListener;
import com.jostrobin.battleships.service.network.detection.ServerDetectionManager;
import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.service.network.rmi.RmiManager;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import com.jostrobin.battleships.service.network.rmi.chat.server.DefaultChatServer;
import com.jostrobin.battleships.session.ApplicationState;
import com.jostrobin.battleships.ui.frames.CreateGameFrame;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;

public class GameSelectionController implements ServerDetectionListener
{
    private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);

    private ServerDetectionManager serverDetectionManager;

    private GameSelectionFrame gameSelectionFrame;

    public GameSelectionController()
    {
        List<ServerDetectionListener> listeners = new ArrayList<ServerDetectionListener>();
        listeners.add(this);

        serverDetectionManager = new ServerDetectionManager(listeners);
        
        ApplicationState state = ApplicationState.getInstance();
        state.setServerDetectionManager(serverDetectionManager);
        
        Thread thread = new Thread(serverDetectionManager);
        thread.start();
    }

    public void refresh(boolean firstBroadcast)
    {
    	gameSelectionFrame.resetServerList();
        serverDetectionManager.broadcastFindGames(firstBroadcast);
    }

    public void exit()
    {
        System.exit(0);
    }

    @Override
    public void addServer(InetAddress address, String id)
    {
        ApplicationInterface applicationInterface;

        try
        {
        	RmiManager rmiManager = RmiManager.getInstance();
        	boolean serverExists = false;
        	for (ServerInformation server : rmiManager.getServers())
        	{
        		if (server.getAddress().equals(address))
        		{
                    // update existing information
        			ApplicationState state = server.getApplicationInterface().getApplicationState();
                    server.setState(state);
                    serverExists = true;
        		}
        	}
        	
        	// create a new server object
        	if (!serverExists)
        	{
                applicationInterface = rmiManager.findApplicationInterface(address, id);
                rmiManager.findChat(address, id);
                ApplicationState state = applicationInterface.getApplicationState();

                ServerInformation newServer = new ServerInformation(address, state, applicationInterface, id);
                rmiManager.getServers().add(newServer);
        		
        	}

            gameSelectionFrame.setServers(rmiManager.getServers());
        }
        catch (RemoteException e)
        {
            logger.error("Failed to connect to the server at {}", address, e);
        }
    }
    
    /**
     * Joins the specified server. Loads RMI objects and instantiates the game frame.
     * @param server
     */
    public void joinGame(ServerInformation server)
    {
    	ApplicationState state = ApplicationState.getInstance();
    	
    	InetAddress address = server.getAddress();
        try
        {
        	ApplicationInterface applicationInterface = server.getApplicationInterface();
        	if (applicationInterface.getApplicationState().getState() != State.WAITING_FOR_PLAYERS)
        	{
        		// we can only join games waiting for more players
        		return;
        	}
        	
        	Configuration configuration = Configuration.getInstance();
        	GameSettings settings = applicationInterface.joinGame(configuration.getId());
        	if (settings == null)
        	{
        		JOptionPane.showMessageDialog(null, "Could not join game.", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        	else
        	{
        		state.setSettings(settings);
        		
        		RmiManager manager = RmiManager.getInstance();
        		Configuration config = Configuration.getInstance();
	            Chat chatClient = manager.getChat(config.getId());
	
	            // connect our gui to the rmi objects
	            GameController gameController = new GameController(chatClient, applicationInterface);
	            gameController.showFrame();
	            
	            // forward our input to the other client
	            RmiManager rmiManager = RmiManager.getInstance();
	            DefaultChatServer chatServer = rmiManager.getChat();
	            chatServer.addListener(gameController.getChatListener());
	            
	            gameSelectionFrame.dispose();
        	}
        }
        catch (RemoteException e)
        {
            logger.error("Failed to join server at {}", address, e);
        }
    }
    
    public void createGame()
    {
        CreateGameController createGameController = new CreateGameController(serverDetectionManager);
        CreateGameFrame gameFrame = new CreateGameFrame(createGameController);
    }

	@Override
	public void updateServer(InetAddress address)
	{
		RmiManager rmiManager = RmiManager.getInstance();
		for (ServerInformation server : rmiManager.getServers())
		{
			if (server.getAddress().equals(address))
			{
				try
				{
					ApplicationInterface applicationInterface = server.getApplicationInterface();
					ApplicationState state = applicationInterface.getApplicationState();
					server.setState(state);
				}
				catch (RemoteException e)
				{
					throw new BattleshipServiceException("could not get state", e);
				}
			}
		}
		gameSelectionFrame.setServers(rmiManager.getServers());
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
