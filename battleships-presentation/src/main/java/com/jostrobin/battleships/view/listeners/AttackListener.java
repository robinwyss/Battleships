package com.jostrobin.battleships.view.listeners;

/**
 * Used to propagate attack events to the controller.
 * @author joscht
 *
 */
public interface AttackListener
{
	public void attack(int x, int y, Long clientId);
}
