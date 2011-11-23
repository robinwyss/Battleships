package com.jostrobin.battleships.controller;

import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.model.GameSelectionModel;

public class GameSelectionController implements NetworkListener, InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);

    private ApplicationController applicationController;

    private GameSelectionModel model;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		applicationController.addNetworkListener(this);
	}
	
    public void addView(Observer view)
    {
        model.addObserver(view);
    }

    public void exit()
    {
        System.exit(0);
    }

    public void joinGame(Player player)
    {
        // only join waiting games
        if (player.getState() == GameState.WAITING_FOR_PLAYERS && player.getGameData() != null)
        {
            applicationController.joinGame(player);
        }
    }

    public void createGame()
    {
        applicationController.showCreateGame();
    }

    @Override
    public void notify(Command command)
    {
        if (command != null)
        {
            if (command.getCommand() == Command.PLAYERS_LIST)
            {
                model.setPlayers(command.getPlayers());
                model.notifyObservers();
            }
            else if (command.getCommand() == Command.PREPARE_GAME)
            {
                applicationController.showGameFrame();
            }
            else if (command.getCommand() == Command.ACCEPTED)
            {
                model.setClientId(command.getClientId());
            }
        }

    }

    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }

    public void setModel(GameSelectionModel model)
    {
        this.model = model;
    }
}
