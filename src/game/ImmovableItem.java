package game;

import java.util.ArrayList;

public class ImmovableItem extends Item {
	private final char character = 'I';
	// array of locations
	private ArrayList<Location> locationsCovered;
	
	public ImmovableItem(game.Game.itemType type, Location leftmost) {
		super(type);
		setLocation(leftmost);
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public ArrayList<Location> getLocationsCovered(){
		return this.locationsCovered;
	}
	
	public void addToLocationsCovered(Location location){
		locationsCovered.add(location);
	}
	
}
