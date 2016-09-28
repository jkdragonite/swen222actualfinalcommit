package game;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<InventoryItem> playerInventory;
	private String nameString;
	private Location location;
	
	
	
	
	public Player(String nameString){
		// add location?
		
		this.nameString = nameString;
		this.playerInventory = new ArrayList<InventoryItem>();

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
