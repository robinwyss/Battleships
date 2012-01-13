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

import java.io.*;

import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.data.enums.GameUpdate;
import com.jostrobin.battleships.common.data.enums.ShipType;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

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
        clientWriter.sendAttackResult(3,6, AttackResult.PLAYER_DESTROYED,new Ship(ShipType.DESTROYER),1l,2l, GameUpdate.PLAYER_HAS_BEEN_DESTROYED,3l);
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getX(), is(3));
                assertThat(command.getY(), is(6));
                assertThat(command.getAttackResult(), is(AttackResult.PLAYER_DESTROYED));
                assertThat(command.getShip(), is(new Ship(ShipType.DESTROYER)));
                assertThat(command.getAttackingClient(), is(3));
                assertThat(command.getX(), is(3));
                assertThat(command.getX(), is(3));
                assertThat(command.getX(), is(3));
                assertThat(command.getX(), is(3));
            }
        });
    }

    @Test
    public void testSendAvailablePlayers()
    {
        clientWriter.sendAvailablePlayers();
    }

    @Test
    public void testSendChatMessage()
    {
        clientWriter.sendChatMessage();
    }

    @Test
    public void testPrepareGame()
    {
        clientWriter.sendPrepareGame();
    }

    @Test
    public void testSendStartGame()
    {
        clientWriter.sendStartGame();
    }
}
