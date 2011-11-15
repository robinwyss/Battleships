package com.jostrobin.battleships.session;

import java.io.Serializable;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.enums.State;
import com.jostrobin.battleships.service.network.detection.ServerDetectionManager;


public class ApplicationState implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final ApplicationState applicationState = new ApplicationState();

    private String username;

    private State state = State.NEW;

    private boolean debug;

    private GameSettings settings;

    private transient ServerDetectionManager serverDetectionManager;

    private ApplicationState()
    {
    }

    public static ApplicationState getInstance()
    {
        return applicationState;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public State getState()
    {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public void setDebug(boolean debug)
    {
        this.debug = debug;
    }

    public GameSettings getSettings()
    {
        return settings;
    }

    public void setSettings(GameSettings settings)
    {
        this.settings = settings;
    }

    public ServerDetectionManager getServerDetectionManager()
    {
        return serverDetectionManager;
    }

    public void setServerDetectionManager(
            ServerDetectionManager serverDetectionManager)
    {
        this.serverDetectionManager = serverDetectionManager;
    }
}
