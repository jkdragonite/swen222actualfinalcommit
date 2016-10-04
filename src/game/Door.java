package game;

import java.util.ArrayList;

public class Door extends Square{
	private Room destinationRoom;
	private Location location;
	private Boolean unlocked;
	// reward item?
	
	public Door(Location location, Room destination) {
		super(location);
		this.destinationRoom = destination;
		this.unlocked = false;
	}

	private ArrayList<Item> solution;
	
	private ArrayList<Item> keyHole;
	
	
	// method for add to solution
	
	
	
	public void testItem(Player player, InventoryItem item){
		if (solution.contains(item)){
			keyHole.add(item);
			player.removeItem(item);
			if (solution.size() == keyHole.size()){
				unlocked = true;
			}
		}
		
		// render item didn't fit, or item fit sprite
		
	}

	
	
}
