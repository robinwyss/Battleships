package com.jostrobin.battleships.controller;

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.listener.ChatListener;
import com.jostrobin.battleships.view.panels.ChatPanel;

public class ChatController implements InitializingBean, NetworkListener
{
	private ChatPanel chatPanel;
	
	private ApplicationController controller;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		chatPanel.addChatListener(new DefaultChatListener());
		controller.addNetworkListener(this);
	}

	public ChatPanel getChatPanel()
	{
		return chatPanel;
	}

	public void setChatPanel(ChatPanel chatPanel)
	{
		this.chatPanel = chatPanel;
	}

	public ApplicationController getController()
	{
		return controller;
	}

	public void setController(ApplicationController controller)
	{
		this.controller = controller;
	}

	private class DefaultChatListener implements ChatListener
	{
		@Override
		public void sendMessage(String message)
		{
			controller.sendChatMessage(message);
		}
	}

	@Override
	public void notify(Command command)
	{
		if (command.getCommand() == Command.CHAT_MESSAGE)
		{
			chatPanel.addChatMessage(command.getUsername(), command.getMessage());
		}
	}
}
