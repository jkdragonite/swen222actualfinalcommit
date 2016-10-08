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
	
	
	public Door getDoor(){
		return this.door;
	}
	
}
