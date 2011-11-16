package com.jostrobin.battleships.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.server.ServerManager;
import com.jostrobin.battleships.server.game.Game;
import com.jostrobin.battleships.server.network.ClientWriter;

/**
 * Represents a client.  Extends the player data with server side used objects.
 * @author joscht
 *
 */
public class Client extends Player implements NetworkListener
{
	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	
	private Socket socket;
	
	private ServerManager serverManager;
	
	private ClientWriter clientWriter;
	
	/**
	 * If this client has started a game.
	 */
	private Game game;

	public Client(ServerManager serverManager, Socket socket, Long id, String username)
	{
		super(id, username);
		this.serverManager = serverManager;
		this.socket = socket;
	}
	
	/**
	 * Starts a new Thread to handle client input.
	 * @throws IOException
	 */
	public void startup() throws IOException
	{
		clientWriter = new ClientWriter(socket);
		
		NetworkHandler handler = new NetworkHandler(socket);
		handler.addNetworkListener(this);
		
		Thread thread = new Thread(handler);
		thread.start();
	}

	@Override
	public void notify(Command command)
	{
		if (command != null)
		{
			switch (command.getCommand())
			{
			case Command.LOGIN:
				try
				{
					clientWriter.acceptPlayer(getId());
					setUsername(command.getUsername());
					serverManager.addClient(this);
				}
				catch (IOException e)
				{
					logger.debug("Client lost before accepting");
					// nothing else to do
				}
				break;
			case Command.CREATE_GAME:
				setState(GameState.WAITING_FOR_PLAYERS);
				
				serverManager.createGame(this, command);
				break;
			case Command.JOIN_GAME:
				serverManager.joinGame(this, command.getGameId());
				break;
			}
		}
		else
		{
			// there was an error in communication
			serverManager.removeClient(this);
			serverManager.resendPlayerLists();
		}
	}
	
	public void sendAvailablePlayers(List<Client> clients) throws IOException
	{
		clientWriter.sendAvailablePlayers(clients);
	}
	
	/**
	 * Called when the game is full and players can start placing their ships.
	 * @throws IOException 
	 */
	public void prepareGame() throws IOException
	{
		clientWriter.sendPrepareGame();
	}

	@Override
	public String toString()
	{
		return "Client [username=" + getUsername() + ", id=" + getId()
				+ ", state=" + getState() + "]";
	}

	public Game getGame()
	{
		return game;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (getId() == null)
		{
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
