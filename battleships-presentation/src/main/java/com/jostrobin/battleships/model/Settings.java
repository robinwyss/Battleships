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

package com.jostrobin.battleships.model;

import com.jostrobin.battleships.view.theme.ThemeDescription;

/**
 * @author rowyss
 *         Date: 14.01.12 Time: 09:10
 */
public class Settings
{
    public boolean soundEnabled = true;

    public ThemeDescription theme;

    public boolean isSoundEnabled()
    {
        return soundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled)
    {
        this.soundEnabled = soundEnabled;
    }

    public ThemeDescription getTheme()
    {
        return theme;
    }

    public void setTheme(ThemeDescription theme)
    {
        this.theme = theme;
    }
}

