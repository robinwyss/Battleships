/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jostrobin.battleships.ui.controller;

import java.rmi.RemoteException;

import com.jostrobin.battleships.listener.ChatListener;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;
import com.jostrobin.battleships.ui.panels.ChatPanel;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 18:02
 */
public class ChatController implements ChatListener
{
    private ChatController chatController;
    private ChatPanel chatPanel;
    private Chat chat;

    public ChatController(Chat chat)
    {
        this.chat = chat;
    }

    @Override
    public void receiveMessage(String username, String message)
    {

    }

    public void sendMessage(String message)
    {
        chatPanel.displayMessage("Me", message);
        try
        {
            chat.sendMessage("Bilbo", message);
            chatPanel.displayMessage("Me", message);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void setChatPanel(ChatPanel chatPanel)
    {
        this.chatPanel = chatPanel;
    }
}
