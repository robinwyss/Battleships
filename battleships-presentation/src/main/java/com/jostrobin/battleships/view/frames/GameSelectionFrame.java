package com.jostrobin.battleships.view.frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jostrobin.battleships.common.data.GameState;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.controller.GameSelectionController;
import com.jostrobin.battleships.model.GameSelectionModel;

public class GameSelectionFrame extends JPanel implements ActionListener, Observer
{
	private static final long serialVersionUID = 1L;
	
	private GameSelectionController controller;

    private JPanel availableGamesPanel;

    private JPanel buttonsPanel;

    private JLabel availableGamesLabel;

    private JButton createGameButton;

    private JButton settingsButton;

    private JButton filterButton;

    private JButton joinButton;

    private JButton exitButton;

    private JTextArea detailsTextArea;

    private JTable availableGamesTable;

    private BattleshipTableModel tableModel;
    
    int x = 0;
    
    int y = 0;
    
	public GameSelectionFrame(GameSelectionController controller)
	{
		this.controller = controller;
		
		buildUi();

        refreshState();
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(700, 500));
    }

    private void buildUi()
    {
        this.setLayout(new GridBagLayout());

        availableGamesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = createConstraint(0, 0);
        c.insets = new Insets(15, 15, 15, 15);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(availableGamesPanel, c);

        availableGamesLabel = new JLabel("Available games");
        c = createConstraint(x, y++, 2, 1);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 5, 0, 0);
        availableGamesPanel.add(availableGamesLabel, c);

        addTable();
        addButtonsPanel();
    }

    private void addTable()
    {
        String[] columnNames = {"Player", "Mode", "Number of players", "State"};
        java.util.List<Player> servers = new ArrayList<Player>();
        tableModel = new BattleshipTableModel(columnNames, servers);
        availableGamesTable = new JTable(tableModel);
        availableGamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableGamesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				refreshState();
			}
		});
        availableGamesTable.setVisible(true);
        availableGamesTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel tablePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = createConstraint(0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        tablePanel.add(availableGamesTable.getTableHeader(), c);

        c = createConstraint(0, 1);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        c.weightx = 1;
        tablePanel.add(availableGamesTable, c);

        c = createConstraint(x++, y);
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        availableGamesPanel.add(tablePanel, c);
    }
    
    private void addButtonsPanel()
    {
        int buttonsY = 0;
        int buttonInsets = 5;

        buttonsPanel = new JPanel(new GridBagLayout());

        createGameButton = createButton("Create game");
        createGameButton.addActionListener(this);
        GridBagConstraints c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(createGameButton, c);

        settingsButton = createButton("Settings");
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(settingsButton, c);

        filterButton = createButton("Filter");
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(filterButton, c);

        detailsTextArea = new JTextArea("");
        detailsTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        detailsTextArea.setEditable(false);
        detailsTextArea.setRows(10);
        detailsTextArea.setMargin(new Insets(3, 3, 3, 3));
        detailsTextArea.setText("Welcome to\nFabuluous\nLas Vegas!");
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(detailsTextArea, c);

        joinButton = createButton("Join");
        joinButton.addActionListener(this);
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(joinButton, c);

        exitButton = createButton("Exit");
        exitButton.addActionListener(this);
        c = createConstraint(0, buttonsY++);
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(exitButton, c);

        c = createConstraint(x, y);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.VERTICAL;
        availableGamesPanel.add(buttonsPanel, c);
    }

    private JButton createButton(String text)
    {
        JButton button = new JButton(text);
        return button;
    }

    private GridBagConstraints createConstraint(int gridx, int gridy, int width, int height)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = width;
        c.gridheight = height;
        return c;
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
        if (source == exitButton)
        {
            controller.exit();
        }
        else if (source == joinButton)
        {
        	int row = availableGamesTable.getSelectedRow();
        	if (row > -1)
        	{
        		Player player = tableModel.getPlayerAtRow(row);
        		if (player != null)
        		{
        			controller.joinGame(player);
        		}
        	}
        }
        else if (source == createGameButton)
        {
        	controller.createGame();
        }
    }
    
    /**
     * Reevaluates which components should be enabled/disabled.
     */
    public void refreshState()
    {
    	int row = availableGamesTable.getSelectedRow();
    	boolean enableJoinButton = false;
    	if (row > -1)
    	{
    		Player player = tableModel.getPlayerAtRow(row);
    		if (player != null && player.getState() == GameState.WAITING_FOR_PLAYERS)
    		{
    			enableJoinButton = true;
    		}
    	}
		joinButton.setEnabled(enableJoinButton);
    }

	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof GameSelectionModel)
		{
			GameSelectionModel model = (GameSelectionModel) o;
			tableModel.setPlayers(model.getPlayers());
			repaint();
		}
	}

}
