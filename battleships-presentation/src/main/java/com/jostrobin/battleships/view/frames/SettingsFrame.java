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
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.model.Settings;
import com.jostrobin.battleships.view.components.ComboBoxItem;
import com.jostrobin.battleships.view.listeners.EventListener;
import com.jostrobin.battleships.view.theme.ThemeDescription;

/**
 * @author rowyss
 *         Date: 14.01.12 Time: 08:32
 */
public class SettingsFrame extends JPanel implements ActionListener
{

    private JLabel createGameLabel;

    private JPanel optionsPanel;

    private JPanel buttonPanel;

    private JLabel modeLabel;

    private JComboBox modeComboBox;

    private JLabel nrOfPlayersLabel;

    private JComboBox nrOfPlayersComboBox;

    private JButton createGameButton;

    private int y;

    private JCheckBox enableSoundCheckBox;

    private Settings settings;

    private List<EventListener<Object>> eventListenerList = new ArrayList<EventListener<Object>>();

    public SettingsFrame()
    {
        buildGui();
    }

    private void buildGui()
    {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(300, 150));

        createGameLabel = new JLabel("Settings");
        GridBagConstraints c = createConstraint(0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(5, 5, 0, 0);
        this.add(createGameLabel, c);

        optionsPanel = new JPanel(new GridBagLayout());
        c = createConstraint(0, 1);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 5, 0, 0);

        c.weightx = 1;
        c.weighty = 1;
        this.add(optionsPanel, c);

        buttonPanel = new JPanel();
        createGameButton = new JButton("OK");
        createGameButton.addActionListener(this);
        buttonPanel.add(createGameButton);
        c = createConstraint(0, 3);
        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(buttonPanel, c);

        addOptions();
    }

    private void addOptions()
    {
        modeLabel = new JLabel("Theme");
        GridBagConstraints c = createConstraint(0, y);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 0, 0, 5);
        c.weightx = 1;
        optionsPanel.add(modeLabel, c);

        // add all the game modes to a dropdown
        ComboBoxItem[] modes = new ComboBoxItem[GameMode.values().length];
        int i = 0;
        for (ThemeDescription theme : ThemeDescription.values())
        {
            ComboBoxItem item = new ComboBoxItem(theme, theme.getName());
            modes[i++] = item;
        }
        modeComboBox = new JComboBox(modes);
        modeComboBox.addActionListener(this);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(modeComboBox, c);

        nrOfPlayersLabel = new JLabel("Enable Sounds");
        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 5);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(nrOfPlayersLabel, c);

        enableSoundCheckBox = new JCheckBox("", true);
        enableSoundCheckBox.addActionListener(this);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(enableSoundCheckBox, c);

    }

    private GridBagConstraints createConstraint(int gridx, int gridy)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getSource() == createGameButton)
        {
            settings.setSoundEnabled(enableSoundCheckBox.isEnabled());
            ComboBoxItem item = (ComboBoxItem) modeComboBox.getSelectedItem();
            ThemeDescription theme = (ThemeDescription) item.getKey();
            settings.setTheme(theme);
            for (EventListener<Object> listener : eventListenerList)
            {
                listener.actionPerformed(null);
            }
        }
    }


    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public List<EventListener<Object>> getEventListenerList()
    {
        return eventListenerList;
    }

    public void setEventListenerList(List<EventListener<Object>> eventListenerList)
    {
        this.eventListenerList = eventListenerList;
    }
}
