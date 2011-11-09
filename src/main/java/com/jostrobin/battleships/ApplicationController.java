package com.jostrobin.battleships;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.session.ApplicationState;
import com.jostrobin.battleships.ui.controller.RegistrationController;

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
        setLookAndFeel();
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
        }
        else
        {
            logger.debug("Starting application in debug mode");
        }
        new RegistrationController().showRegistrationDialog();

    }

    private static void setLookAndFeel()
    {
        // set the name of the application menu item
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Battleships");

        // set the look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            logger.warn("Could not set system look and fell", e);
        }
    }

}
