package com.jostrobin.battleships.controller;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.model.GameSelectionModel;
import com.jostrobin.battleships.view.frames.GameSelectionFrame;
import com.jostrobin.battleships.view.listeners.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class GameSelectionController implements NetworkListener, InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);

    private ApplicationController applicationController;

    private GameSelectionModel model;

    private GameSelectionFrame gameSelectionFrame;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        applicationController.addNetworkListener(this);
        gameSelectionFrame.addCreateGameListener(new CreateGameListener());
        gameSelectionFrame.addExitListener(new ExitListener());
        gameSelectionFrame.addJoinGameListener(new JoinGameListener());
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
                gameSelectionFrame.updatePlayerList();
            }
            else if (command.getCommand() == Command.PREPARE_GAME)
            {
                int length = command.getFieldLength();
                int width = command.getFieldWidth();
                applicationController.showGameFrame(length, width);
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

    public void setGameSelectionFrame(GameSelectionFrame gameSelectionFrame)
    {
        this.gameSelectionFrame = gameSelectionFrame;
    }

    private class ExitListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            System.exit(0);
        }
    }

    private class JoinGameListener implements EventListener<Player>
    {

        @Override
        public void actionPerformed(Player player)
        {
            applicationController.joinGame(player);
        }
    }

    private class CreateGameListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            applicationController.showCreateGame();
        }
    }
}
