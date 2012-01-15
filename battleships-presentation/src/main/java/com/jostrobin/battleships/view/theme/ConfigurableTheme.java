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

import com.jostrobin.battleships.common.data.enums.ShipType;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author rowyss
 *         Date: 27.12.11 Time: 14:19
 */
public class ConfigurableTheme extends BaseTheme implements InitializingBean
{

    private static ConfigurableTheme INSTANCE;
    private Theme currentTheme;

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
    public String getThemeName()
    {
        return currentTheme.getThemeName();
    }

    @Override
    public Image getBackground()
    {
        return currentTheme.getBackground();
    }

    @Override
    public Image getByShipType(ShipType type)
    {
        return currentTheme.getByShipType(type);
    }

	@Override
	public Image getGreendDot()
	{
		return currentTheme.getGreendDot();
	}

	@Override
	public Image getRedDot()
	{
		return currentTheme.getRedDot();
	}

}
