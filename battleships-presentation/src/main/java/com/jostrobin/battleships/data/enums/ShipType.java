package com.jostrobin.battleships.data.enums;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:34
 */
public enum ShipType
{
    AIRCRAFT_CARRIER("Aircraft carrier", 5),
    BATTLESHIP("Battleship", 4),
    DESTROYER("Destroyer", 3),
    SUBMARINE("Submarine", 3),
    PATROL_BOAT("Patrol boat", 2);

    private String name;
    private int length;

    private ShipType(String name, int length)
    {
        this.name = name;
        this.length = length;
    }

    public String getName()
    {
        return name;
    }

    public int getLength()
    {
        return length;
    }

}
