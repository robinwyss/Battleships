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

package com.jostrobin.battleships.view.theme;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.util.IOUtils;

/**
 * @author rowyss
 *         Date: 27.12.11 Time: 15:13
 */
public abstract class BaseTheme implements Theme
{
    private static final Logger logger = LoggerFactory.getLogger(BaseTheme.class);

    protected Image background;
    protected Image aircraftCarrier;
    protected Image battleship;
    protected Image destroyer;
    protected Image submarine;
    protected Image patrolBoat;

    protected Image loadImage(String imageFilePath)
    {
        Image image = null;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream imgInputStream = classLoader.getResourceAsStream(imageFilePath);
        try
        {
            image = ImageIO.read(imgInputStream);
        }
        catch (IOException e)
        {
            logger.warn("Could not load image");
        }
        finally
        {
            IOUtils.closeSilently(imgInputStream);
        }
        return image;
    }
}
