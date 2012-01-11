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

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.common.data.enums.ShipType;

/**
 * @author rowyss
 *         Date: 27.12.11 Time: 14:19
 */
public class ConfigurableTheme implements Theme, InitializingBean
{

    private Theme currentTheme;
    private static ConfigurableTheme INSTANCE;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        INSTANCE = this;
    }

    public static ConfigurableTheme getInstance()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException("Class has not been initialized by the spring container");
        }
        return INSTANCE;
    }

    public Theme getCurrentTheme()
    {
        return currentTheme;
    }

    public void setCurrentTheme(Theme currentTheme)
    {
        this.currentTheme = currentTheme;
    }

    @Override
    public Image[] getAircraftCarrier()
    {
        return currentTheme.getAircraftCarrier();
    }

    @Override
    public Image getBackground()
    {
        return currentTheme.getBackground();
    }

    @Override
    public Image[] getBattleship()
    {
        return currentTheme.getBattleship();
    }

    @Override
    public Image[] getDestroyer()
    {
        return currentTheme.getDestroyer();
    }

    @Override
    public Image[] getPatrolBoat()
    {
        return currentTheme.getPatrolBoat();
    }

    @Override
    public Image getSubmarine()
    {
        return currentTheme.getSubmarine();
    }

    @Override
    public String getThemeName()
    {
        return currentTheme.getThemeName();
    }

	@Override
	public Image getByShipType(ShipType type)
	{
		return currentTheme.getByShipType(type);
	}

}
