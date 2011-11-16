package com.jostrobin.battleships.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import com.jostrobin.battleships.common.data.Player;

public class GameSelectionModel extends Observable
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
		// we need to remove our own entry
		Iterator<Player> it = players.iterator();
		while (it.hasNext())
		{
			Player player = it.next();
			if (player.getId().equals(clientId))
			{
				it.remove();
			}
		}
		setChanged();
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
