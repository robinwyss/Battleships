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

import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.common.network.NetworkWriter;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * @author rowyss
 *         Date: 13.01.12 Time: 13:31
 */
public class ClientServerTest
{

    private NetworkWriter networkWriter;
    private NetworkHandler networkHandler;
    private Thread t1;

    @Before
    public void init() throws IOException
    {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        networkWriter = new NetworkWriter(new DataOutputStream(pipedOutputStream));

        networkHandler = new NetworkHandler();
        networkHandler.init(new DataInputStream(pipedInputStream));
        t1 = new Thread(networkHandler);
        t1.start();
    }

    @Test
    public void testLogin() throws IOException, InterruptedException
    {
        final String loginName = "Babar";
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getUsername(), is(loginName));
                networkHandler.stop();
            }
        });
        networkWriter.login(loginName);
        t1.join();
    }

    @Test
    public void testCreateGame() throws IOException
    {
        final GameData gd = new GameData(1l, GameMode.CLASSIC, 2, 10, 10);
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getGameId(), is(gd.getId()));
                assertThat(command.getGameMode(), is(gd.getMode()));
                assertThat(command.getMaxPlayers(), is(gd.getMaxPlayers()));
                assertThat(command.getFieldWidth(), is(gd.getFieldWidth()));
                assertThat(command.getFieldLength(), is(gd.getFieldLength()));
                assertThat(command.getNrOfAircraftCarriers(), is(gd.getNrOfAircraftCarriers()));
                assertThat(command.getNrOfBattleships(), is(gd.getNrOfBattleships()));
                assertThat(command.getNrOfDestroyers(), is(gd.getNrOfDestroyers()));
                assertThat(command.getNrOfPatrolBoats(), is(gd.getNrOfPatrolBoats()));
                assertThat(command.getNrOfSubmarines(), is(gd.getNrOfSubmarines()));
                networkHandler.stop();
            }
        });
        networkWriter.createGame(gd);
    }
}
