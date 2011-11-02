package com.jostrobin.battleships.ui.controller;

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
            dialog.dispose();
        }
    }

}
