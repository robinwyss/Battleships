package com.jostrobin.battleships.controller;

import com.jostrobin.battleships.view.controller.UIController;
import com.jostrobin.battleships.view.frames.RegistrationDialog;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationController
{
    private RegistrationDialog dialog;
    
    private UIController uiController;

    public RegistrationController(UIController uiController)
	{
		this.uiController = uiController;
	}

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
            // show the next frame
            uiController.showGameSelection();
            
        	// login to the server
        	uiController.login(username);
        }
    }

}
