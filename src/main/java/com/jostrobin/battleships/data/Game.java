package com.jostrobin.battleships.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author rowyss
 *         Date: 25.10.11 Time: 18:28
 */
public class Game {
    private Set<Player> players = new TreeSet<Player>();
    private GameSettings settings;
    private Map<Player, Battlefield> battlefields = new HashMap<Player, Battlefield>();
    private Player currentPlayer;
}
