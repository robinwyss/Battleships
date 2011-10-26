package com.jostrobin.battleships;

import com.jostrobin.battleships.service.network.rmi.chat.Client.ChatClient;
import com.jostrobin.battleships.service.network.rmi.chat.server.ChatServer;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class App {
    public static void main(String... args) throws Exception {
        new ChatServer();

        new ChatClient().sendMessage("Hello");
    }

}
