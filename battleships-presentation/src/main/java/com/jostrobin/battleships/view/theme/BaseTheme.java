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

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
    	Color transparentColor = new Color(255, 0, 255);
    	
        BufferedImage image = null;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream imgInputStream = classLoader.getResourceAsStream(imageFilePath);
        try
        {
            BufferedImage nonTransparentImage = ImageIO.read(imgInputStream);
            image = new BufferedImage(nonTransparentImage.getWidth(), nonTransparentImage.getHeight(),
            		BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(nonTransparentImage, 0, 0, image.getWidth(), image.getHeight(),
            		0, 0, image.getWidth(), image.getHeight(), null);

            // remove transparent colors
            for (int y=0; y<image.getHeight(); y++)
            {
            	for (int x=0; x<image.getWidth(); x++)
            	{
            		if (image.getRGB(x, y) == transparentColor.getRGB())
            		{
            			image.setRGB(x, y, new Color(0, 0, 0, 0).getRGB()); // replace with transparency
            		}
            	}
            }
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
