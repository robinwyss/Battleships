package com.jostrobin.battleships.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.springframework.scheduling.annotation.Async;

import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.server.client.AttackResult;
import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.client.Ship;
import com.jostrobin.battleships.server.game.Game;

/**
 * This class is used to send commands to a client.
 *
 * @author joscht
 */
public class ClientWriter implements Writer
{
    private DataOutputStream outputStream;

    public void init(Socket socket) throws IOException
    {
        this.outputStream = new DataOutputStream(socket.getOutputStream());
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
    }

    /**
     * Players can start placing their ships.
     *
     * @throws IOException
     */
    @Override
    @Async
    public void sendPrepareGame() throws IOException
    {
        outputStream.writeInt(Command.PREPARE_GAME);
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
        outputStream.writeInt(Command.ACCEPTED);
        outputStream.writeLong(id);
    }

	@Override
	@Async
	public void sendChatMessage(String username, String message)
			throws IOException
	{
		outputStream.writeInt(Command.CHAT_MESSAGE);
		outputStream.writeUTF(username);
		outputStream.writeUTF(message);
	}

	@Override
	@Async
	public void sendAttackResult(Long clientId, int x, int y, AttackResult result, Ship ship)
			throws Exception
	{
		outputStream.writeInt(Command.ATTACK_RESULT);
		outputStream.writeInt(x);
		outputStream.writeInt(y);
		outputStream.writeUTF(result.name());
		if (result == AttackResult.SHIP_DESTROYED)
		{
//			outputStream.writeUTF(ship.getShipType().name()); // TODO: ADD SHIP TYPE
			outputStream.writeInt(ship.getX());
			outputStream.writeInt(ship.getY());
			outputStream.writeInt(ship.getSize());
			outputStream.writeUTF(ship.getOrientation().name());
		}
	}
    
    
}
