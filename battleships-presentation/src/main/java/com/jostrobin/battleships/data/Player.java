package com.jostrobin.battleships.data;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:26
 */
public class Player
{
    private String Name;
    private String ipAddress;

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
}
