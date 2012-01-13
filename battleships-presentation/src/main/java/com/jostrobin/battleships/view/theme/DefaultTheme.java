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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.enums.ShipType;

/**
 * @author rowyss
 *         Date: 14.12.11 Time: 18:44
 */
public class DefaultTheme extends BaseTheme
{

    public static final Logger logger = LoggerFactory.getLogger(DefaultTheme.class);

    @Override
    public String getThemeName()
    {
        return "Default theme";
    }

    @Override
    public Image getBackground()
    {
        if (background == null)
        {
            background = loadImage("tiles/water.gif");
        }
        return background;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image[] getAircraftCarrier()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image[] getBattleship()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image getDestroyer()
    {
    	if (destroyer == null)
    	{
//    		destroyer = loadImage("tiles/destroyer.bmp");
    	}
    	return destroyer;
    }

    @Override
    public Image[] getPatrolBoat()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image getSubmarine()
    {
    	if (submarine == null)
    	{
    		submarine = loadImage("tiles/submarine.bmp");
    	}
    	return submarine;
    }

	@Override
	public Image getByShipType(ShipType type)
	{
		switch (type)
		{
		case SUBMARINE:
			return getSubmarine();
		case DESTROYER:
			return getDestroyer();
		}
		return null;
	}


}

