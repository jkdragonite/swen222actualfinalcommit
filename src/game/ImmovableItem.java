package game;

import java.util.ArrayList;

public class ImmovableItem extends Item {
	// array of locations
	private ArrayList<Location> locationsCovered;
	
	public ImmovableItem(game.Game.itemType type, Location leftmost) {
		super(type, 'I', leftmost);
	}
	
	public ArrayList<Location> getLocationsCovered(){
		return this.locationsCovered;
	}
	
	public void addToLocationsCovered(Location location){
		locationsCovered.add(location);
	}
	
}
