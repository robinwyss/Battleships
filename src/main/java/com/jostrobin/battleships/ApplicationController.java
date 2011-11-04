package com.jostrobin.battleships;

import com.jostrobin.battleships.service.network.rmi.RmiManager;
import com.jostrobin.battleships.ui.controller.CreateGameController;
import com.jostrobin.battleships.ui.controller.GameSelectionController;
import com.jostrobin.battleships.ui.controller.RegistrationController;
import com.jostrobin.battleships.ui.frames.CreateGameFrame;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class ApplicationController
{

    public static void main(String... args)
    {

        // thread rmi
        RmiManager rmiManager = RmiManager.getInstance();
        rmiManager.startupRmiServices();

        // thread broadcast

        // gui


        new RegistrationController().showRegistrationDialog();

//        new GameFrame();

        GameSelectionController gameSelectionController = new GameSelectionController();
        GameSelectionFrame f = new GameSelectionFrame(gameSelectionController);
        gameSelectionController.setGameSelectionFrame(f);
        f.setVisible(true);
        
        CreateGameController createGameController = new CreateGameController();
        CreateGameFrame gameFrame = new CreateGameFrame(createGameController);
    }

}
