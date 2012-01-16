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

package com.jostrobin.battleships.view.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.common.PlacementHelper;
import com.jostrobin.battleships.common.data.Ship;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.model.ShipsModel;
import com.jostrobin.battleships.view.listeners.AttackListener;
import com.jostrobin.battleships.view.panels.BattleFieldPanel;
import com.jostrobin.battleships.view.panels.ChatPanel;
import com.jostrobin.battleships.view.panels.GamePanel;
import com.jostrobin.battleships.view.panels.PlacementPanel;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 18:00
 */
@SuppressWarnings("serial")
public class GameFrame extends JPanel implements InitializingBean, AttackListener
{
    private GamePanel gamePanel;
    private PlacementPanel placementPanel;
    private ChatPanel chatPanel;
    private SortedMap<Long, String> participants;
    private List<AttackListener> attackListeners = new ArrayList<AttackListener>();
    private int length;
    private int width;
    private ShipsModel shipsModel;
    private PlacementHelper placementHelper;
    private Long playerId;
    private BattleFieldPanel battleFieldPanel;

    public void reset()
    {
        participants = null;
        gamePanel.reset();
        placementPanel.reset();
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {

        setLayout(new GridBagLayout());

        addGamePanel(0, 0, 1);
        addPlacementPanel(0, 0);
        addChatPanel(0, 1);
        gamePanel.setVisible(false);
        placementPanel.setVisible(true);
        setVisible(true);

        setPreferredSize(new Dimension(400, 450));
        gamePanel.addAttackListener(this);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addPlacementPanel(int gridX, int gridY)
    {
        GridBagConstraints placementPanelConstraints = new GridBagConstraints();
        placementPanelConstraints.weightx = 1.0;
        placementPanelConstraints.weighty = 0.8;
        placementPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        placementPanelConstraints.fill = GridBagConstraints.BOTH;
        placementPanelConstraints.gridy = gridY;
        placementPanelConstraints.gridx = gridX;
        add(placementPanel, placementPanelConstraints);
    }

    private void addGamePanel(int gridX, int gridY, int gridWidth)
    {
        GridBagConstraints battlefieldConstraints = new GridBagConstraints();
        battlefieldConstraints.weightx = 1.0;
        battlefieldConstraints.weighty = 0.8;
        battlefieldConstraints.gridy = gridY;
        battlefieldConstraints.gridx = gridX;
        battlefieldConstraints.gridwidth = gridWidth;
        battlefieldConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        battlefieldConstraints.fill = GridBagConstraints.BOTH;
        add(gamePanel, battlefieldConstraints);
    }

    public void addChatPanel(int gridX, int gridY)
    {
        GridBagConstraints chatPanelConstraints = new GridBagConstraints();
        chatPanelConstraints.gridy = gridY;
        chatPanelConstraints.gridx = gridX;
        chatPanelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        chatPanelConstraints.fill = GridBagConstraints.BOTH;
        chatPanelConstraints.weightx = 1.0;
        chatPanelConstraints.weighty = 0.2;
        add(chatPanel, chatPanelConstraints);
    }

    public void showGameView(Long startingPlayer)
    {
        setPreferredSize(new Dimension(600, 600));
        gamePanel.setVisible(true);
        placementPanel.setVisible(false);
        createGameView(length, width, participants.get(participants.firstKey()));
        changeCurrentPlayer(startingPlayer);
        placeShips();
    }

    public void showPlacementView()
    {
        gamePanel.setVisible(false);
        placementPanel.setVisible(true);
    }

    public void initializeFields(int length, int width, SortedMap<Long, String> participants)
    {
        this.width = width;
        this.length = length;
        this.participants = participants;
        // first entry is the current player
        playerId = participants.firstKey();
        // remove it from the map 
        Map<Long, String> opponents = new HashMap<Long, String>(participants);
        opponents.remove(playerId);
        gamePanel.initUi(length, width, opponents);
        initializeFieldSize(length, width);
    }

    private void createGameView(int length, int width, String playerName)
    {
        removeAll();
        addBattleField(length, width, playerName, 0, 0);
        addChatPanel(1, 0);
        addGamePanel(0, 1, 2);
    }

    private void addBattleField(int length, int width, String playerName, int gridX, int gridY)
    {
        battleFieldPanel = new BattleFieldPanel(playerName);
        battleFieldPanel.initializeFieldSize(length, width);
        battleFieldPanel.setSelectable(false);
        placementHelper = new PlacementHelper(battleFieldPanel);
        GridBagConstraints battlefieldConstraints = new GridBagConstraints();
        battlefieldConstraints.weightx = 1.0;
        battlefieldConstraints.weighty = 0.8;
        battlefieldConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        battlefieldConstraints.fill = GridBagConstraints.BOTH;
        battlefieldConstraints.gridy = gridY;
        battlefieldConstraints.gridx = gridX;
        add(battleFieldPanel, battlefieldConstraints);
    }

    public void hitCell(Command command)
    {
        if (command.getAttackedClient().equals(playerId))
        {
            battleFieldPanel.hitCell(command.getX(), command.getY(), command.getAttackResult());
        }
        else
        {
            gamePanel.hitCell(command);
        }
    }

    /**
     * Adds a ship to the currently attacked field. Used when a ship has been destroyed and we receive its complete
     * position.
     *
     * @param ship
     */
    public void addShip(Long attackedClientId, Ship ship)
    {
        if (attackedClientId.equals(playerId))
        {
            placementHelper.placeShipWithoutCheck(ship, ship.getPositionX(), ship.getPositionY());
            ship.setSelected(false);
        }
        else
        {
            gamePanel.addShip(attackedClientId, ship);

        }
    }

    public void showWinnerDialog()
    {
        JOptionPane.showMessageDialog(this, "You have won", "Game End", JOptionPane.PLAIN_MESSAGE);
    }

    public void showWinnerDialog(String username)
    {
        JOptionPane.showMessageDialog(this, username + " has won", "Game End", JOptionPane.PLAIN_MESSAGE);
    }

    public void showDestroyedDialog()
    {
        JOptionPane.showMessageDialog(this, "You have been destroyed", "Destroyed", JOptionPane.PLAIN_MESSAGE);
    }

    public void showDestroyedDialog(String username)
    {
        JOptionPane.showMessageDialog(this, username + " has  been destroyed", "Destroyed", JOptionPane.PLAIN_MESSAGE);
    }

    public void initializeFieldSize(int length, int width)
    {
        gamePanel.initializeFieldSize(length, width);
        placementPanel.setFieldSize(length, width);
    }

    public void addAttackListener(AttackListener listener)
    {
        attackListeners.add(listener);
    }

    public GamePanel getGamePanel()
    {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
    }

    public PlacementPanel getPlacementPanel()
    {
        return placementPanel;
    }

    public void setPlacementPanel(PlacementPanel placementPanel)
    {
        this.placementPanel = placementPanel;
    }

    public ChatPanel getChatPanel()
    {
        return chatPanel;
    }

    public void setChatPanel(ChatPanel chatPanel)
    {
        this.chatPanel = chatPanel;
    }

    public Map<Long, String> getParticipants()
    {
        return participants;
    }

    public void setParticipants(SortedMap<Long, String> participants)
    {
        this.participants = participants;
    }

    @Override
    public void attack(int x, int y, Long clientId)
    {
        for (AttackListener listener : attackListeners)
        {
            listener.attack(x, y, clientId);
        }
    }

    public void changeCurrentPlayer(Long playerId)
    {
        gamePanel.changeCurrentPlayer(playerId);
        battleFieldPanel.setCurrent(playerId.equals(this.playerId));
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
