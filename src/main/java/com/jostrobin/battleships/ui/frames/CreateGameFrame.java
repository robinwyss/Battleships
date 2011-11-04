package com.jostrobin.battleships.ui.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateGameFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JLabel createGameLabel;
	
	private JPanel optionsPanel;
	
	private int x = 0;
	
	private int y = 0;

	public CreateGameFrame()
	{
		
	}
	
    private void buildGui()
    {
        this.setLayout(new GridBagLayout());

        optionsPanel = new JPanel(new GridBagLayout());
        this.setMinimumSize(new Dimension(700, 500));
        GridBagConstraints c = createConstraint(0, 0);
        c.insets = new Insets(15, 15, 15, 15);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(optionsPanel, c);

//        optionsPanel = new JLabel("Available games");
//        c = createConstraint(x, y++, 2, 1);
//        c.anchor = GridBagConstraints.FIRST_LINE_START;
//        c.insets = new Insets(0, 5, 0, 0);
//        optionsPanel.add(optionsPanel, c);
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
