package com.jostrobin.battleships.view.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.view.components.ComboBoxItem;
import com.jostrobin.battleships.view.listeners.EventListener;

public class CreateGameFrame extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private JLabel createGameLabel;

    private JPanel optionsPanel;
    
    private JButton createGameButton;

    private JButton cancelButton;

    private JPanel buttonPanel;

    private JLabel modeLabel;

    private JComboBox modeComboBox;

    private JLabel nrOfPlayersLabel;

    private JComboBox nrOfPlayersComboBox;

    private JLabel fieldSizeLabel;

    private JComboBox fieldSizeComboBox;
    
    private JLabel aircraftCarrierLabel;
    
    private JComboBox aircraftCarrierComboBox;
    
    private JLabel battleshipLabel;
    
    private JComboBox battleshipComboBox;
    
    private JLabel destroyerLabel;
    
    private JComboBox destroyerComboBox;
    
    private JLabel submarineLabel;
    
    private JComboBox submarineComboBox;
    
    private JLabel patrolBoatLabel;
    
    private JComboBox patrolBoatComboBox;

    private int y = 0;

    private List<EventListener<GameMode>> createGameListeners = new ArrayList<EventListener<GameMode>>();

    private List<EventListener<Object>> cancelListeners = new ArrayList<EventListener<Object>>();

    public CreateGameFrame()
    {
        buildGui();
        updateUiState();
        this.setVisible(true);
    }

    private void buildGui()
    {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 400));

        createGameLabel = new JLabel("New game");
        GridBagConstraints c = createConstraint(0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 0, 0);
        this.add(createGameLabel, c);

        optionsPanel = new JPanel(new GridBagLayout());
        c = createConstraint(0, 1);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 0, 0, 0);
//        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(optionsPanel, c);

        buttonPanel = new JPanel();
        createGameButton = new JButton("Create");
        createGameButton.addActionListener(this);
        buttonPanel.add(createGameButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        c = createConstraint(0, 3);
        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(buttonPanel, c);

        addOptions();
    }

    private void addOptions()
    {
        modeLabel = new JLabel("Mode");
        GridBagConstraints c = createConstraint(0, y);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 0, 0, 0);
        c.weightx = 1;
        optionsPanel.add(modeLabel, c);

        // add all the game modes to a dropdown
        ComboBoxItem[] modes = new ComboBoxItem[GameMode.values().length];
        int i = 0;
        for (GameMode mode : GameMode.values())
        {
            ComboBoxItem item = new ComboBoxItem(mode, mode.name());
            modes[i++] = item;
        }
        modeComboBox = new JComboBox(modes);
        modeComboBox.addActionListener(this);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(modeComboBox, c);

        nrOfPlayersLabel = new JLabel("Number of players");
        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(nrOfPlayersLabel, c);

        Object[] nrOfPlayerItems = new Object[2];
        nrOfPlayerItems[0] = "2";
        nrOfPlayerItems[1] = "4";
        nrOfPlayersComboBox = new JComboBox(nrOfPlayerItems);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(nrOfPlayersComboBox, c);

        fieldSizeLabel = new JLabel("Field size");
        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(fieldSizeLabel, c);

        Object[] fieldSizeItems = new Object[3];
        fieldSizeItems[0] = "10 x 10";
        fieldSizeItems[1] = "15 x 15";
        fieldSizeItems[2] = "20 x 20";
        fieldSizeComboBox = new JComboBox(fieldSizeItems);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(fieldSizeComboBox, c);
        
        addShips();
    }
    
    private void addShips()
    {
    	aircraftCarrierLabel = new JLabel("Aircraft carrier");
    	battleshipLabel = new JLabel("Battleship");
    	destroyerLabel = new JLabel("Destroyer");
    	submarineLabel = new JLabel("Submarine");
    	patrolBoatLabel = new JLabel("Patrol boat");
    	
    	ComboBoxItem[] items = new ComboBoxItem[5];
    	items[0] = new ComboBoxItem(0, "0");
    	items[1] = new ComboBoxItem(1, "1");
    	items[2] = new ComboBoxItem(2, "2");
    	items[3] = new ComboBoxItem(3, "3");
    	items[4] = new ComboBoxItem(4, "4");
    	
    	aircraftCarrierComboBox = new JComboBox(items);
    	battleshipComboBox = new JComboBox(items);
    	destroyerComboBox = new JComboBox(items);
    	submarineComboBox = new JComboBox(items);
    	patrolBoatComboBox = new JComboBox(items);
    	initializeClassicShips();

        GridBagConstraints c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(aircraftCarrierLabel, c);

        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(aircraftCarrierComboBox, c);

        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(battleshipLabel, c);

        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(battleshipComboBox, c);

        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(destroyerLabel, c);

        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(destroyerComboBox, c);

        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(submarineLabel, c);

        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(submarineComboBox, c);

        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(patrolBoatLabel, c);

        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(patrolBoatComboBox, c);
    }
    
    private void initializeClassicShips()
    {
    	aircraftCarrierComboBox.setSelectedIndex(0);
    	battleshipComboBox.setSelectedIndex(1);
    	destroyerComboBox.setSelectedIndex(1);
    	submarineComboBox.setSelectedIndex(1);
    	patrolBoatComboBox.setSelectedIndex(1);
    }
    
    private void initializeCustomShips()
    {
    	aircraftCarrierComboBox.setSelectedIndex(1);
    	battleshipComboBox.setSelectedIndex(1);
    	destroyerComboBox.setSelectedIndex(0);
    	submarineComboBox.setSelectedIndex(1);
    	patrolBoatComboBox.setSelectedIndex(2);
    }
    
    private void initializeHardcoreShips()
    {
    	aircraftCarrierComboBox.setSelectedIndex(0);
    	battleshipComboBox.setSelectedIndex(0);
    	destroyerComboBox.setSelectedIndex(2);
    	submarineComboBox.setSelectedIndex(2);
    	patrolBoatComboBox.setSelectedIndex(2);
    }
    
    private void showShipsPanel()
    {
    	aircraftCarrierComboBox.setVisible(true);
    	aircraftCarrierLabel.setVisible(true);
    	battleshipComboBox.setVisible(true);
    	battleshipLabel.setVisible(true);
    	destroyerComboBox.setVisible(true);
    	destroyerLabel.setVisible(true);
    	submarineComboBox.setVisible(true);
    	submarineLabel.setVisible(true);
    	patrolBoatComboBox.setVisible(true);
    	patrolBoatLabel.setVisible(true);
    }
    
    private void hideShipsPanel()
    {
    	aircraftCarrierComboBox.setVisible(false);
    	aircraftCarrierLabel.setVisible(false);
    	battleshipComboBox.setVisible(false);
    	battleshipLabel.setVisible(false);
    	destroyerComboBox.setVisible(false);
    	destroyerLabel.setVisible(false);
    	submarineComboBox.setVisible(false);
    	submarineLabel.setVisible(false);
    	patrolBoatComboBox.setVisible(false);
    	patrolBoatLabel.setVisible(false);
    }

    /**
     * Sets the enabled disabled flags depending on the selected options.
     */
    public void updateUiState()
    {
        ComboBoxItem item = (ComboBoxItem) modeComboBox.getSelectedItem();
        GameMode mode = (GameMode) item.getKey();
        if (mode == GameMode.CLASSIC)
        {
        	initializeClassicShips();
            nrOfPlayersComboBox.setEnabled(false);
            fieldSizeComboBox.setEnabled(false);
            hideShipsPanel();
        }
        else if (mode == GameMode.HARDCORE)
        {
        	initializeHardcoreShips();
            nrOfPlayersComboBox.setEnabled(false);
            fieldSizeComboBox.setEnabled(false);
            hideShipsPanel();
        }
        else if (mode == GameMode.CUSTOM)
        {
        	initializeCustomShips();
            nrOfPlayersComboBox.setEnabled(true);
            fieldSizeComboBox.setEnabled(true);
            showShipsPanel();
        }
    }

    private GridBagConstraints createConstraint(int gridx, int gridy)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == createGameButton)
        {
            ComboBoxItem item = (ComboBoxItem) modeComboBox.getSelectedItem();
            GameMode mode = (GameMode) item.getKey();
            for (EventListener<GameMode> listener : createGameListeners)
            {
                listener.actionPerformed(mode);
            }
            showWaitingForPlayers();
        }
        else if (source == modeComboBox)
        {
            updateUiState();
        }
        else if (source == cancelButton)
        {
            for (EventListener<Object> cancelListener : cancelListeners)
            {
                cancelListener.actionPerformed(null);
            }
            cancelGame();
        }
    }
    
    public int getNumberOfPlayersAllowed()
    {
    	String number = (String) nrOfPlayersComboBox.getSelectedItem();
    	try
    	{
    		int numberOfPlayers = Integer.parseInt(number);
    		return numberOfPlayers;
    	}
    	catch (NumberFormatException e)
    	{
    		return 2; // DEFAULT
    	}
    }
    
    public int getSelectedFieldSize()
    {
    	String size = (String) fieldSizeComboBox.getSelectedItem();
    	try
    	{
    		String[] sizeParts = size.split(" x ");
    		int fieldSize = Integer.parseInt(sizeParts[0]);
    		return fieldSize;
    	}
    	catch (NumberFormatException e)
    	{
    		return 10; // DEFAULT
    	}
    }
    
    public int getNumberOfAircraftCarriers()
    {
    	return (Integer)((ComboBoxItem)aircraftCarrierComboBox.getSelectedItem()).getKey();
    }
    
    public int getNumberOfBattleships()
    {
    	return (Integer)((ComboBoxItem)battleshipComboBox.getSelectedItem()).getKey();
    }
    
    public int getNumberOfDestroyers()
    {
    	return (Integer)((ComboBoxItem)destroyerComboBox.getSelectedItem()).getKey();
    }
    
    public int getNumberOfSubmarines()
    {
    	return (Integer)((ComboBoxItem)submarineComboBox.getSelectedItem()).getKey();
    }
    
    public int getNumberOfPatrolBoats()
    {
    	return (Integer)((ComboBoxItem)patrolBoatComboBox.getSelectedItem()).getKey();
    }
    
    public void cancelGame()
    {
        createGameLabel.setText("New game");
        createGameButton.setEnabled(true);
        modeComboBox.setEnabled(true);
        modeComboBox.setSelectedItem(GameMode.CLASSIC);
        nrOfPlayersComboBox.setEnabled(false);
        fieldSizeComboBox.setEnabled(false);
    }

    public void showWaitingForPlayers()
    {
        createGameLabel.setText("Waiting for players to join");
        createGameButton.setEnabled(false);
        modeComboBox.setEnabled(false);
        nrOfPlayersComboBox.setEnabled(false);
        fieldSizeComboBox.setEnabled(false);
    }

    public void showMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message);
    }

    public void addCreateGameListener(EventListener<GameMode> listener)
    {
        createGameListeners.add(listener);
    }

    public void removeCreateGameListener(EventListener<GameMode> listener)
    {
        createGameListeners.remove(listener);
    }

    public void addCancelListener(EventListener<Object> listener)
    {
        cancelListeners.add(listener);
    }

    public void removeCancelListener(EventListener<Object> listener)
    {
        cancelListeners.remove(listener);
    }
}
