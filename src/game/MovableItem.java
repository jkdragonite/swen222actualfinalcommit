package game;

import game.Game.itemType;

public class MovableItem extends Item {
	
	public MovableItem(itemType type, Location loc) {
		super(type, 'M', loc);
	}
}
