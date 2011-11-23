package com.jostrobin.battleships;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.common.network.NetworkWriter;
import com.jostrobin.battleships.controller.RegistrationController;
import com.jostrobin.battleships.model.GameSelectionModel;
import com.jostrobin.battleships.view.controller.UIController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    private GameSelectionModel model;

    private UIController uiController;

    private NetworkHandler networkHandler;

    private Socket socket;

    public ApplicationController(InetAddress address) throws IOException
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        uiController = applicationContext.getBean(UIController.class);
        uiController.setController(this);

        String username = System.getProperty("username");
        if (username != null && !username.isEmpty())
        {
            new RegistrationController(uiController).registerUser(username);
        }
        else
        {
            uiController.showRegistrationDialog();
        }

        this.address = address;

        socket = new Socket(address, SERVER_PORT);
        networkHandler = new NetworkHandler(socket);
    }

    public void init(InetAddress address)
    {
    }

    public void login(String username) throws IOException
    {
        Thread thread = new Thread(networkHandler);
        thread.start();

        writer = new NetworkWriter(socket);
        writer.login(username);
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

}
