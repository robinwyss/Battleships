package com.jostrobin.battleships.view.panels;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

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
	public void initUi(int length, int width, List<Long> participants)
	{
		setLayout(new FlowLayout());

		battlefieldPanels = new HashMap<Long, BattleFieldPanel>();
		for (Long id : participants)
		{
			BattleFieldPanel panel = new BattleFieldPanel();
			panel.setFieldSize(length, width);
			battlefieldPanels.put(id, panel);
			this.add(panel);
		}
	}

    public void setFieldSize(int length, int width)
    {
    	for (Long clientId : battlefieldPanels.keySet())
    	{
    		BattleFieldPanel panel = battlefieldPanels.get(clientId);
    		panel.setFieldSize(length, width);
    	}
    }
	
	
}
