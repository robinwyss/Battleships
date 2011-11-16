package com.jostrobin.battleships.view.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jostrobin.battleships.controller.ServerDetectionController;

public class ServerDetectionFrame extends JFrame implements ActionListener, Observer
{
	private static final long serialVersionUID = 1L;
	
	private ServerDetectionController controller;

	private JLabel statusLabel;
	
	private JButton retryButton;
	
	public ServerDetectionFrame(ServerDetectionController controller)
	{
		this.controller = controller;
		this.setLayout(new GridBagLayout());
		
		buildUi();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 120);
		this.setMinimumSize(new Dimension(400, 120));
		this.setVisible(true);
	}
	
	private void buildUi()
	{
		statusLabel = new JLabel("Searching Battleships server...");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(statusLabel, c);
		
		retryButton = new JButton("Retry");
		retryButton.addActionListener(this);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		this.add(retryButton, c);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == retryButton)
		{
			controller.retry();
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (o instanceof ServerDetectionController && arg instanceof InetAddress)
		{
			// we found a server
			controller.startApplication((InetAddress)arg);
			this.dispose();
		}
	}

}
