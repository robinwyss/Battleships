package com.jostrobin.battleships.data;

import com.jostrobin.battleships.data.enums.ShipType;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:31
 */
public class GameSettings {
    private Map<ShipType, Integer> boats = new TreeMap<ShipType, Integer>();
    private int fieldWidth;
    private int fieldHeight;
    private boolean canMove;
}
