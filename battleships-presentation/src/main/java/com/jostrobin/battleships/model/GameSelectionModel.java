package com.jostrobin.battleships.model;

import java.util.ArrayList;
import java.util.List;

import com.jostrobin.battleships.common.data.Player;

public class GameSelectionModel
{
    private List<Player> players = new ArrayList<Player>();

    /**
     * Our own client id.
     */
    private Long clientId;

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
}
