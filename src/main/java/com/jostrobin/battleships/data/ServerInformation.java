package com.jostrobin.battleships.data;

import java.net.InetAddress;

import com.jostrobin.battleships.service.network.rmi.GameState;

public class ServerInformation
{
	private InetAddress address;
	
	private GameState state;

	public ServerInformation()
	{
	}

	public ServerInformation(InetAddress address, GameState state)
	{
		this.address = address;
		this.state = state;
	}

	public InetAddress getAddress()
	{
		return address;
	}

	public void setAddress(InetAddress address)
	{
		this.address = address;
	}

	public GameState getState()
	{
		return state;
	}

	public void setState(GameState state)
	{
		this.state = state;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerInformation other = (ServerInformation) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		return true;
	}
}
