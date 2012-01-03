package com.jostrobin.battleships.view.panels;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jostrobin.battleships.common.PlacementHelper;
import com.jostrobin.battleships.common.data.Cell;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.model.ShipsModel;
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

    private ShipsModel shipsModel;
    private PlacementHelper placementHelper;

    /**
     * Initializes the UI for the specified game type.
     *
     * @param length       field length
     * @param width        field width
     * @param participants list of participants
     */
    public void initUi(int length, int width, Map<Long, String> participants)
    {
        setLayout(new FlowLayout());

        battlefieldPanels = new HashMap<Long, BattleFieldPanel>();
        boolean first = true;
        for (Map.Entry<Long, String> entry : participants.entrySet())
        {
        	final Long id = entry.getKey();
            BattleFieldPanel panel = new BattleFieldPanel(entry.getValue());
            panel.initializeFieldSize(length, width);
            battlefieldPanels.put(id, panel);

            // be notified about click events
            // only add selection listener to the first panel, because this one belongs to the current player.
            if (!first)
            {
                panel.addSelectionListener(new SelectionListener<Cell>()
                {
                    @Override
                    public void selected(Cell cell)
                    {
                        cellClicked(cell, id);
                    }
                });
            }
            else
            {
                panel.setSelectable(false);
                first = false;
                placementHelper = new PlacementHelper(panel);
            }

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
     *
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

    public void hitCell(Command command)
    {
        BattleFieldPanel panel = battlefieldPanels.get(command.getClientId());
        panel.hitCell(command.getX(), command.getY(), command.getAttackResult());
    }


    public void changeCurrentPlayer(Long playerId)
    {
        for (Map.Entry<Long, BattleFieldPanel> entry : battlefieldPanels.entrySet())
        {
            BattleFieldPanel battleFieldPanel = entry.getValue();
            Long currentId = entry.getKey();
            battleFieldPanel.setCurrent(playerId.equals(currentId));
        }
    }

    public void placeShips()
    {
        for (Ship ship : shipsModel.getShips())
        {
            placementHelper.placeShip(ship, ship.getPositionX(), ship.getPositionY());
            ship.setSelected(false);
        }
    }

    public void setShipsModel(ShipsModel shipsModel)
    {
        this.shipsModel = shipsModel;
    }
}
