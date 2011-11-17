package com.jostrobin.battleships.controller;

import java.util.Observer;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.model.GameSelectionModel;
import com.jostrobin.battleships.view.controller.UIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameSelectionController implements NetworkListener
{
    private static final Logger logger = LoggerFactory.getLogger(GameSelectionController.class);

    private ApplicationController controller;

    private GameSelectionModel model;

    private UIController uiController;

    public GameSelectionController(UIController uiController, ApplicationController controller, GameSelectionModel model)
    {
        this.uiController = uiController;
        this.controller = controller;
        this.model = model;
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
            controller.joinGame(player);
        }
    }

    public void createGame()
    {
        uiController.showCreateGame();
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
                uiController.showGameFrame();
            }
            else if (command.getCommand() == Command.ACCEPTED)
            {
                model.setClientId(command.getClientId());
            }
        }

    }

}
