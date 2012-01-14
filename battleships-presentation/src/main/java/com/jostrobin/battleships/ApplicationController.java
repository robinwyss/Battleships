package com.jostrobin.battleships;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.common.network.NetworkWriter;
import com.jostrobin.battleships.model.GameModel;
import com.jostrobin.battleships.view.controller.UIController;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class ApplicationController
{
    private static final int SERVER_PORT = 11223;

    private InetAddress address;

    // TODO: Refactor to run in its own thread
    private NetworkWriter writer;

    private GameModel model;

    private UIController uiController;

    private NetworkHandler networkHandler;

    private Socket socket;

    private String username;

    public ApplicationController(NetworkHandler handler)
    {
        this.networkHandler = handler;
    }

    public void init(InetAddress address) throws IOException
    {
        String username = System.getProperty("username");
        if (username != null && !username.isEmpty())
        {
            login(username);
        }
        else
        {
            uiController.showRegistrationDialog();
        }

        this.address = address;

        socket = new Socket(address, SERVER_PORT);
        networkHandler.init(new DataInputStream(socket.getInputStream()));
    }

    public void login(String username)
    {
        this.username = username;

        showGameSelection();

        Thread thread = new Thread(networkHandler);
        thread.start();

        try
        {
            writer = new NetworkWriter(new DataOutputStream(socket.getOutputStream()));
            writer.login(username);
        }
        catch (IOException e)
        {
            //TODO: show error message
            e.printStackTrace();
        }
    }

    public void showGameSelection()
    {
        uiController.showGameSelection();
    }

    public void addNetworkListener(NetworkListener listener)
    {
        if (networkHandler != null)
        {
            networkHandler.addNetworkListener(listener);
        }
    }

    public void createGame(GameData game)
    {
        try
        {
            writer.createGame(game);
        }
        catch (IOException e)
        {
            // TODO: Communication stopped
            e.printStackTrace();
        }
    }

    public void cancelGame()
    {
        try
        {
            writer.sendCancelGame();
        }
        catch (IOException e)
        {
            // TODO: COmmunication stopped
            e.printStackTrace();
        }
    }

    public void joinGame(Player player)
    {
        try
        {
            writer.joinGame(player);
        }
        catch (IOException e)
        {
            // TODO: Communication stopped
            e.printStackTrace();
        }
    }

    public void sendChatMessage(String message)
    {
        try
        {
            writer.sendChatMessage(username, message);
        }
        catch (IOException e)
        {
            // TODO: Communication stopped
            e.printStackTrace();
        }
    }

    public void sendAttack(int x, int y, Long clientId)
    {
        try
        {
            writer.sendAttack(x, y, clientId);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void shipsPlaced(List<Ship> ships)
    {
        try
        {
            writer.sendShipPlaced(ships);
        }
        catch (IOException e)
        {
            // TODO: Communication stopped
            e.printStackTrace();
        }
    }

    public void setUiController(UIController uiController)
    {
        this.uiController = uiController;
    }

    public void showCreateGame()
    {
        uiController.showCreateGame();
    }

    public void showGameView(Long startingPlayer)
    {
        uiController.showGameView(startingPlayer);
    }

    public void showGameFrame(int length, int width, Map<Long, String> participants, List<Ship> ships)
    {
        uiController.showPlacementFrame(length, width, participants, ships);
    }

    public void showSettingsFrame()
    {
        uiController.showSettingsFrame();
    }
}
