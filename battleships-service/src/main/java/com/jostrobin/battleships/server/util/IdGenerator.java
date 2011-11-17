package com.jostrobin.battleships.server.util;

/**
 * Generates IDs.
 *
 * @author joscht
 */
public class IdGenerator
{
    private Long id = new Long(0);

    public synchronized Long nextId()
    {
        return id++;
    }
}
