/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jostrobin.battleships.view.frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rowyss
 *         Date: 19.10.11 Time: 17:02
 */
public class RegistrationDialog extends JPanel implements ActionListener, KeyListener
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationDialog.class);
    private List<com.jostrobin.battleships.view.listeners.ActionListener<String>> actionListeners = new ArrayList<com.jostrobin.battleships.view.listeners.ActionListener<String>>();
    private JLabel messageLabel;
    private JPanel panel;
    private JButton okButton;
    private JTextField textField;

    int y = 0;

    public RegistrationDialog()
    {
        createUI();
    }

    private void createUI()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel = new JPanel();
        panel.setLayout(gridBagLayout);

        addMessageLabel();
        addLabel();
        addTextField();
        addButton();

        add(panel);

        textField.addKeyListener(this);
        okButton.addKeyListener(this);

        setPreferredSize(new Dimension(300, 90));
        setMinimumSize(new Dimension(250, 70));
//        setResizable(false);
        setVisible(true);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void addMessageLabel()
    {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridwidth = 2;
        labelConstraints.gridy = y++;
        labelConstraints.anchor = GridBagConstraints.CENTER;
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        messageLabel.setVisible(false);
        panel.add(messageLabel, labelConstraints);
    }


    private void addLabel()
    {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridwidth = 2;
        labelConstraints.gridy = y++;
        labelConstraints.anchor = GridBagConstraints.CENTER;
        panel.add(new JLabel("Please provide a name"), labelConstraints);
    }

    private void addTextField()
    {
        textField = new JTextField(10);
        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        textFieldConstraints.gridy = y++;
        textFieldConstraints.anchor = GridBagConstraints.PAGE_START;
        panel.add(textField, textFieldConstraints);
    }

    private void addButton()
    {
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.anchor = GridBagConstraints.BASELINE;
        panel.add(okButton, buttonConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getSource().equals(okButton))
        {
            registerUser();
        }
    }

    private void registerUser()
    {
        String text = textField.getText();
        for (com.jostrobin.battleships.view.listeners.ActionListener<String> actionListener : actionListeners)
        {
            actionListener.actionPerformed(text);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        {
            registerUser();
        }

    }

    public void showMessage(String message)
    {
        messageLabel.setText(message);
        messageLabel.setVisible(true);
    }

    public void addActionListener(com.jostrobin.battleships.view.listeners.ActionListener<String> actionListener)
    {
        actionListeners.add(actionListener);
    }

    public void removeActionListener(com.jostrobin.battleships.view.listeners.ActionListener<String> actionListener)
    {
        actionListeners.remove(actionListener);
    }
}
