package com.jostrobin.battleships.server.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IdGeneratorTest
{
    private IdGenerator generator = new IdGenerator();

    @Test
    public void testConsecutiveIds()
    {
        assertEquals(new Long(0), generator.nextId());
        assertEquals(new Long(1), generator.nextId());
        assertEquals(new Long(2), generator.nextId());
        assertEquals(new Long(3), generator.nextId());
        assertEquals(new Long(4), generator.nextId());
        assertEquals(new Long(5), generator.nextId());
        assertEquals(new Long(6), generator.nextId());
    }

    @Test
    public void testManyIds()
    {
        for (int i = 0; i < 10000; i++)
        {
            generator.nextId();
        }
        assertEquals(new Long(10000), generator.nextId());
    }
}
