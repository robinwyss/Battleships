package com.jostrobin.battleships.service.network.rmi;

import java.io.Serializable;

import com.jostrobin.battleships.session.ApplicationState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultApplicationInterface implements ApplicationInterface, Serializable
{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationInterface.class);

    @Override
    public ApplicationState getApplicationState()
    {
        ApplicationState state = ApplicationState.getInstance();
        return state;
    }
}
