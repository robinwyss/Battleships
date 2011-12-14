/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jostrobin.battleships.view.Theme;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 14.12.11 Time: 18:44
 */
public class DefaultTheme
{
    private static Image water;
    private static Image[] aircraftCarrier = new Image[5];
    private static Image[] battleship = new Image[4];
    private static Image[] destroyer = new Image[3];
    private static Image[] submarine = new Image[3];
    private static Image[] patrolBoat = new Image[2];

    public static final Logger logger = LoggerFactory.getLogger(DefaultTheme.class);

    static
    {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream waterImgInputStream = classLoader.getResourceAsStream("tiles/water.bmp");
        try
        {
            water = ImageIO.read(waterImgInputStream);
        }
        catch (IOException e)
        {
            logger.warn("Could not load image");
        }
    }


    public static Image[] getAircraftCarrier()
    {
        return aircraftCarrier;
    }

    public static Image[] getBattleship()
    {
        return battleship;
    }

    public static Image[] getDestroyer()
    {
        return destroyer;
    }

    public static Image[] getPatrolBoat()
    {
        return patrolBoat;
    }

    public static Image[] getSubmarine()
    {
        return submarine;
    }

    public static Image getWater()
    {
        return water;
    }

}
