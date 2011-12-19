package com.jostrobin.battleships.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.game.Game;

/**
 * This class is used to send commands to a client.
 *
 * @author joscht
 */
public class ClientWriter implements Writer
{
    private DataOutputStream outputStream;

    private static Logger logger = LoggerFactory.getLogger(ClientWriter.class);

    public void init(DataOutputStream outputStream) throws IOException
    {
    	this.outputStream = outputStream;
    }

    /**
     * Sends the list of all the players online to the client.
     *
     * @throws IOException
     */
    @Override
    @Async
    public void sendAvailablePlayers(List<Client> clients) throws IOException
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.PLAYERS_LIST);
            outputStream.writeInt(clients.size());
            for (Client client : clients)
            {
                outputStream.writeLong(client.getId());
                outputStream.writeUTF(client.getUsername());
                outputStream.writeUTF(client.getState().name());
                Game game = client.getGame();
                outputStream.writeBoolean(game != null);
                if (game != null)
                {
                    outputStream.writeLong(game.getId());
                    outputStream.writeUTF(game.getMode().name());
                    outputStream.writeInt(game.getCurrentPlayers());
                    outputStream.writeInt(game.getMaxPlayers());
                    outputStream.writeInt(game.getFieldWidth());
                    outputStream.writeInt(game.getFieldLength());
                }
            }
            outputStream.flush();
            logger.debug("sent PLAYERS_LIST");
        }
    }

    /**
     * Players can start placing their ships.
     *
     * @param fieldLength
     * @param fieldWidth
     * @param participants the clientIds of all the participants
     * @throws IOException
     */
    @Override
    @Async
    public void sendPrepareGame(Game game, List<Long> participants) throws IOException
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.PREPARE_GAME);
            outputStream.writeInt(game.getFieldLength());
            outputStream.writeInt(game.getFieldWidth());
            outputStream.writeInt(game.getNrOfAircraftCarriers());
            outputStream.writeInt(game.getNrOfBattleships());
            outputStream.writeInt(game.getNrOfDestroyers());
            outputStream.writeInt(game.getNrOfSubmarines());
            outputStream.writeInt(game.getNrOfPatrolBoats());
            outputStream.writeInt(participants.size());
            for (Long id : participants)
            {
                outputStream.writeLong(id);
                outputStream.writeUTF("placeholderusername");
            }
            outputStream.flush();
            logger.debug("sent PREPARE_GAME");
        }
    }

    /**
     * If a player has been accepted, send him his client id.
     *
     * @param id
     * @throws IOException
     */
    @Override
    @Async
    public void acceptPlayer(Long id) throws IOException
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.ACCEPTED);
            outputStream.writeLong(id);
            outputStream.flush();
            logger.debug("sent ACCEPTED");
        }
    }

    @Override
    @Async
    public void sendChatMessage(String username, String message)
            throws IOException
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.CHAT_MESSAGE);
            outputStream.writeUTF(username);
            outputStream.writeUTF(message);
            outputStream.flush();
            logger.debug("sent CHAT_MESSAGE");
        }
    }

    @Override
    @Async
    public void sendAttackResult(Long clientId, int x, int y, AttackResult result, Ship ship, Long nextPlayer)
            throws Exception
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.ATTACK_RESULT);
            outputStream.writeInt(x);
            outputStream.writeInt(y);
            outputStream.writeUTF(result.name());
            if (ship != null)
            {
                outputStream.writeInt(ship.getPositionX());
                outputStream.writeInt(ship.getPositionY());
                outputStream.writeInt(ship.getSize());
                outputStream.writeUTF(ship.getOrientation().name());
                outputStream.writeUTF(ship.getType().name());
            }
            outputStream.writeLong(nextPlayer);
            outputStream.flush();
            logger.debug("sent ATTACK_RESULT");
        }
    }

    @Override
    @Async
    public void sendStartGame(Long clientId) throws Exception
    {
        synchronized (outputStream)
        {
            outputStream.writeInt(Command.START_GAME);
            outputStream.writeLong(clientId);
            outputStream.flush();
            logger.debug("sent START_GAME");
        }
    }
}
