package game;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<InventoryItem> playerInventory;
	private String nameString;
	private Location location;
	
	
	
	
	public Player(String nameString){
		this.nameString = nameString;
		this.playerInventory = new ArrayList<InventoryItem>();
		this.location = new Location(0, 0);

	}
	
	public void addItem(InventoryItem item){
		this.playerInventory.add(item);
		
	}
	
	public void removeItem(InventoryItem item){
		playerInventory.remove(item);
	}
	
	
	// inventory
	
	// current view direction
	
	// location
	
}
