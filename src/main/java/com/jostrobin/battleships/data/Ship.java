package com.jostrobin.battleships.data;


import com.jostrobin.battleships.data.enums.Orientation;
import com.jostrobin.battleships.data.enums.ShipType;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:33
 */
public class Ship {
    private ShipType type;
    private Position position;
    private Orientation orientation;

    public class Position {
        private int x;
        private int y;
    }
}