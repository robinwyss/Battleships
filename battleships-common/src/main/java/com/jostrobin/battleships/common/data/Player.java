package com.jostrobin.battleships.common.data;

/**
 * Contains the data describing a player.
 *
 * @author joscht
 */
public class Player
{
    private Long id;

    private String username;

    private GameState state = GameState.INIT;

    private GameData gameData;

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

}
