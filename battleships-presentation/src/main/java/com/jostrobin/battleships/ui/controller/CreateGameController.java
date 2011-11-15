package com.jostrobin.battleships.ui.controller;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.enums.State;
import com.jostrobin.battleships.service.network.detection.ServerDetectionManager;
import com.jostrobin.battleships.session.ApplicationState;


public class CreateGameController
{
    private ServerDetectionManager serverDetectionManager;

    public CreateGameController(ServerDetectionManager serverDetectionManager)
    {
        this.serverDetectionManager = serverDetectionManager;
    }

    public void createGame(GameSettings settings)
    {
        ApplicationState state = ApplicationState.getInstance();
        state.setState(State.WAITING_FOR_PLAYERS);
        settings.setCurrentNumberOfPlayers(1); // we are in it
        state.setSettings(settings);

        // tell the others we're ready
        serverDetectionManager.broadcastUpdate();

        UIController.getInstance().showGameSelection();
    }
}
