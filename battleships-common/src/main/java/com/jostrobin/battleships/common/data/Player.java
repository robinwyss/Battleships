package com.jostrobin.battleships.common.data;

import java.util.List;

/**
 * Contains the data describing a player.
 *
 * @author joscht
 */
public class Player
{
    private Long id;

    private String username;

    private List<Ship> ships;

    private GameState state = GameState.INIT;

    private GameData gameData;

    public boolean isReady()
    {
        return ships != null && !ships.isEmpty();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public GameState getState()
    {
        return state;
    }

    public void setState(GameState state)
    {
        this.state = state;
    }

    public GameData getGameData()
    {
        return gameData;
    }

    public void setGameData(GameData gameData)
    {
        this.gameData = gameData;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<Ship> getShips()
    {
        return ships;
    }

    public void setShips(List<Ship> ships)
    {
        this.ships = ships;
    }
}
