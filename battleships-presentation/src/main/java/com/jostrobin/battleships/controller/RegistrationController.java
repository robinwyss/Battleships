package com.jostrobin.battleships.controller;

import javax.annotation.PostConstruct;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.view.frames.RegistrationDialog;
import com.jostrobin.battleships.view.listeners.ActionListener;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationController implements ActionListener<String>
{
    private RegistrationDialog registrationDialog;
    private ApplicationController applicationController;

    public void showRegistrationDialog()
    {

    }

    @PostConstruct
    public void init()
    {
        registrationDialog.addActionListener(this);
    }


    @Override
    public void actionPerformed(String username)
    {
        if (username == null || username.trim().isEmpty())
        {
            registrationDialog.showMessage("You must provide a name!");
        }
        else
        {
            applicationController.login(username);
        }
    }

    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }

    public void setRegistrationDialog(RegistrationDialog registrationDialog)
    {
        this.registrationDialog = registrationDialog;
    }

}
