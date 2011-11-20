package com.jostrobin.battleships.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.jostrobin.battleships.server.client.Client;

public interface Writer
{
	public void init(Socket socket) throws IOException;
	
    public void sendAvailablePlayers(List<Client> clients) throws IOException;

    public void sendPrepareGame() throws IOException;

    public void acceptPlayer(Long id) throws IOException;
}
