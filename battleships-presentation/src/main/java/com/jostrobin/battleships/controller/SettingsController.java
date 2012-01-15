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

package com.jostrobin.battleships.controller;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.model.Settings;
import com.jostrobin.battleships.view.frames.SettingsFrame;
import com.jostrobin.battleships.view.listeners.EventListener;
import com.jostrobin.battleships.view.sound.SoundEffects;
import com.jostrobin.battleships.view.theme.DescriptionBasedTheme;
import com.jostrobin.battleships.view.theme.ThemeDescription;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author rowyss
 *         Date: 15.01.12 Time: 09:12
 */
public class SettingsController implements InitializingBean
{

    private ApplicationController applicationController;

    private Settings settings;

    private SettingsFrame settingsFrame;

    private DescriptionBasedTheme descriptionBasedTheme;

    private SoundEffects soundEffects;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        settingsFrame.addOkListener(new OkListener());
        settingsFrame.addCancelListener(new CancelListener());
        resetSettings();
    }

    public void resetSettings()
    {
        ThemeDescription td = descriptionBasedTheme.getThemeDescription();
        boolean soundEnabled = soundEffects.isEnabled();
        settings.setTheme(td);
        settings.setSoundEnabled(soundEnabled);
        settingsFrame.resetSettings();
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public void setSettingsFrame(SettingsFrame settingsFrame)
    {
        this.settingsFrame = settingsFrame;
    }

    public void setSoundEffects(SoundEffects soundEffects)
    {
        this.soundEffects = soundEffects;
    }

    public void setDescriptionBasedTheme(DescriptionBasedTheme descriptionBasedTheme)
    {
        this.descriptionBasedTheme = descriptionBasedTheme;
    }


    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }

    private class OkListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            descriptionBasedTheme.setThemeDescription(settings.getTheme());
            soundEffects.setEnabled(settings.isSoundEnabled());
            applicationController.showGameSelection();
        }
    }

    private class CancelListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            resetSettings();
            applicationController.showGameSelection();
        }
    }
}
