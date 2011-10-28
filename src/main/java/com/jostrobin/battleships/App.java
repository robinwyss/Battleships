package com.jostrobin.battleships;

import com.jostrobin.battleships.service.network.rmi.chat.server.ChatServer;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionListener;
import com.jostrobin.battleships.service.network.rmi.chat.server.ServerDetectionManager;
import com.jostrobin.battleships.ui.controller.GameSelectionController;
import com.jostrobin.battleships.ui.controller.RegistrationCtrl;
import com.jostrobin.battleships.ui.frames.GameSelectionFrame;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * This is the entry point of the application.
 *
 * @author rowyss
 *         Date: 18.10.11 Time: 22:33
 */
public class App {
    public static void main(String... args) throws Exception {

		// thread rmi

		// thread broadcast

		// gui


		new RegistrationCtrl().showRegistrationDialog();

        new ChatServer();
        
        GameSelectionFrame f = new GameSelectionFrame(new GameSelectionController());
        f.setVisible(true);
    }

}
