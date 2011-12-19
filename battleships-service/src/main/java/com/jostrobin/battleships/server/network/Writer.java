package com.jostrobin.battleships.server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.game.Game;

public interface Writer
{
    public void init(DataOutputStream outputStream) throws IOException;

    public void sendAvailablePlayers(List<Client> clients) throws IOException;

    public void sendPrepareGame(Game game, List<Long> participants) throws IOException;

    public void acceptPlayer(Long id) throws IOException;

    public void sendChatMessage(String username, String message) throws IOException;

    public void sendAttackResult(Long clientId, int x, int y, AttackResult result, Ship ship, Long nextPlayer) throws Exception;

    public void sendStartGame(Long clientId) throws Exception;
}
