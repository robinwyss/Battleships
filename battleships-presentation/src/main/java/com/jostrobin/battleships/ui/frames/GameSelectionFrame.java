package com.jostrobin.battleships.ui.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jostrobin.battleships.common.observer.Observer;
import com.jostrobin.battleships.data.ServerInformation;
import com.jostrobin.battleships.data.enums.State;
import com.jostrobin.battleships.ui.controller.GameSelectionController;

public class GameSelectionFrame extends JPanel implements ActionListener, Observer<List<ServerInformation>>
{
    private static final long serialVersionUID = 1L;

    private GameSelectionController controller;

    private JPanel availableGamesPanel;

    private JPanel buttonsPanel;

    private JLabel availableGamesLabel;

    private JButton createGameButton;

    private JButton settingsButton;

    private JButton refreshButton;

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

        buildGui();

        refreshState();
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(700, 500));
    }

    private void buildGui()
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
        String[] columnNames = {"Player", "Mode", "Number of players", "Host", "State"};
        java.util.List<ServerInformation> servers = new ArrayList<ServerInformation>();
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source == refreshButton)
        {
            controller.refresh(false);
        }
        else if (source == exitButton)
        {
            controller.exit();
        }
        else if (source == joinButton)
        {
            int row = availableGamesTable.getSelectedRow();
            if (row > -1)
            {
                ServerInformation server = tableModel.getServerAtRow(row);
                if (server != null)
                {
                    controller.joinGame(server);
                }
            }
        }
        else if (source == createGameButton)
        {
            controller.createGame();
        }
    }

    public void setServers(java.util.List<ServerInformation> servers)
    {
        tableModel.setServers(servers);
        refreshState();
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

        refreshButton = createButton("Refresh");
        refreshButton.addActionListener(this);
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(refreshButton, c);

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

    /**
     * Reevaluates which components should be enabled/disabled.
     */
    public void refreshState()
    {
        int row = availableGamesTable.getSelectedRow();
        boolean enableJoinButton = false;
        if (row > -1)
        {
            ServerInformation server = tableModel.getServerAtRow(row);
            if (server != null && server.getState().getState() == State.WAITING_FOR_PLAYERS)
            {
                enableJoinButton = true;
            }
        }
        joinButton.setEnabled(enableJoinButton);
    }

    @Override
    public void update(List<ServerInformation> serverList)
    {
        setServers(serverList);
    }
}
