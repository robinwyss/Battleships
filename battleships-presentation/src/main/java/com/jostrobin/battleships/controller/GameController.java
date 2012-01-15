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

import javax.swing.JOptionPane;

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.AttackResult;
import com.jostrobin.battleships.common.data.Player;
import com.jostrobin.battleships.common.network.Command;
import com.jostrobin.battleships.common.network.NetworkListener;
import com.jostrobin.battleships.model.GameModel;
import com.jostrobin.battleships.view.frames.GameFrame;
import com.jostrobin.battleships.view.listeners.AttackListener;
import com.jostrobin.battleships.view.sound.SoundEffects;

public class GameController implements NetworkListener, InitializingBean, AttackListener
{
    private ApplicationController applicationController;

    private SoundEffects soundEffects;

    private GameFrame gameFrame;

    private GameModel gameModel;

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
                if (command.getAttackResult() != AttackResult.INVALID)
                {
                    gameFrame.hitCell(command);

                    AttackResult result = command.getAttackResult();

                    // if a ship has been destroyed, add it to the game field
                    if (result == AttackResult.SHIP_DESTROYED || result == AttackResult.PLAYER_DESTROYED)
                    {
                        gameFrame.addShip(command.getAttackedClient(), command.getShip());
                    }
                    playSound(result);
                    checkGameUpdate(command);
                    gameFrame.changeCurrentPlayer(command.getClientId());
                }
                break;
            case Command.CLOSE_GAME:
            	// the game has been aborted, go back to main screen
            	JOptionPane.showMessageDialog(gameFrame, "The game has been aborted by another player.", "Error", JOptionPane.ERROR_MESSAGE);
            	applicationController.showGameSelection();
            	gameModel.setPlayers(null);
            	gameModel.setClientId(null);
            	gameFrame.reset();
            	break;
        }
    }

    private void checkGameUpdate(Command command)
    {
        switch (command.getGameUpdate())
        {
            case PLAYER_HAS_WON:
                Player winner = gameModel.findPlayerById(command.getAttackingClient());
                gameFrame.showWinnerDialog(winner.getUsername());
                break;
            case YOU_HAVE_WON:
                gameFrame.showWinnerDialog();
                break;
            case PLAYER_HAS_BEEN_DESTROYED:
                Player player = gameModel.findPlayerById(command.getAttackedClient());
                gameFrame.showDestroyedDialog(player.getUsername());
                break;
            case YOU_ARE_DESTROYED:
                gameFrame.showDestroyedDialog();
                break;
        }
    }

    private void playSound(AttackResult result)
    {
        if (result == AttackResult.HIT || result == AttackResult.SHIP_DESTROYED || result == AttackResult.PLAYER_DESTROYED)
        {
            soundEffects.explosion();
        }
        else if (result == AttackResult.NO_HIT)
        {
            soundEffects.splash();
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


    public void setGameModel(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }
}
