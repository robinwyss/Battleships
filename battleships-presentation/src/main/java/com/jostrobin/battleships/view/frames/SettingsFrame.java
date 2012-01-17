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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    private JLabel themeLabel;

    private JComboBox themeComboBox;

    private JLabel enableSoundLabel;

    private JComboBox nrOfPlayersComboBox;

    private JButton okButton;

    private JButton cancelButton;

    private int y;

    private JCheckBox enableSoundCheckBox;

    private Settings settings;

    private List<EventListener<Object>> okListeners = new ArrayList<EventListener<Object>>();

    private List<EventListener<Object>> cancelListeners = new ArrayList<EventListener<Object>>();

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
        okButton = new JButton("OK");
        okButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        c = createConstraint(0, 3);
        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(buttonPanel, c);

        addOptions();
    }

    private void addOptions()
    {
        themeLabel = new JLabel("Theme");
        GridBagConstraints c = createConstraint(0, y);
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(5, 0, 0, 5);
        c.weightx = 1;
        optionsPanel.add(themeLabel, c);

        // add all the game themes to a dropdown
        ComboBoxItem[] themes = new ComboBoxItem[ThemeDescription.values().length];
        int i = 0;
        for (ThemeDescription theme : ThemeDescription.values())
        {
            ComboBoxItem item = new ComboBoxItem(theme, theme.getName());
            themes[i++] = item;
        }
        themeComboBox = new JComboBox(themes);
        themeComboBox.addActionListener(this);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(themeComboBox, c);

        enableSoundLabel = new JLabel("Enable Sounds");
        c = createConstraint(0, y);
        c.insets = new Insets(5, 0, 0, 5);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(enableSoundLabel, c);

        enableSoundCheckBox = new JCheckBox();
        enableSoundCheckBox.addActionListener(this);
        c = createConstraint(1, y++);
        c.insets = new Insets(5, 0, 0, 0);
        c.anchor = GridBagConstraints.LINE_START;
        optionsPanel.add(enableSoundCheckBox, c);

    }

    public void resetSettings()
    {
        enableSoundCheckBox.setSelected(settings.isSoundEnabled());
        ThemeDescription theme = settings.getTheme();
        themeComboBox.setSelectedItem(new ComboBoxItem(theme, theme.getName()));
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
        if (actionEvent.getSource() == okButton)
        {
            settings.setSoundEnabled(enableSoundCheckBox.isSelected());
            ComboBoxItem item = (ComboBoxItem) themeComboBox.getSelectedItem();
            ThemeDescription theme = (ThemeDescription) item.getKey();
            settings.setTheme(theme);
            for (EventListener<Object> listener : okListeners)
            {
                listener.actionPerformed(null);
            }
        }
        else if (actionEvent.getSource() == cancelButton)
        {
            for (EventListener<Object> listener : cancelListeners)
            {
                listener.actionPerformed(null);
            }
        }
    }


    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public void removeOkListener(EventListener<Object> eventListener)
    {
        okListeners.remove(eventListener);
    }

    public void addOkListener(EventListener<Object> eventListener)
    {
        this.okListeners.add(eventListener);
    }

    public void removeCancelListener(EventListener<Object> eventListener)
    {
        cancelListeners.remove(eventListener);
    }

    public void addCancelListener(EventListener<Object> eventListener)
    {
        this.cancelListeners.add(eventListener);
    }
}
