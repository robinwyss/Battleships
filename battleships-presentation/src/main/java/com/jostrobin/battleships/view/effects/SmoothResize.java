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

package com.jostrobin.battleships.view.effects;

import java.awt.*;

/**
 * @author rowyss
 *         Date: 13.11.11 Time: 20:47
 */
public class SmoothResize
{
    private int steps = 10;
    private int delay = 1;

    public void resize(Component component, Dimension newSize, boolean recenter)
    {

        int initialWidth = component.getWidth();
        int initialHeight = component.getHeight();
        int deltaW = ((int) newSize.getWidth()) - initialWidth;
        int deltaH = ((int) newSize.getHeight()) - initialHeight;
        int stepW = deltaW / steps;
        int stepH = deltaH / steps;
        for (int i = 0; i < steps; i++)
        {
            int width = initialWidth + stepW * i;
            int height = initialHeight + stepH * i;
            if (true)
            {
                centerWindow(component, stepW, stepH, width, height);
            }
            component.setSize(width, height);
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
            }
        }
        component.setSize(newSize);
    }

    private void centerWindow(Component component, int stepW, int stepH, int width, int height)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point location = component.getLocation();
        int newPosX = (int) (location.getX() - (stepW / 2));
        if (newPosX < 0)
        {
            newPosX = 0;
        }
        else if (newPosX + width > screenSize.getWidth())
        {
            newPosX = (int) (screenSize.getWidth() - width);
        }
        int newPosY = (int) (location.getY() - (stepH / 2));
        if (newPosY < 0)
        {
            newPosY = 0;
        }
        else if (newPosY + height > screenSize.getHeight())
        {
            newPosY = (int) (screenSize.getHeight() - height);
        }
        component.setLocation(newPosX, newPosY);
    }
}
