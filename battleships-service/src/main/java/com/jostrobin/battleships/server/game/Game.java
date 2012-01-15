package com.jostrobin.battleships.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.server.client.Client;

/**
 * Extends the GameData with server side used objects.
 *
 * @author joscht
 */
public class Game extends GameData
{
    public Game(Long id, GameMode mode, int maxPlayers, int fieldWidth,
                int fieldLength)
    {
        super(id, mode, maxPlayers, fieldWidth, fieldLength);
    }

    private List<Client> players = new ArrayList<Client>();

    private Player currentPlayer;
    private List<Client> destroyedPlayers = new ArrayList<Client>();

    /**
     * The player who started the game.
     */
    private Client owner;


    /**
     * Adds the specified client to the game, if there is enough open player slots.
     *
     * @param client
     * @return True if the client could be added.
     */
    public synchronized boolean addPlayer(Client client)
    {
        boolean added = false;
        if (players.size() < getMaxPlayers())
        {
            this.players.add(client);
            setCurrentPlayers(getCurrentPlayers() + 1);

            // set him as current player if there is none so far
            if (currentPlayer == null)
            {
                currentPlayer = client;
            }
            added = true;
        }
        return added;
    }
    
    public void removePlayer(Client client)
    {
    	players.remove(client);
    	setCurrentPlayers(getCurrentPlayers()-1);
    }

    /**
     * Notifies all the clients of this game that they can start preparing.
     *
     * @throws IOException
     */
    public void prepareGame() throws IOException
    {
        Map<Long, String> participants = new HashMap<Long, String>();
        for (Client client : players)
        {
            participants.put(client.getId(), client.getUsername());
        }
        for (Client client : players)
        {
            client.setState(GameState.PREPARING);
            client.prepareGame(participants);
        }
    }

    public void notifyAboutChatMessage(String username, String message) throws IOException
    {
        for (Client player : players)
        {
            player.sendChatMessage(username, message);
        }
    }
    
    /**
     * If a player disappeared, close the game.
     */
    public void closeGame()
    {
    	for (Client client : players)
    	{
    		client.sendCloseGame();
    		client.setGame(null);
    		client.setState(GameState.NEW);
    		client.setGameData(null);
    	}
    	owner = null;
    	players.clear();
    }

    public List<Client> getPlayers()
    {
        return players;
    }

    public Client getOwner()
    {
        return owner;
    }

    public void setOwner(Client owner)
    {
        this.owner = owner;
    }

    public Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    public List<Client> getDestroyedPlayers()
    {
        return destroyedPlayers;
    }

    public void addDestroyedPlayer(Client player)
    {
        destroyedPlayers.add(player);
    }

    public Player getNextPlayer()
    {
        players.removeAll(destroyedPlayers);
        int index = players.indexOf(currentPlayer);
        int nextPlayerIndex = ++index % players.size();
        Player nextPlayer = players.get(nextPlayerIndex);
        setCurrentPlayer(nextPlayer);
        return nextPlayer;
    }
}
