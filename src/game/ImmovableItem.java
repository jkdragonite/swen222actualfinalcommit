package game;

import java.util.ArrayList;
/**
 * Represents an Immovable item, which may cover more than
 * one space on a board and stores the additional information
 * pertaining to that. 
 * 
 * @author Jordan 300394044
 *
 */
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
