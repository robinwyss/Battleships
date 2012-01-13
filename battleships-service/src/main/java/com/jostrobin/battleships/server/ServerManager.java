package com.jostrobin.battleships.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.GameUpdate;
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
        Game game = new Game(gameIdGenerator.nextId(), command.getGameMode(), command.getMaxPlayers(),
                command.getFieldWidth(), command.getFieldLength());
        game.setNrOfAircraftCarriers(command.getNrOfAircraftCarriers());
        game.setNrOfBattleships(command.getNrOfBattleships());
        game.setNrOfDestroyers(command.getNrOfDestroyers());
        game.setNrOfSubmarines(command.getNrOfSubmarines());
        game.setNrOfPatrolBoats(command.getNrOfPatrolBoats());
        game.setOwner(client);
        game.addPlayer(client);
        client.setGame(game);
        client.initializeField(command.getFieldWidth(), command.getFieldLength());

        resendPlayerLists();
    }

    /**
     * Sends a chat message to every connected client.
     *
     * @param message
     */
    public void sendGlobalChatMessage(String message)
    {
        for (Client client : clients)
        {
            try
            {
                client.sendChatMessage("Info", message);
            }
            catch (IOException ignore)
            {
                logger.info("Could not send global chat message", ignore);
            }
        }
    }

    public void cancelGame(Client client)
    {
        // TODO: Send cancel game to every client of the game
        client.setGame(null);
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
        Game game = findGame(gameId);
        // clients can only join games of others
        if (game != null && !game.getOwner().equals(client))
        {
            boolean added = game.addPlayer(client);
            client.setGame(game);
            client.initializeField(game.getFieldWidth(), game.getFieldLength());
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
        }
        resendPlayerLists();
    }

    private Game findGame(Long gameId)
    {
        Game game = null;
        for (Client c : clients)
        {
            if (c.getGame() != null && c.getGame().getId().equals(gameId))
            {
                game = c.getGame();
            }
        }
        return game;
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

    public void attack(Client attackingClient, Long clientId, int x, int y)
    {
        Client attackedClient = getClientById(clientId);
        Game game = attackedClient.getGame();
        if (!game.getCurrentPlayer().equals(attackingClient))
        {
            logger.warn("Player {} tried to attack, but it is not his turn", attackedClient);
            return;
        }
        if (attackedClient != null && !attackingClient.isDestroyed())
        {
            AttackResult result = attackedClient.attack(x, y);
            if (AttackResult.SHIP_DESTROYED.equals(result))
            {
                if (attackedClient.isDestroyed())
                {
                    result = AttackResult.PLAYER_DESTROYED;
                    sendGlobalChatMessage(attackedClient.getUsername() + " has been destroyed");
                }
            }
            Player nextPlayer = game.getNextPlayer();
            notifyParticipants(attackingClient, attackedClient, x, y, nextPlayer, result);
        }
    }

    private void notifyParticipants(Client attacker, Client attacked, int x, int y, Player nextPlayer, AttackResult result)
    {
        List<Client> clients = attacked.getGame().getPlayers();
        boolean gameOver = true;
        for (Client client : clients)
        {
            gameOver &= client.isDestroyed() || client.equals(attacker);
        }
        for (Client toBeNotified : attacked.getGame().getPlayers())
        {
            Ship ship = null;
            if (result == AttackResult.SHIP_DESTROYED || result == AttackResult.PLAYER_DESTROYED)
            {
                // we also need to transmit the ship which has been destroyed
                ship = attacked.getShipAtPosition(x, y);
            }
            GameUpdate gameUpdate = null;
            if (gameOver)
            {
                gameUpdate = GameUpdate.PLAYER_HAS_WON;
            }
            if (result == AttackResult.PLAYER_DESTROYED)
            {
                gameUpdate = GameUpdate.PLAYER_HAS_BEEN_DESTROYED;
            }
            // TODO: continue here, send game update
            // TODO: remove GAME_STATE from Communication
            // TODO: a lot

            try
            {
                toBeNotified.sendAttackResult(x, y, result, ship, attacker.getId(), attacked.getId(), gameUpdate, nextPlayer.getId());
            }
            catch (Exception e)
            {
                logger.info("Client communication aborted.");
                removeClient(toBeNotified);
                resendPlayerLists();
            }
        }
    }

    public void sendGameUpdate(Client attacker, Client attacked, AttackResult attackResult)
    {
        if (attackResult == AttackResult.PLAYER_DESTROYED)
        {

        }
        for (Client client : attacked.getGame().getPlayers())
        {

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

    public void updateGameState(Game game)
    {
        boolean allPlayersReady = true;
        for (Client player : game.getPlayers())
        {
            allPlayersReady &= player.isReady();
        }
        if (allPlayersReady)
        {
            // all the players are ready, notify them. decide on who starts first
            Random random = new Random();
            int startIndex = random.nextInt(game.getPlayers().size());
            Player startingPlayer = game.getPlayers().get(startIndex);
            game.setCurrentPlayer(startingPlayer);
            for (Client client : game.getPlayers())
            {
                client.setState(GameState.RUNNING);
                client.sendStartGame(startingPlayer.getId());
            }
        }
    }

    /**
     * Returns the client with the specified id or null if it doesn't exist.
     *
     * @param id
     * @return
     */
    private Client getClientById(Long id)
    {
        for (Client client : clients)
        {
            if (client.getId().equals(id))
            {
                return client;
            }
        }
        return null;
    }
}
