package com.jostrobin.battleships.view.panels;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.jostrobin.battleships.common.data.GameData;

public class GamePanel extends JPanel
{
	/**
	 * Map holding a battlefieldpanel for each of the participants. clientId as key, panel as value.
	 */
	private Map<Long, BattleFieldPanel> battlefieldPanels;
	
	/**
	 * Initializes the UI for the specified game type.
	 * @param gameData
	 */
	private void initUi(GameData gameData)
	{
		setLayout(new FlowLayout());
		int maxPlayers = gameData.getMaxPlayers();
		int width = gameData.getFieldWidth();
		int length = gameData.getFieldLength();

		battlefieldPanels = new HashMap<Long, BattleFieldPanel>();
		
	}
	
	
}
