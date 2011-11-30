package com.jostrobin.battleships.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.server.client.Client;

/**
 * Extends the GameData with server side used objects.
 *
 * @author joscht
 */
public class Game extends GameData
{
    public Game(Long id, GameMode mode, int currentPlayers, int maxPlayers, int fieldWidth,
                int fieldLength)
    {
        super(id, mode, currentPlayers, maxPlayers, fieldWidth, fieldLength);
    }

    private List<Client> players = new ArrayList<Client>();

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
            added = true;
        }
        return added;
    }

    public void prepareGame() throws IOException
    {
        for (Client client : players)
        {
            client.setState(GameState.PREPARING);
            client.prepareGame();
        }
    }

    public void notifyAboutChatMessage(String username, String message) throws IOException
    {
        for (Client player : players)
        {
            player.sendChatMessage(username, message);
        }
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
}
