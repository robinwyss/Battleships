package com.jostrobin.battleships;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.service.network.rmi.RmiManager;
import com.jostrobin.battleships.session.ApplicationState;
import com.jostrobin.battleships.ui.controller.GameSelectionController;
import com.jostrobin.battleships.ui.controller.RegistrationController;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class ApplicationController
{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    public static void main(String... args)
    {
		ApplicationState state = ApplicationState.getInstance();
    	for (String arg : args)
    	{
    		if (arg != null && (arg.equals("debug") || arg.equals("-debug") || arg.equals("-Ddebug")))
    		{
    			state.setDebug(true);
    		}
    	}
    	if (!state.isDebug())
    	{
    		logger.debug("Starting application in production mode");
	        new RegistrationController().showRegistrationDialog();
    	}
    	else
    	{
			logger.debug("Starting application in debug mode");

	        // thread rmi
	        RmiManager rmiManager = RmiManager.getInstance();
	        rmiManager.startupRmiServices();

	        // thread broadcast

	        // gui

	        new RegistrationController().showRegistrationDialog();

	        GameSelectionController gameSelectionController = new GameSelectionController();
	        GameSelectionFrame f = new GameSelectionFrame(gameSelectionController);
	        gameSelectionController.setGameSelectionFrame(f);
	        f.setVisible(true);
    	}

    }

}
