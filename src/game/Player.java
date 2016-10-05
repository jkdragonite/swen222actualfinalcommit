package game;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<InventoryItem> playerInventory;
	private String nameString;
	private Location location;
	private char character;
	
	
	
	
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
	
	public Location getLocation(){
		return this.location;
	}
	
	public void updateLocation(Location location){
		this.location = location;
	}
	
	public void pushItem(){
		// get neighbouts
		// if neighbour has movable item
		// if square in push direction empty / not door / exists
		// can push
		// else cannot push
	}
	
	public void setCharacter(char newCharacter){
		this.character = newCharacter;
	}
	
	public char getCharacter(){
		return this.character;
	}
	

	
	
	
	// inventory
	
	// current view direction
	
	// location
	
}
