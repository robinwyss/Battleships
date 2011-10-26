package com.jostrobin.battleships.ui.frames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationDialog extends JDialog implements ActionListener
{
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationDialog.class);

	private JPanel panel;
	private JButton okButton;
	private JTextField textField;

	public RegistrationDialog()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		panel = new JPanel();
		panel.setLayout(gridBagLayout);

		addLabel();
		addTextField();
		addButton();

		add(panel);

		setSize(300, 80);
		setResizable(true);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void addButton()
	{
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.gridx = 1;
		buttonConstraints.gridy = 1;
		buttonConstraints.anchor = GridBagConstraints.BASELINE;
		panel.add(okButton, buttonConstraints);
	}

	private void addLabel()
	{
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridwidth = 2;
		labelConstraints.anchor = GridBagConstraints.CENTER;
		panel.add(new JLabel("Please provide a name"), labelConstraints);
	}

	private void addTextField()
	{
		textField = new JTextField(10);
		GridBagConstraints textFieldConstraints = new GridBagConstraints();
		textFieldConstraints.gridy = 1;
		textFieldConstraints.anchor = GridBagConstraints.PAGE_START;
		panel.add(textField, textFieldConstraints);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(actionEvent.getSource().equals(okButton))
		{
			String text = textField.getText();
			LOG.debug("User provided name {}", text);
			dispose();
		}
	}
}
