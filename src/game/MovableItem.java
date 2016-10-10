package game;

import game.Game.itemType;

public class MovableItem extends Item {
	private final char character = 'M';
	
	public MovableItem(itemType type, Location loc) {
		super(type);
		setLocation(loc);
	}

	public char getCharacter(){
		return this.character;
	}
}
