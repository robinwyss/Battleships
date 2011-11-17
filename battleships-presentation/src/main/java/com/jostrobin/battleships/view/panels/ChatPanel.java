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

package com.jostrobin.battleships.view.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author rowyss
 *         Date: 28.10.11 Time: 18:01
 */
public class ChatPanel extends JPanel implements ActionListener
{

    private JTextArea displayArea;
    private JTextField messageField;
    private JButton sendButton;
    private int y;

    public ChatPanel()
    {
        initUI();
    }

    private void initUI()
    {
        setLayout(new GridBagLayout());

        addChatDisplay();

        addInputField();

        addSendButton();
    }

    private void addSendButton()
    {
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        GridBagConstraints sendButtonConstraints = new GridBagConstraints();
        sendButtonConstraints.gridy = y++;
        sendButtonConstraints.anchor = GridBagConstraints.BASELINE_TRAILING;
        sendButtonConstraints.gridx = 1;
        add(sendButton, sendButtonConstraints);
    }

    private void addInputField()
    {
        messageField = new JTextField();
        GridBagConstraints messageFieldConstraints = new GridBagConstraints();
        messageFieldConstraints.gridy = y;
        messageFieldConstraints.weightx = 1.0;
        messageFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        messageFieldConstraints.anchor = GridBagConstraints.BASELINE_LEADING;
        add(messageField, messageFieldConstraints);
    }

    private void addChatDisplay()
    {
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFocusable(false);
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridy = y++;
        textConstraints.gridwidth = 2;
        textConstraints.weightx = 1.0;
        textConstraints.weighty = 1.0;
        textConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
        textConstraints.fill = GridBagConstraints.BOTH;
        add(displayArea, textConstraints);
    }

    public void displayMessage(String username, String message)
    {
        displayArea.append(String.format("%s: %s", username, message));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        messageField.setText("");
    }
}
