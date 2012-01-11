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

import java.awt.*;

/**
 * @author rowyss
 *         Date: 27.12.11 Time: 11:26
 */
public class PaperTheme extends BaseTheme
{
    @Override
    public String getThemeName()
    {
        return "Paper theme";
    }

    @Override
    public Image getBackground()
    {
        if (background == null)
        {
            background = loadImage("tiles/paper-theme/background.gif");
        }
        return background;
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
    public Image[] getDestroyer()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image[] getPatrolBoat()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Image[] getSubmarine()
    {
        return new Image[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
