package com.jostrobin.battleships.ui.controller;

import java.util.UUID;

import com.jostrobin.battleships.data.Configuration;
import com.jostrobin.battleships.service.network.rmi.RmiManager;
import com.jostrobin.battleships.session.ApplicationState;
import com.jostrobin.battleships.ui.frames.RegistrationDialog;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationController
{
    RegistrationDialog dialog;

    public void showRegistrationDialog()
    {
        dialog = new RegistrationDialog(this);
    }

    public void registerUser(String username)
    {
        if (username == null || username.trim().isEmpty())
        {
            dialog.showMessage("You must provide a name!");
        }
        else
        {

            String id = username + UUID.randomUUID().getLeastSignificantBits();
            Configuration config = Configuration.getInstance();
            config.setId(id);

            ApplicationState state = ApplicationState.getInstance();
            state.setUsername(username);

            // prepare rmi things
            RmiManager rmiManager = RmiManager.getInstance();
            rmiManager.startupRmiServices();

            // show the next frame
            UIController.getInstance().showGameSelection();
        }
    }

}
