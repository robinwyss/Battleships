package com.jostrobin.battleships.server.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.server.client.Client;

public class GameTest
{
	@Test
	public void testAddMaxPlayers()
	{
		Game game = new Game(new Long(1), GameMode.CLASSIC, 5, 1, 1);
		assertTrue(game.addPlayer(new Client(null, null)));
		assertEquals(1, game.getCurrentPlayers());
		assertTrue(game.addPlayer(new Client(null, null)));
		assertEquals(2, game.getCurrentPlayers());
		assertTrue(game.addPlayer(new Client(null, null)));
		assertEquals(3, game.getCurrentPlayers());
		assertTrue(game.addPlayer(new Client(null, null)));
		assertEquals(4, game.getCurrentPlayers());
		assertTrue(game.addPlayer(new Client(null, null)));
		assertEquals(5, game.getCurrentPlayers());
		assertFalse(game.addPlayer(new Client(null, null)));
		assertEquals(5, game.getCurrentPlayers());
		assertFalse(game.addPlayer(new Client(null, null)));
		assertEquals(5, game.getCurrentPlayers());
	}
	
	@Test
	public void testPrepareStates() throws IOException
	{
		Game game = new Game(new Long(1), GameMode.CLASSIC, 5, 1, 1);
		List<Client> clients = new ArrayList<Client>();
		List<Long> ids = new ArrayList<Long>();
		for (Long i=new Long(0); i<5; i++)
		{
			Client client = mock(Client.class);
			when(client.getId()).thenReturn(i);
			
			clients.add(client);
			game.addPlayer(client);
			
			ids.add(i); // save for later
		}
		
		game.prepareGame();
		
		for (Client client : clients)
		{
			verify(client).setState(GameState.PREPARING);
			verify(client).prepareGame(ids);
		}
	}
	
	@Test
	public void testChatMessageNotification() throws IOException
	{
		Game game = new Game(new Long(1), GameMode.CLASSIC, 5, 1, 1);
		List<Client> clients = new ArrayList<Client>();
		for (Long i=new Long(0); i<5; i++)
		{
			Client client = mock(Client.class);
			clients.add(client);
			game.addPlayer(client);
		}
		
		game.notifyAboutChatMessage("testuser", "testmessage");
		
		for (Client client : clients)
		{
			verify(client).sendChatMessage("testuser", "testmessage");
		}
	}
	
	@Test
	public void testDefaultCurrentPlayer()
	{
		Game game = new Game(new Long(1), GameMode.CLASSIC, 5, 1, 1);
		Assert.assertNull(game.getCurrentPlayer());
		
		game.addPlayer(mock(Client.class));

		Assert.assertNotNull(game.getCurrentPlayer());
	}
	
	@Test
	public void testGetNextPlayer()
	{
		Game game = new Game(new Long(1), GameMode.CLASSIC, 5, 1, 1);
		for (Long i=new Long(0); i<5; i++)
		{
			Client client = mock(Client.class);
			when(client.getId()).thenReturn(i);
			game.addPlayer(client);
		}
		
		assertEquals(new Long(0), game.getCurrentPlayer().getId());

		// 0 is the first, so the next will be 1
		assertEquals(new Long(1), game.getNextPlayer().getId());
		assertEquals(new Long(2), game.getNextPlayer().getId());
		assertEquals(new Long(3), game.getNextPlayer().getId());
		assertEquals(new Long(4), game.getNextPlayer().getId());
		
		// start over
		assertEquals(new Long(0), game.getNextPlayer().getId());
		assertEquals(new Long(1), game.getNextPlayer().getId());
	}
}
