package com.jostrobin.battleships.common.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;

/**
 * The NetworkHandler is responsible for accepting and parsing commands via network and forwarding them to the
 * NetworkListeners.
 *
 * @author joscht
 */
public class NetworkHandler implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(NetworkHandler.class);

    private List<NetworkListener> listeners = new ArrayList<NetworkListener>();

    private DataInputStream inputStream;

    public void init(Socket socket) throws IOException
    {
        inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run()
    {
        String username = null;
        try
        {
            boolean running = true;
            while (running)
            {
                int startingCharacter = inputStream.readInt();
                Command command = new Command(startingCharacter);

                // some commands are followed by more information, fetch these information
                switch (startingCharacter)
                {
                    case Command.LOGIN:
                        username = inputStream.readUTF();
                        command.setUsername(username);
                        break;

                    // the user wants to create a new game
                    case Command.CREATE_GAME:
                        String mode = inputStream.readUTF();
                        int maxPlayers = inputStream.readInt();
                        int fieldWidth = inputStream.readInt();
                        int fieldLength = inputStream.readInt();
                        GameMode gameMode = GameMode.fromString(mode);

                        command.setGameMode(gameMode);
                        command.setMaxPlayers(maxPlayers);
                        command.setFieldWidth(fieldWidth);
                        command.setFieldLength(fieldLength);

                        break;

                    case Command.JOIN_GAME:
                        Long gameId = inputStream.readLong();
                        command.setGameId(gameId);
                        break;

                    case Command.PLAYERS_LIST:
                        int nrOfPlayers = inputStream.readInt();
                        List<Player> players = new ArrayList<Player>();
                        for (int i = 0; i < nrOfPlayers; i++)
                        {
                            Long clientId = inputStream.readLong();
                            String name = inputStream.readUTF();
                            String state = inputStream.readUTF();
                            boolean hasGame = inputStream.readBoolean();
                            GameData gameData = null;
                            if (hasGame)
                            {
                                Long remoteGameId = inputStream.readLong();
                                String remotGameMode = inputStream.readUTF();
                                GameMode parsedMode = GameMode.fromString(remotGameMode);
                                int currentPlayers = inputStream.readInt();
                                int remoteMaxPlayers = inputStream.readInt();
                                int remoteFieldWidth = inputStream.readInt();
                                int remoteFieldLength = inputStream.readInt();
                                gameData = new GameData(remoteGameId, parsedMode, currentPlayers, remoteMaxPlayers,
                                        remoteFieldWidth, remoteFieldLength);
                            }
                            Player player = new Player();
                            player.setId(clientId);
                            player.setUsername(name);
                            GameState parsedState = GameState.fromString(state);
                            player.setState(parsedState);
                            player.setGameData(gameData);
                            players.add(player);
                        }
                        command.setPlayers(players);
                        break;

                    case Command.ACCEPTED:
                        Long myClientId = inputStream.readLong();
                        command.setClientId(myClientId);
                        break;

                    case Command.CHAT_MESSAGE:
                        username = inputStream.readUTF();
                        String message = inputStream.readUTF();
                        command.setUsername(username);
                        command.setMessage(message);
                        break;
                        
                    case Command.ATTACK:
                    	command.setX(inputStream.readInt());
                    	command.setY(inputStream.readInt());
                    	command.setClientId(inputStream.readLong());
                    	break;
                }

                notifyAll(command);
            }
        }
        catch (IOException e)
        {
            logger.warn("Communication to client aborted");
            notifyAll(null);
        }
    }

    /**
     * Forwards the command to all the registered ClientListeners.
     *
     * @param command
     */
    private void notifyAll(Command command)
    {
        for (NetworkListener listener : listeners)
        {
            listener.notify(command);
        }
    }

    public void addNetworkListener(NetworkListener listener)
    {
        listeners.add(listener);
    }

}
