package com.jostrobin.battleships.ui.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.jostrobin.battleships.data.GameSettings;
import com.jostrobin.battleships.data.enums.GameMode;
import com.jostrobin.battleships.ui.components.ComboBoxItem;
import com.jostrobin.battleships.ui.controller.CreateGameController;

public class CreateGameFrame extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private JLabel createGameLabel;

    private JPanel optionsPanel;

    private JButton createGameButton;

    private JLabel modeLabel;

    private JComboBox modeComboBox;

    private JLabel nrOfPlayersLabel;

    private JComboBox nrOfPlayersComboBox;

    private JLabel fieldSizeLabel;

    private JComboBox fieldSizeComboBox;

    private int y = 0;

    private CreateGameController controller;

    public CreateGameFrame(CreateGameController controller)
    {
        this.controller = controller;

        buildGui();
        updateUiState();
        this.setVisible(true);
    }

    private void buildGui()
    {
        this.setLayout(new GridBagLayout());
        this.setSize(350, 200);
        //this.setResizable(false);

        createGameLabel = new JLabel("New game");
        GridBagConstraints c = createConstraint(0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 0, 0);
        this.add(createGameLabel, c);

        optionsPanel = new JPanel(new GridBagLayout());
        c = createConstraint(0, 1);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(optionsPanel, c);

        createGameButton = new JButton("Create");
        createGameButton.addActionListener(this);
        c = createConstraint(0, 2);
        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(createGameButton, c);

        addOptions();
    }

    private void addOptions()
    {
        modeLabel = new JLabel("Mode");
        GridBagConstraints c = createConstraint(0, y);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 0, 0, 0);
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

        Object[] nrOfPlayerItems = new Object[1];
        nrOfPlayerItems[0] = "2";
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

        Object[] fieldSizeItems = new Object[1];
        fieldSizeItems[0] = "10 x 10";
        fieldSizeComboBox = new JComboBox(fieldSizeItems);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(fieldSizeComboBox, c);
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
            nrOfPlayersComboBox.setEnabled(false);
            fieldSizeComboBox.setEnabled(false);
        }
        else if (mode == GameMode.HARDCORE)
        {
            nrOfPlayersComboBox.setEnabled(false);
            fieldSizeComboBox.setEnabled(false);
        }
        else if (mode == GameMode.CUSTOM)
        {
            nrOfPlayersComboBox.setEnabled(true);
            fieldSizeComboBox.setEnabled(true);
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
            if (mode == GameMode.CLASSIC)
            {
                GameSettings settings = new GameSettings();
                settings.setMode(mode);
                settings.setCanMove(false);
                settings.setFieldHeight(10);
                settings.setFieldWidth(10);
                settings.setNumberOfPlayers(2);

                controller.createGame(settings);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Currently only classic mode is supported.");
            }
        }
        else if (source == modeComboBox)
        {
            updateUiState();
        }
    }
}