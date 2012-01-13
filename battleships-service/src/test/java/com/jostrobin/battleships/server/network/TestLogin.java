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

import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkHandler;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.common.network.NetworkWriter;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * @author rowyss
 *         Date: 13.01.12 Time: 12:41
 */
public class TestLogin
{

    @Test
    public void testLogin() throws IOException
    {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        NetworkWriter networkWriter = new NetworkWriter(new DataOutputStream(pipedOutputStream));

        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.init(new DataInputStream(pipedInputStream));

        final String loginName = "Babar";
        networkHandler.addNetworkListener(new NetworkListener()
        {
            @Override
            public void notify(Command command)
            {
                assertThat(command.getUsername(), is(loginName));
            }
        });
        networkWriter.login(loginName);


    }
}
