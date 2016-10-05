package game;

import java.util.ArrayList;

public class PuzzleRoom extends Room {
	
	private Door door;
	public ArrayList<InventoryItem> keyItems =  new ArrayList<InventoryItem>();
	public ArrayList<MovableItem> movableItems = new ArrayList<MovableItem>();
	
	public PuzzleRoom(int size){
		super(size);
		// add field to constuctor which takes a list of items, to add to room
	}
	
	/**
	 * 
	 * replaces a square with an instance of a door
	 * 
	 * @param door
	 */
	public void addDoor(Door door){
		this.door = door;
		Location doorLocation = door.getLocation();
		this.board.grid[doorLocation.getY()][doorLocation.getX()] = door;
	}
	
	/**
	 * 
	 * Adds an item at a given location to a puzzle room
	 * 
	 * @param item
	 * @param location 
	 */
	public void addItem(Item item, Location location){
		item.setLocation(location);
		this.board.grid[location.getY()][location.getX()].setItem(item);
	}
	
	
	public Door getDoor(){
		return this.door;
	}
	
}
