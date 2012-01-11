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
import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.view.frames.GameFrame;
import com.jostrobin.battleships.view.listeners.AttackListener;
import com.jostrobin.battleships.view.sound.SoundEffects;
import org.springframework.beans.factory.InitializingBean;

public class GameController implements NetworkListener, InitializingBean, AttackListener
{
    private ApplicationController applicationController;

    private SoundEffects soundEffects;

    private GameFrame gameFrame;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        applicationController.addNetworkListener(this);
        gameFrame.addAttackListener(this);
    }

    @Override
    public void notify(Command command)
    {
        switch (command.getCommand())
        {
            case Command.ATTACK_RESULT:
                AttackResult result = command.getAttackResult();
                if (result.equals(AttackResult.HIT) || result.equals(AttackResult.SHIP_DESTROYED) || result.equals(AttackResult.PLAYER_DESTROYED))
                {
                    soundEffects.explosion();
                }
                gameFrame.hitCell(command);
                gameFrame.changeCurrentPlayer(command.getClientId());
                break;
        }
    }

    @Override
    public void attack(int x, int y, Long clientId)
    {
        applicationController.sendAttack(x, y, clientId);
    }

    public ApplicationController getApplicationController()
    {
        return applicationController;
    }

    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }

    public GameFrame getGameFrame()
    {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame)
    {
        this.gameFrame = gameFrame;
    }

    public void setSoundEffects(SoundEffects soundEffects)
    {
        this.soundEffects = soundEffects;
    }
}
