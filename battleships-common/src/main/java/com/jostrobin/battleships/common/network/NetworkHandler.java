package com.jostrobin.battleships.common.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Orientation;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.GameUpdate;
import com.jostrobin.battleships.common.data.enums.ShipType;

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
    private boolean running = true;

    public void init(DataInputStream inputStream) throws IOException
    {
        this.inputStream = inputStream;
    }

    @Override
    public void run()
    {
        String username = null;
        try
        {
            while (running)
            {
                int startingCharacter = inputStream.readInt();
                Command command = new Command(startingCharacter);

                // some commands are followed by more information, fetch these information
                switch (startingCharacter)
                {
                    case Command.LOGIN:
                        logger.debug("receiving LOGIN...");
                        username = inputStream.readUTF();
                        command.setUsername(username);
                        logger.debug("received LOGIN(username: {})", username);
                        break;
                    // the user wants to create a new game
                    case Command.CREATE_GAME:
                        logger.debug("receiving CREATE_GAME...");
                        String mode = inputStream.readUTF();
                        int maxPlayers = inputStream.readInt();
                        int fieldWidth = inputStream.readInt();
                        int fieldLength = inputStream.readInt();
                        int aircraftCarriers = inputStream.readInt();
                        int battleships = inputStream.readInt();
                        int destroyers = inputStream.readInt();
                        int submarines = inputStream.readInt();
                        int patrolBoats = inputStream.readInt();
                        GameMode gameMode = GameMode.fromString(mode);

                        command.setGameMode(gameMode);
                        command.setMaxPlayers(maxPlayers);
                        command.setFieldWidth(fieldWidth);
                        command.setFieldLength(fieldLength);
                        command.setNrOfAircraftCarriers(aircraftCarriers);
                        command.setNrOfBattleships(battleships);
                        command.setNrOfDestroyers(destroyers);
                        command.setNrOfSubmarines(submarines);
                        command.setNrOfPatrolBoats(patrolBoats);
                        logger.debug("received CREATE_GAME");
                        break;
                    case Command.JOIN_GAME:
                        logger.debug("receiving JOIN_GAME...");
                        Long gameId = inputStream.readLong();
                        command.setGameId(gameId);
                        logger.debug("received JOIN_GAME");
                        break;
                    case Command.PLAYERS_LIST:
                        logger.debug("receiving PLAYERS_LIST...");
                        int nrOfPlayers = inputStream.readInt();
                        List<Player> players = new ArrayList<Player>();
                        logger.debug("player list size: {}", nrOfPlayers);
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
                                gameData = new GameData(remoteGameId, parsedMode, remoteMaxPlayers,
                                        remoteFieldWidth, remoteFieldLength);
                                gameData.setCurrentPlayers(currentPlayers);
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
                        logger.debug("received PLAYERS_LIST");
                        break;
                    case Command.ACCEPTED:
                        logger.debug("receiving Command.ACCEPTED...");
                        Long myClientId = inputStream.readLong();
                        command.setClientId(myClientId);
                        logger.debug("received Command.ACCEPTED");
                        break;
                    case Command.ATTACK_RESULT:
                        logger.debug("receiving ATTACK_RESULT...");
                        command.setX(inputStream.readInt());
                        command.setY(inputStream.readInt());
                        AttackResult attackResult = AttackResult.valueOf(inputStream.readUTF());
                        command.setAttackResult(attackResult);
                        if (attackResult == AttackResult.SHIP_DESTROYED || attackResult == AttackResult.PLAYER_DESTROYED)
                        {
                        	Ship ship = readShip();
                        	ship.setShipDestroyed(true);
                            command.setShip(ship);
                        }
                        command.setAttackingClient(inputStream.readLong());
                        command.setAttackedClient(inputStream.readLong());
                        command.setGameUpdate(GameUpdate.valueOf(inputStream.readUTF()));
                        command.setClientId(inputStream.readLong());
                        logger.debug("received ATTACK_RESULT");
                        break;
                    case Command.PREPARE_GAME:
                        logger.debug("receiving PREPARE_GAME...");
                        int length = inputStream.readInt();
                        int width = inputStream.readInt();
                        int nrAircraftCarriers = inputStream.readInt();
                        int nrBattleships = inputStream.readInt();
                        int nrDestroyers = inputStream.readInt();
                        int nrSubmarines = inputStream.readInt();
                        int nrPatrolBoats = inputStream.readInt();
                        command.setFieldLength(length);
                        command.setFieldWidth(width);
                        command.setNrOfAircraftCarriers(nrAircraftCarriers);
                        command.setNrOfBattleships(nrBattleships);
                        command.setNrOfDestroyers(nrDestroyers);
                        command.setNrOfSubmarines(nrSubmarines);
                        command.setNrOfPatrolBoats(nrPatrolBoats);
                        int numberOfClients = inputStream.readInt();
                        Map<Long, String> participants = new HashMap<Long, String>();
                        logger.debug("participant list size: {}", numberOfClients);
                        for (int i = 0; i < numberOfClients; i++)
                        {
                            Long id = inputStream.readLong();
                            String placeholderUsername = inputStream.readUTF();
                            participants.put(id, placeholderUsername);
                        }
                        command.setParticipants(participants);
                        logger.debug("received PREPARE_GAME");
                        break;
                    case Command.CHAT_MESSAGE:
                        logger.debug("receiving CHAT_MESSAGE...");
                        username = inputStream.readUTF();
                        String message = inputStream.readUTF();
                        command.setUsername(username);
                        command.setMessage(message);
                        logger.debug("received CHAT_MESSAGE");
                        break;
                    case Command.ATTACK:
                        logger.debug("receiving ATTACK...");
                        command.setX(inputStream.readInt());
                        command.setY(inputStream.readInt());
                        command.setClientId(inputStream.readLong());
                        logger.debug("received ATTACK");
                        break;
                    case Command.SET_SHIPS:
                        logger.debug("receiving SET_SHIPS...");
                        int nbrOfShips = inputStream.readInt();
                        List<Ship> ships = new ArrayList<Ship>();
                        logger.debug("ship list size: {}", nbrOfShips);
                        for (int i = 0; i < nbrOfShips; i++)
                        {
                            Ship ship = readShip();
                            ships.add(ship);
                        }
                        command.setShips(ships);
                        logger.debug("received SET_SHIPS");
                        break;
                    case Command.START_GAME:
                        logger.debug("receiving START_GAME...");
                        Long startingPlayer = inputStream.readLong();
                        command.setStartingPlayer(startingPlayer);
                        logger.debug("received START_GAME");
                        break;
                    default:
                        logger.warn("Bullshit came over the wire: {}", startingCharacter);
                        break;
                }
                notifyListeners(command);
            }
        }
        catch (IOException e)
        {
            logger.warn("Communication to client aborted");
            notifyListeners(null);
        }
        catch (Exception e)
        {
            logger.error("something really bad happened my friend, check da stacktrace", e);
        }
    }

    private Ship readShip() throws IOException
    {
        Ship ship = new Ship(ShipType.PATROL_BOAT);
        ship.setPositionX(inputStream.readInt());
        ship.setPositionY(inputStream.readInt());
        ship.setSize(inputStream.readInt());
        Orientation orientation = Orientation.valueOf(inputStream.readUTF());
        ship.setOrientation(orientation);
        ShipType shipType = ShipType.valueOf(inputStream.readUTF());
        ship.setType(shipType);
        return ship;
    }

    /**
     * Forwards the command to all the registered ClientListeners.
     *
     * @param command
     */
    private void notifyListeners(Command command)
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

    public void stop()
    {
        running = false;
    }
}
