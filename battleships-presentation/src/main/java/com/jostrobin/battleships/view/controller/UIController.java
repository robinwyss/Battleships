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

package com.jostrobin.battleships.view.controller;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.controller.GameSelectionController;
import com.jostrobin.battleships.controller.PlacementController;
import com.jostrobin.battleships.view.effects.SmoothResize;
import com.jostrobin.battleships.view.frames.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 13.11.11 Time: 18:59
 */
public class UIController
{
    private static final Logger logger = LoggerFactory.getLogger(UIController.class);

    private JFrame frame;
    private JPanel currentFrame;
    private RegistrationDialog registrationDialog;
    private GameSelectionFrame gameSelectionFrame;
    private CreateGameFrame createGameFrame;
    private GameFrame gameFrame;
    private SmoothResize smoothResize = new SmoothResize();
    private GameSelectionController gameSelectionController;
    private PlacementController placementController;
    private JPanel settingsFrame;

    public UIController()
    {
        frame = new JFrame();
        frame.getRootPane().setDoubleBuffered(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Battleships");
    }

    public void showRegistrationDialog()
    {
        show(registrationDialog);
    }

    public void showGameSelection()
    {
        show(gameSelectionFrame);
    }

    public void showCreateGame()
    {
        show(createGameFrame);
    }

    public void showGameView(Long startingPlayer)
    {
        gameFrame.showGameView(startingPlayer);
    }

    public void showPlacementFrame(int length, int width, Map<Long, String> participants, List<Ship> ships)
    {
        if (!gameFrame.equals(currentFrame))
        {
            show(gameFrame);
        }
        placementController.initializeShips(ships);
        gameFrame.initializeFields(length, width, participants);
        gameFrame.showPlacementView();
    }

    public void showSettingsFrame()
    {
        show(settingsFrame);
    }

    private void show(JPanel newFrame)
    {
        if (currentFrame != null)
        {
            frame.remove(currentFrame);
        }
        currentFrame = newFrame;
        frame.add(newFrame);
        frame.setVisible(true);
        resize(newFrame.getPreferredSize());
        frame.setMinimumSize(newFrame.getMinimumSize());
        frame.setMaximumSize(newFrame.getMaximumSize());
    }

    private void resize(Dimension newSize)
    {
        smoothResize.resize(frame, newSize);
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setGameSelectionFrame(GameSelectionFrame gameSelectionFrame)
    {
        this.gameSelectionFrame = gameSelectionFrame;
    }

    public void setRegistrationDialog(RegistrationDialog registrationDialog)
    {
        this.registrationDialog = registrationDialog;
    }

    public void setCreateGameFrame(CreateGameFrame createGameFrame)
    {
        this.createGameFrame = createGameFrame;
    }

    public void setPlacementController(PlacementController placementController)
    {
        this.placementController = placementController;
    }


    public void setSettingsFrame(SettingsFrame settingsFrame)
    {
        this.settingsFrame = settingsFrame;
    }
}
