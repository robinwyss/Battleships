package com.jostrobin.battleships.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.game.Game;
import com.jostrobin.battleships.server.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerManager
{
    private static final Logger logger = LoggerFactory.getLogger(ServerManager.class);

    private List<Client> clients = new ArrayList<Client>();

    private IdGenerator gameIdGenerator = new IdGenerator();

    public void addClient(Client client)
    {
        logger.debug("New client registered: {}", client);
        clients.add(client);

        resendPlayerLists();
    }

    /**
     * Removes the client from the list of clients. Does not resend the
     * list of players.
     *
     * @param client
     */
    public void removeClient(Client client)
    {
        logger.debug("Player removed: {}", client);
        // TODO: What else do we need to do?
        // notify running games...
        clients.remove(client);
    }

    /**
     * The specified player has started a new game. Notify everyone.
     *
     * @param client
     */
    public void createGame(Client client, Command command)
    {
        Game game = new Game(gameIdGenerator.nextId(), command.getGameMode(), 0, command.getMaxPlayers(),
                command.getFieldWidth(), command.getFieldLength());
        game.setOwner(client);
        game.addPlayer(client);
        client.setGame(game);
        
        client.initializeField(command.getFieldWidth(), command.getFieldLength());

        resendPlayerLists();
    }

    /**
     * The specified client wants to join the specified game.
     *
     * @param client
     * @param gameId
     */
    public synchronized void joinGame(Client client, Long gameId)
    {
        Game game = null;
        for (Client c : clients)
        {
            if (c.getGame() != null && c.getGame().getId().equals(gameId))
            {
                game = c.getGame();
            }
        }
        // clients can only join games of others
        if (game != null && !game.getOwner().equals(client))
        {
            boolean added = game.addPlayer(client);
            if (added && game.getCurrentPlayers() == game.getMaxPlayers())
            {
                // the game is full, we can start it
                try
                {
                    game.prepareGame();
                }
                catch (IOException e)
                {
                    removeClient(client);
                }
            }
            client.setGame(game);
        }
        resendPlayerLists();
    }

    /**
     * Sends the lists of players to all the clients.
     */
    public void resendPlayerLists()
    {
        boolean resend = false;
        for (Client c : clients)
        {
            try
            {
                c.sendAvailablePlayers(clients);
            }
            catch (IOException e)
            {
                logger.info("Client communication aborted");
                removeClient(c);
                resend = true;
            }
        }
        if (resend)
        {
            resendPlayerLists();
        }
    }

    public String getServerStatus()
    {
        String status = "";

        List<Long> gameIds = new ArrayList<Long>();
        for (Client client : clients)
        {
            if (client.getGame() != null)
            {
                if (!gameIds.contains(client.getGame().getId()))
                {
                    gameIds.add(client.getGame().getId());
                }
            }
        }

        status += "\nCurrently " + clients.size() + " clients are connected.\n";
        status += gameIds.size() + " games running in total.";

        return status;
    }
}
