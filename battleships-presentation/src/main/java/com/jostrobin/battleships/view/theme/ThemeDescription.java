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

/**
 * @author rowyss
 *         Date: 13.01.12 Time: 22:49
 */
public enum ThemeDescription
{
    DEFAULT_THEME("Default Theme", "default-theme"),
    PAPER_THEME("Paper Theme", "paper-theme"),
    SPACE_THEME("Space Theme", "space-theme"),
    FRUIT_THEME("Fruit Theme", "fruit-theme");

    private String name;
    private String folder;

    private ThemeDescription(String name, String folder)
    {
        this.name = name;
        this.folder = folder;
    }

    public String getFolder()
    {
        return folder;
    }

    public String getName()
    {
        return name;
    }
}
