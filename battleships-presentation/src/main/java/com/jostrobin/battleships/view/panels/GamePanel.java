package com.jostrobin.battleships.view.panels;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.view.listeners.AttackListener;
import com.jostrobin.battleships.view.listeners.SelectionListener;

@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
	private Logger logger = LoggerFactory.getLogger(GamePanel.class);
	
	/**
	 * Map holding a battlefieldpanel for each of the participants. clientId as key, panel as value.
	 */
	private Map<Long, BattleFieldPanel> battlefieldPanels;
	
	private List<AttackListener> attackListeners = new ArrayList<AttackListener>();
	
	/**
	 * Initializes the UI for the specified game type.
	 * @param gameData
	 */
	public void initUi(int length, int width, List<Long> participants)
	{
		setLayout(new FlowLayout());

		battlefieldPanels = new HashMap<Long, BattleFieldPanel>();
		for (final Long id : participants)
		{
			BattleFieldPanel panel = new BattleFieldPanel();
			panel.initializeFieldSize(length, width);
			battlefieldPanels.put(id, panel);
			
			// be notified about click events
			panel.addSelectionListener(new SelectionListener<Cell>()
			{
				@Override
				public void selected(Cell cell)
				{
					cellClicked(cell, id);
				}
			});
			
			this.add(panel);
		}
	}

    public void initializeFieldSize(int length, int width)
    {
    	for (Long clientId : battlefieldPanels.keySet())
    	{
    		BattleFieldPanel panel = battlefieldPanels.get(clientId);
    		panel.initializeFieldSize(length, width);
    	}
    }
    
    public void addAttackListener(AttackListener listener)
    {
    	attackListeners.add(listener);
    }
    
    /**
     * The specified cell on the field of the specified player has been clicked. Called by listeners.
     * @param cell
     * @param clientId
     */
    private void cellClicked(Cell cell, Long clientId)
    {
    	logger.debug("Cell {} of client {} clicked", cell, clientId);
    	for (AttackListener listener : attackListeners)
    	{
    		listener.attack(cell.getBoardX(), cell.getBoardY(), clientId);
    	}
    }
    
    public void hitCell(int x, int y, Long clientId)
    {
    	battlefieldPanels.get(clientId).hitCell(x, y);
    }
	
	
}
