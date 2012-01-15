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

import org.springframework.beans.factory.InitializingBean;

import com.jostrobin.battleships.ApplicationController;
import com.jostrobin.battleships.common.data.GameData;
import com.jostrobin.battleships.common.data.GameMode;
import com.jostrobin.battleships.view.frames.CreateGameFrame;
import com.jostrobin.battleships.view.listeners.EventListener;

/**
 * @author rowyss
 *         Date: 04.12.11 Time: 10:31
 */
public class CreateGameController implements InitializingBean
{
    private CreateGameFrame frame;

    private ApplicationController applicationController;

    private void createGame(GameMode mode)
    {
    	int numberOfPlayers = frame.getNumberOfPlayersAllowed();
    	int size = frame.getSelectedFieldSize();
        GameData game = new GameData(null, mode, numberOfPlayers, size, size);
        
        // add the ships allowed in the game
        game.setNrOfAircraftCarriers(frame.getNumberOfAircraftCarriers());
        game.setNrOfBattleships(frame.getNumberOfBattleships());
        game.setNrOfDestroyers(frame.getNumberOfDestroyers());
        game.setNrOfPatrolBoats(frame.getNumberOfPatrolBoats());
        game.setNrOfSubmarines(frame.getNumberOfSubmarines());
        	
        applicationController.createGame(game);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        frame.addCancelListener(new CancelListener());
        frame.addCreateGameListener(new CreateGameListener());
    }

    private void cancel()
    {
        applicationController.showGameSelection();
        applicationController.cancelGame();
    }

    public ApplicationController getApplicationController()
    {
        return applicationController;
    }

    public void setApplicationController(ApplicationController applicationController)
    {
        this.applicationController = applicationController;
    }

    public CreateGameFrame getFrame()
    {
        return frame;
    }

    public void setFrame(CreateGameFrame frame)
    {
        this.frame = frame;
    }

    private class CancelListener implements EventListener<Object>
    {

        @Override
        public void actionPerformed(Object value)
        {
            cancel();
        }
    }

    private class CreateGameListener implements EventListener<GameMode>
    {
        @Override
        public void actionPerformed(GameMode mode)
        {
        	createGame(mode);
        }
    }
}
