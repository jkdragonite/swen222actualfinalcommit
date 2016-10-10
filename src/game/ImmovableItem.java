package game;

import java.util.ArrayList;

public class ImmovableItem extends Item {
	// array of locations
	private ArrayList<Location> locationsCovered = new ArrayList<Location>();
	
	public ImmovableItem(game.Game.itemType type, Location leftmost, int uoid) {
		super(type, 'I', leftmost, uoid);
	}
	
	public ArrayList<Location> getLocationsCovered(){
		return this.locationsCovered;
	}
	
	public void addToLocationsCovered(Location location){
		locationsCovered.add(location);
	}
	
}
