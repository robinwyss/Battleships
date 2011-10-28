package com.jostrobin.battleships.ui.frames;

import java.awt.*;
import javax.swing.*;

public class GameSelectionFrame extends JFrame
{
    private static final long serialVersionUID = 1L;

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

    int x = 0;

    int y = 0;

    public GameSelectionFrame()
    {
        buildGui();
    }

    private void buildGui()
    {
        this.setLayout(new GridBagLayout());

        availableGamesPanel = new JPanel(new GridBagLayout());
        this.setMinimumSize(new Dimension(700, 500));
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
        String[] columnNames = {"Player", "Mode", "Number of players", "Date"};
        Object[][] data = {};
        availableGamesTable = new JTable(data, columnNames);
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
        c = createConstraint(0, buttonsY++);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(buttonInsets, buttonInsets, buttonInsets, buttonInsets);
        buttonsPanel.add(joinButton, c);

        exitButton = createButton("Exit");
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

}
