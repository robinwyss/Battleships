package com.jostrobin.battleships.controller;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.listener.ChatListener;
import com.jostrobin.battleships.view.panels.ChatPanel;

public class ChatController
{
	private ChatPanel panel;
	
	private ApplicationController controller;
	
	public ChatController(ApplicationController controller, ChatPanel panel)
	{
		this.panel = panel;
		panel.addChatListener(new DefaultChatListener());
	}
	
	private class DefaultChatListener implements ChatListener
	{
		@Override
		public void sendMessage(String username, String message)
		{
		}
	}
}
