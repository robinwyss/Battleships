package com.jostrobin.battleships.ui.controller;

import com.jostrobin.battleships.service.network.rmi.GameState;

public class CreateGameController
{
	public void createGame()
	{
		GameState state = GameState.getInstance();
		state.setState("waiting for players");
	}
}
