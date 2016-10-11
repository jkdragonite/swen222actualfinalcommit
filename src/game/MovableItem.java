package game;

import game.Game.itemType;

/**
 * Represents an item which is able to be pushed or pulled
 * in the game. Commonly given type box. 
 * 
 * @author Jordan 300394044
 *
 */
public class MovableItem extends Item {
	
	public MovableItem(itemType type, Location loc, int uoid) {
		super(type, 'M', loc, uoid);
	}
}
