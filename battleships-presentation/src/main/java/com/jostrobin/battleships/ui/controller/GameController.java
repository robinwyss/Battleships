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

import com.jostrobin.battleships.listener.ChatListener;
import com.jostrobin.battleships.service.network.rmi.ApplicationInterface;
import com.jostrobin.battleships.service.network.rmi.chat.Chat;

/**
 * @author rowyss
 *         Date: 02.11.11 Time: 19:53
 */
public class GameController
{
    private ApplicationInterface appInterface;
    private ChatController chatController;

    public GameController(Chat chat, ApplicationInterface appInterface)
    {
        this.appInterface = appInterface;
        chatController = new ChatController(chat);
    }

    public ChatListener getChatListener()
    {
        return chatController;
    }
}
