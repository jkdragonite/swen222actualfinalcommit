package game;

import java.util.ArrayList;

public class ImmovableItem extends Item {
	private char character;
	// array of locations
	private ArrayList<Location> locationsCovered;
	
	public ImmovableItem(game.Game.itemType type) {
		super(type);
		setCharacter();
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public void setCharacter(){
		this.character = 'I';
	}
	
	public ArrayList<Location> getLocations(){
		return this.locationsCovered;
	}
	
	public void addToLocations(Location location){
		locationsCovered.add(location);
	}
	
}
