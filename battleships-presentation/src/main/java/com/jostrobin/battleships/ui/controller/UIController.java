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

package com.jostrobin.battleships.ui.controller;

import java.awt.*;
import javax.swing.*;

import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import com.jostrobin.battleships.ui.effects.SmoothResize;
import com.jostrobin.battleships.ui.frames.CreateGameFrame;
import com.jostrobin.battleships.ui.frames.GameFrame;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;
import com.jostrobin.battleships.ui.frames.RegistrationDialog;

/**
 * @author rowyss
 *         Date: 13.11.11 Time: 18:59
 */
public class UIController
{
    private JFrame frame;
    private JPanel currentFrame;
    private RegistrationDialog registrationDialog;
    private GameSelectionFrame gameSelectionFrame;
    private CreateGameFrame createGameFrame;
    private GameFrame gameFrame;
    private SmoothResize smoothResize = new SmoothResize();
    private GameSelectionController gameSelectionController;
    private static UIController uiController = new UIController();

    public static UIController getInstance()
    {
        return uiController;
    }

    private UIController()
    {
        frame = new JFrame();
        gameSelectionController = new GameSelectionController();
    }

    public void showRegistrationDialog()
    {
        RegistrationController registrationController = new RegistrationController();
        registrationDialog = new RegistrationDialog(registrationController);
        show(registrationDialog);
    }

    public void showGameSelection()
    {
        if (gameSelectionFrame == null)
        {
            gameSelectionFrame = new GameSelectionFrame(gameSelectionController);
            gameSelectionController.addObserver(gameSelectionFrame);
        }
        show(gameSelectionFrame);
//        f.setVisible(true);

        // try to find games
        gameSelectionController.refresh(true);
    }

    public void showCreateGame(CreateGameController createGameController)
    {
        createGameFrame = new CreateGameFrame(createGameController);
        show(createGameFrame);
    }

    public void showGameFrame(Chat chatClient, ApplicationInterface applicationInterface)
    {
        gameFrame = new GameFrame();
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
