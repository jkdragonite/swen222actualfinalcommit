package game;

import java.util.ArrayList;

public class ImmovableItem extends Item {
	private char character;
	// array of locations
	private ArrayList<Location> locationsCovered;
	
	public ImmovableItem(String nameString) {
		super(nameString);
		setCharacter();
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public void setCharacter(){
		this.character = 'I';
	}
	
}
