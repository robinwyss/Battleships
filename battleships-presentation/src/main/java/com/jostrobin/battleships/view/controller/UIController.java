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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.SortedMap;
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
    //    private JPanel contentPanel;
//    private JScrollPane scrollPane;
    private JPanel currentFrame;
    private RegistrationDialog registrationDialog;
    private GameSelectionFrame gameSelectionFrame;
    private CreateGameFrame createGameFrame;
    private GameFrame gameFrame;
    private SmoothResize smoothResize = new SmoothResize();
    private GameSelectionController gameSelectionController;
    private PlacementController placementController;
    private JPanel settingsFrame;
    private WindowComponentListener windowComponentListener;

    public UIController()
    {
        frame = new JFrame();
        frame.getRootPane().setDoubleBuffered(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Battleships");
        // add listeners
        windowComponentListener = new WindowComponentListener();
        frame.addComponentListener(windowComponentListener);
//        contentPanel = new JPanel();
//        scrollPane = new JScrollPane(contentPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        frame.add(scrollPane);
        centerFrame();
    }

    private void centerFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int posX = (int) (screenSize.getWidth() / 2) - frame.getWidth() / 2;
        int posY = (int) (screenSize.getHeight() / 2) - frame.getHeight() / 2;
        frame.setLocation(posX, posY);
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
        // call show to adjust the size
        show(gameFrame);
    }

    public void showPlacementFrame(int length, int width, SortedMap<Long, String> participants, List<Ship> ships)
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
        frame.setPreferredSize(newFrame.getPreferredSize());
        frame.setMinimumSize(newFrame.getMinimumSize());
        frame.setMaximumSize(newFrame.getMaximumSize());
    }

    private void resize(Dimension newSize)
    {
        smoothResize.resize(frame, newSize);
//        frame.setSize((int) newSize.getWidth(), (int) (newSize.getHeight() + 20));
//        contentPanel.setSize(newSize);
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

    private class WindowComponentListener implements ComponentListener
    {

        @Override
        public void componentResized(ComponentEvent componentEvent)
        {
            Dimension size = componentEvent.getComponent().getSize();
            currentFrame.setPreferredSize(size);
        }

        @Override
        public void componentMoved(ComponentEvent componentEvent)
        {

        }

        @Override
        public void componentShown(ComponentEvent componentEvent)
        {
        }

        @Override
        public void componentHidden(ComponentEvent componentEvent)
        {
        }


    }
}
