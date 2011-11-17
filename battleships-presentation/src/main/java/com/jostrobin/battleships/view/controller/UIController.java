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
import java.io.IOException;
import javax.swing.*;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.controller.GameSelectionController;
import com.jostrobin.battleships.controller.RegistrationController;
import com.jostrobin.battleships.model.GameSelectionModel;
import com.jostrobin.battleships.view.effects.SmoothResize;
import com.jostrobin.battleships.view.frames.CreateGameFrame;
import com.jostrobin.battleships.view.frames.GameFrame;
import com.jostrobin.battleships.view.frames.GameSelectionFrame;
import com.jostrobin.battleships.view.frames.RegistrationDialog;
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
    private ApplicationController controller;
    private RegistrationDialog registrationDialog;
    private GameSelectionFrame gameSelectionFrame;
    private CreateGameFrame createGameFrame;
    private GameFrame gameFrame;
    private SmoothResize smoothResize = new SmoothResize();
    private GameSelectionController gameSelectionController;

    public UIController(ApplicationController controller)
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.controller = controller;
        GameSelectionModel gameSelectionModel = new GameSelectionModel();
        gameSelectionController = new GameSelectionController(this, controller, gameSelectionModel);
    }

    public void login(String username)
    {
        try
        {
            controller.addNetworkListener(gameSelectionController);
            controller.login(username);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Could not connect to the server.");
            logger.error("Could not connect to the server", e);
            // TODO: Retry finding a server
        }
    }

    public void showRegistrationDialog()
    {
        RegistrationController registrationController = new RegistrationController(this);
        registrationDialog = new RegistrationDialog(registrationController);
        show(registrationDialog);
    }

    public void showGameSelection()
    {
        if (gameSelectionFrame == null)
        {
            gameSelectionFrame = new GameSelectionFrame(gameSelectionController);
            gameSelectionController.addView(gameSelectionFrame);
//            gameSelectionController.addObserver(gameSelectionFrame);
        }
        show(gameSelectionFrame);
//        f.setVisible(true);
    }

    public void showCreateGame()
    {
        createGameFrame = new CreateGameFrame(controller);
        show(createGameFrame);
    }

    public void showGameFrame()
    {
        gameFrame = new GameFrame();
        gameFrame.showPlacementWindow();
        show(gameFrame);
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
//        frame.setSize(newFrame.getPreferredSize());
        frame.setMinimumSize(newFrame.getMinimumSize());
        frame.setMaximumSize(newFrame.getMaximumSize());
    }

    private void resize(Dimension newSize)
    {
        smoothResize.resize(frame, newSize);
    }


}
