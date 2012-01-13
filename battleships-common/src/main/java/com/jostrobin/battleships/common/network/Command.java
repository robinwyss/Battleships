package com.jostrobin.battleships.common.network;

import java.util.List;
import java.util.Map;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.GameUpdate;

public class Command
{
    public static final int LOGIN = 0;

    /**
     * The list of available players follows.
     */
    public static final int PLAYERS_LIST = 1;

    /**
     * The player sends this when he creates a new game, followed by the settings.
     */
    public static final int CREATE_GAME = 2;

    /**
     * The player sends this when he wants to join the game specified by the following game id.
     */
    public static final int JOIN_GAME = 3;

    /**
     * Notifies the players that the game is full and they can start placing ships.
     */
    public static final int PREPARE_GAME = 4;

    /**
     * Notifies a player that he is accepted and sends him his client id.
     */
    public static final int ACCEPTED = 5;

    /**
     * A chat message follows (bidirectional, can come from the server or the client).
     */
    public static final int CHAT_MESSAGE = 6;

    /**
     * Sent when a player has finished placing ships, followed by the placements.
     */
    public static final int SET_SHIPS = 7;

    /**
     * A player wants to attack at the specified position.
     */
    public static final int ATTACK = 8;

    /**
     * The result of an attack broadcasted to all the participants.
     */
    public static final int ATTACK_RESULT = 9;

    /**
     * All the players have set their ships, the game can start.
     */
    public static final int START_GAME = 10;

    /**
     * Cancels a game if the user has started one. Ignored if the user has not started a game.
     */
    public static final int CANCEL_GAME = 11;

    public static final int DISCONNECT = 100;

    private int command;

    private String username;

    private Long gameId;

    private GameMode gameMode;

    private GameUpdate gameUpdate;

    private int maxPlayers;

    private int fieldWidth;

    private int fieldLength;

    private List<Player> players;

    private Map<Long, String> participants;

    private Long clientId;

    private String message;

    private Integer x;

    private Integer y;

    private List<Ship> ships;

    private Long startingPlayer;

    private AttackResult attackResult;

    private Ship ship;

    private int nrOfAircraftCarriers;

    private int nrOfDestroyers;

    private int nrOfBattleships;

    private int nrOfSubmarines;

    private int nrOfPatrolBoats;

    private Long attackingClient;

    private Long attackedClient;

    public Command(int command)
    {
        this.command = command;
    }

    public int getCommand()
    {
        return command;
    }

    public void setCommand(int command)
    {
        this.command = command;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Long getGameId()
    {
        return gameId;
    }

    public void setGameId(Long gameId)
    {
        this.gameId = gameId;
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getFieldWidth()
    {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth)
    {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldLength()
    {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength)
    {
        this.fieldLength = fieldLength;
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(List<Player> players)
    {
        this.players = players;
    }

    public Long getClientId()
    {
        return clientId;
    }

    public void setClientId(Long clientId)
    {
        this.clientId = clientId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Integer getX()
    {
        return x;
    }

    public void setX(Integer x)
    {
        this.x = x;
    }

    public Integer getY()
    {
        return y;
    }

    public void setY(Integer y)
    {
        this.y = y;
    }

    public void setShips(List<Ship> ships)
    {
        this.ships = ships;
    }

    public List<Ship> getShips()
    {
        return ships;
    }

    public void setStartingPlayer(Long startingPlayer)
    {
        this.startingPlayer = startingPlayer;
    }

    public Long getStartingPlayer()
    {
        return startingPlayer;
    }

    public Map<Long, String> getParticipants()
    {
        return participants;
    }

    public void setParticipants(Map<Long, String> participants)
    {
        this.participants = participants;
    }

    public void setAttackResult(AttackResult attackResult)
    {
        this.attackResult = attackResult;
    }

    public AttackResult getAttackResult()
    {
        return attackResult;
    }

    public void setShip(Ship ship)
    {
        this.ship = ship;
    }

    public Ship getShip()
    {
        return ship;
    }

    public int getNrOfAircraftCarriers()
    {
        return nrOfAircraftCarriers;
    }

    public void setNrOfAircraftCarriers(int nrOfAircraftCarriers)
    {
        this.nrOfAircraftCarriers = nrOfAircraftCarriers;
    }

    public int getNrOfDestroyers()
    {
        return nrOfDestroyers;
    }

    public void setNrOfDestroyers(int nrOfDestroyers)
    {
        this.nrOfDestroyers = nrOfDestroyers;
    }

    public int getNrOfBattleships()
    {
        return nrOfBattleships;
    }

    public void setNrOfBattleships(int nrOfBattleships)
    {
        this.nrOfBattleships = nrOfBattleships;
    }

    public int getNrOfSubmarines()
    {
        return nrOfSubmarines;
    }

    public void setNrOfSubmarines(int nrOfSubmarines)
    {
        this.nrOfSubmarines = nrOfSubmarines;
    }

    public int getNrOfPatrolBoats()
    {
        return nrOfPatrolBoats;
    }

    public void setNrOfPatrolBoats(int nrOfPatrolBoats)
    {
        this.nrOfPatrolBoats = nrOfPatrolBoats;
    }

    public Long getAttackedClient()
    {
        return attackedClient;
    }

    public void setAttackedClient(Long attackedClient)
    {
        this.attackedClient = attackedClient;
    }

    public Long getAttackingClient()
    {
        return attackingClient;
    }

    public void setAttackingClient(Long attackingClient)
    {
        this.attackingClient = attackingClient;
    }

    public GameUpdate getGameUpdate()
    {
        return gameUpdate;
    }

    public void setGameUpdate(GameUpdate gameUpdate)
    {
        this.gameUpdate = gameUpdate;
    }
}
