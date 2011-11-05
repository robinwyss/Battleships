package com.jostrobin.battleships.ui.controller;

import com.jostrobin.battleships.enumerations.State;
import com.jostrobin.battleships.service.network.detection.ServerDetectionManager;
import com.jostrobin.battleships.session.ApplicationState;


public class CreateGameController
{
	private ServerDetectionManager serverDetectionManager;
	
	public CreateGameController(ServerDetectionManager serverDetectionManager)
	{
		this.serverDetectionManager = serverDetectionManager;
	}
	
	public void createGame()
	{
		ApplicationState state = ApplicationState.getInstance();
		state.setState(State.WAITING_FOR_PLAYERS);
		serverDetectionManager.broadcastUpdate();
	}
}
