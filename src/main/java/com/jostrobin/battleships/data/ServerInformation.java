package com.jostrobin.battleships.data;

import java.net.InetAddress;

import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.session.ApplicationState;

public class ServerInformation
{
    private InetAddress address;

    private ApplicationState state;
    
    private ApplicationInterface applicationInterface;

    public ServerInformation()
    {
    }

    public ServerInformation(InetAddress address, ApplicationState state, ApplicationInterface applicationInterface)
    {
        this.address = address;
        this.state = state;
        this.applicationInterface = applicationInterface;
    }

    public InetAddress getAddress()
    {
        return address;
    }

    public void setAddress(InetAddress address)
    {
        this.address = address;
    }

    public ApplicationState getState()
    {
        return state;
    }

    public void setState(ApplicationState state)
    {
        this.state = state;
    }

    public ApplicationInterface getApplicationInterface()
	{
		return applicationInterface;
	}

	public void setApplicationInterface(ApplicationInterface applicationInterface)
	{
		this.applicationInterface = applicationInterface;
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
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        ServerInformation other = (ServerInformation) obj;
        if (address == null)
        {
            if (other.address != null)
            {
                return false;
            }
        }
        else if (!address.equals(other.address))
        {
            return false;
        }
        return true;
    }
}
