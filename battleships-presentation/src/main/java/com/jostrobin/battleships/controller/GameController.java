package com.jostrobin.battleships.controller;

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.view.frames.GameFrame;
import com.jostrobin.battleships.view.listeners.AttackListener;

public class GameController implements NetworkListener, InitializingBean, AttackListener
{
	private ApplicationController applicationController;
	
	private GameFrame gameFrame;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		applicationController.addNetworkListener(this);
		gameFrame.addAttackListener(this);
	}

	@Override
	public void notify(Command command)
	{
		switch (command.getCommand())
		{
		case Command.ATTACK_RESULT:
			AttackResult result = command.getAttackResult();
			gameFrame.hitCell(command);
			break;
		}
	}

	@Override
	public void attack(int x, int y, Long clientId)
	{
		applicationController.sendAttack(x, y, clientId);
	}

	public ApplicationController getApplicationController()
	{
		return applicationController;
	}

	public void setApplicationController(ApplicationController applicationController)
	{
		this.applicationController = applicationController;
	}

	public GameFrame getGameFrame()
	{
		return gameFrame;
	}

	public void setGameFrame(GameFrame gameFrame)
	{
		this.gameFrame = gameFrame;
	}
	
}
