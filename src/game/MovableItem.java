package game;

import game.Game.itemType;

public class MovableItem extends Item {
	
	public MovableItem(itemType type, Location loc, int uoid) {
		super(type, 'M', loc, uoid);
	}
}
