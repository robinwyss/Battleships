package com.jostrobin.battleships.ui.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jostrobin.battleships.enumerations.GameMode;
import com.jostrobin.battleships.ui.components.ComboBoxItem;
import com.jostrobin.battleships.ui.controller.CreateGameController;

public class CreateGameFrame extends JFrame implements ActionListener
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

        createGameLabel = new JLabel("New game");
        GridBagConstraints c = createConstraint(0, 0);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(0, 5, 0, 0);
		this.add(createGameLabel, c);
        
        optionsPanel = new JPanel(new GridBagLayout());
        this.setMinimumSize(new Dimension(700, 500));
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
    	c.anchor = GridBagConstraints.LINE_START;
    	optionsPanel.add(modeComboBox, c);

    	nrOfPlayersLabel = new JLabel("Number of players");
    	c = createConstraint(0, y);
    	c.anchor = GridBagConstraints.LINE_START;
    	optionsPanel.add(nrOfPlayersLabel, c);
    	
    	Object[] nrOfPlayerItems = new Object[1];
    	nrOfPlayerItems[0] = "2";
    	nrOfPlayersComboBox = new JComboBox(nrOfPlayerItems);
    	c = createConstraint(1, y++);
    	c.anchor = GridBagConstraints.LINE_START;
    	optionsPanel.add(nrOfPlayersComboBox, c);

    	fieldSizeLabel = new JLabel("Field size");
    	c = createConstraint(0, y);
    	c.anchor = GridBagConstraints.LINE_START;
    	optionsPanel.add(fieldSizeLabel, c);
    	
    	Object[] fieldSizeItems = new Object[1];
    	fieldSizeItems[0] = "10 x 10";
    	fieldSizeComboBox = new JComboBox(fieldSizeItems);
    	c = createConstraint(1, y++);
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
				controller.createGame();
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
