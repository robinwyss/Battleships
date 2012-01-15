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

package com.jostrobin.battleships.server.network;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.GameUpdate;
import com.jostrobin.battleships.common.data.enums.ShipType;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.server.client.Client;
import com.jostrobin.battleships.server.game.Game;

/**
 * @author rowyss
 *         Date: 13.01.12 Time: 14:32
 */
public class ServerClientTest
{

    private ClientWriter clientWriter;
    private NetworkHandler networkHandler;
    private Thread t1;

    @Before
    public void init() throws IOException
    {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        clientWriter = new ClientWriter();
        clientWriter.init(new DataOutputStream(pipedOutputStream));

        networkHandler = new NetworkHandler();
        networkHandler.init(new DataInputStream(pipedInputStream));
        t1 = new Thread(networkHandler);
        t1.start();
    }

    @Test
    public void testSendAttackResult() throws Exception
    {
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.ATTACK_RESULT));
                assertThat(command.getX(), is(3));
                assertThat(command.getY(), is(6));
                assertThat(command.getAttackResult(), is(AttackResult.PLAYER_DESTROYED));
                assertThat(command.getShip(), is(new Ship(ShipType.DESTROYER)));
                assertThat(command.getAttackingClient(), is(1l));
                assertThat(command.getAttackedClient(), is(2l));
                assertThat(command.getGameUpdate(), is(GameUpdate.PLAYER_HAS_BEEN_DESTROYED));
                assertThat(command.getClientId(), is(3l));
                networkHandler.stop();
            }
        });
        clientWriter.sendAttackResult(3, 6, AttackResult.PLAYER_DESTROYED, new Ship(ShipType.DESTROYER), 1l, 2l, GameUpdate.PLAYER_HAS_BEEN_DESTROYED, 3l);
    }

    @Test
    public void testSendAvailablePlayers() throws IOException
    {
        final List<Client> clients = new ArrayList<Client>();
        clients.add(new MockClient());
        clients.add(new MockClient());
        clients.add(new MockClient());
        clients.add(new MockClient());
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.PLAYERS_LIST));
                assertThat(command.getPlayers().size(), is(clients.size()));
                networkHandler.stop();
            }
        });
        clientWriter.sendAvailablePlayers(clients);
    }

    @Test
    public void testSendChatMessage() throws IOException
    {
        final String username = "Barbie";
        final String message = "Hello Battleships";
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.CHAT_MESSAGE));
                assertThat(command.getMessage(), is(message));
                assertThat(command.getUsername(), is(username));
                networkHandler.stop();
            }
        });
        clientWriter.sendChatMessage(username, message);
    }

    @Test
    public void testPrepareGame() throws IOException
    {
        Game game = new Game(4l, GameMode.HARDCORE, 4, 20, 10);
        final Map<Long, String> players = new HashMap<Long, String>();
        players.put(2l, "Robin");
        players.put(4l, "Joscht");
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.PREPARE_GAME));
                assertThat(command.getGameId(), is(4l));
                assertThat(command.getGameMode(), is(GameMode.HARDCORE));
                assertThat(command.getFieldLength(), is(20));
                assertThat(command.getFieldWidth(), is(20));
                assertThat(command.getParticipants(), is(players));
                networkHandler.stop();
            }
        });
        clientWriter.sendPrepareGame(game, players);
    }

    @Test
    public void testSendStartGame() throws Exception
    {
        final Long startingClient = 6l;
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.START_GAME));
                assertThat(command.getStartingPlayer(), is(startingClient));
                networkHandler.stop();
            }
        });
        clientWriter.sendStartGame(startingClient);
    }

    @Test
    public void testAcceptPlayer() throws Exception
    {
        final Long clientId = 4l;
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.ACCEPTED));
                assertThat(command.getClientId(), is(clientId));
                networkHandler.stop();
            }
        });
        clientWriter.acceptPlayer(clientId);
    }

    @Test
    public void testLogin() throws Exception
    {
        final Long clientId = 4l;
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getCommand(), is(Command.ACCEPTED));
                assertThat(command.getClientId(), is(clientId));
                networkHandler.stop();
            }
        });
        clientWriter.acceptPlayer(clientId);
    }

    private class MockClient extends Client
    {

        public MockClient()
        {
            super(null, null);
        }

        @Override
        public String getUsername()
        {
            return "username";
        }

        @Override
        public Long getId()
        {
            return 7l;
        }

        @Override
        public GameData getGameData()
        {
            return new GameData(1l, GameMode.CUSTOM, 2, 5, 6);
        }

        @Override
        public GameState getState()
        {
            return GameState.INIT;
        }
    }
}
