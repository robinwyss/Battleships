package com.jostrobin.battleships.common.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.Player;

public class NetworkWriter
{
    private DataOutputStream outputStream;

    public NetworkWriter(Socket socket) throws IOException
    {
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void login(String username) throws IOException
    {
        outputStream.writeInt(Command.LOGIN);
        outputStream.writeUTF(username);
    }

    public void joinGame(Player player) throws IOException
    {
        outputStream.writeInt(Command.JOIN_GAME);
        outputStream.writeLong(player.getGameData().getId());
    }

    public void createGame(GameData game) throws IOException
    {
        outputStream.writeInt(Command.CREATE_GAME);
        outputStream.writeUTF(game.getMode().name());
        outputStream.writeInt(game.getMaxPlayers());
        outputStream.writeInt(game.getFieldLength());
        outputStream.writeInt(game.getFieldWidth());
    }
    
    public void sendChatMessage(String username, String message) throws IOException
    {
    	outputStream.writeInt(Command.CHAT_MESSAGE);
    	outputStream.writeUTF(username);
    	outputStream.writeUTF(message);
    }
}
