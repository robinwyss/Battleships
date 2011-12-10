package com.jostrobin.battleships.controller;

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.view.frames.GameFrame;

public class GameController implements NetworkListener, InitializingBean
{
	private ApplicationController applicationController;
	
	private GameFrame gameFrame;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		applicationController.addNetworkListener(this);
	}

	@Override
	public void notify(Command command)
	{
		// TODO Auto-generated method stub
		
	}
	
}
