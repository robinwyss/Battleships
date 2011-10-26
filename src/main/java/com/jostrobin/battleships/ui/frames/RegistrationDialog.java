package com.jostrobin.battleships.ui.frames;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.PAGE_START;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationDialog extends JDialog
{
	private JPanel panel;
	private JButton okButton;
	private JTextField textField;

	public RegistrationDialog()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		panel = new JPanel();
		Insets insets = new Insets(0, 0, 0, 0);
		GridBagConstraints constraints = new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, BOTH, PAGE_START, insets, 0, 0);
	}
}
