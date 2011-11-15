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

package com.jostrobin.battleships.common.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rowyss
 *         Date: 13.11.11 Time: 22:07
 */
public class Observable<T>
{
    List<Observer<T>> observers = new ArrayList<Observer<T>>();

    public void notifyObservers(T value)
    {
        for (Observer<T> observer : observers)
        {
            observer.update(value);
        }
    }

    public void addObserver(Observer<T> observer)
    {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer)
    {
        observers.remove(observer);
    }
}
