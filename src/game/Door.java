package game;

import java.util.ArrayList;

public class Door extends Square{
	private Room destinationRoom;
	private Location location;
	
	public Door(Location location, Room destination) {
		super(location);
		this.destinationRoom = destination;
	}

	private ArrayList<Item> solution;
	
	private ArrayList<Item> keyHole;
	
	public void testItem(Player player, InventoryItem item){
		if (solution.contains(item)){
			keyHole.add(item);
			player.removeItem(item);
		}
	}

	
	
}
