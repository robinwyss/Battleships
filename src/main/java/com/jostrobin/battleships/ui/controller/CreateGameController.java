package com.jostrobin.battleships.ui.controller;

import com.jostrobin.battleships.service.network.rmi.GameState;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;

public class CreateGameController
{
	private ServerDetectionManager serverDetectionManager;
	
	public CreateGameController(ServerDetectionManager serverDetectionManager)
	{
		this.serverDetectionManager = serverDetectionManager;
	}
	
	public void createGame()
	{
		GameState state = GameState.getInstance();
		state.setState("waiting for players");
		serverDetectionManager.broadcastUpdate();
	}
}
