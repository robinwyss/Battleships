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

    public void resize(Component component, Dimension newSize)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
            centerWindow(component, screenSize, width, height);
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

    private void centerWindow(Component component, Dimension screenSize, int width, int height)
    {
        int posX = (int) ((screenSize.getWidth() / 2) - width / 2);
        int postY = (int) ((screenSize.getHeight() / 2) - height / 2);
        component.setLocation(posX, postY);
    }
}
