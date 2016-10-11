package game;

import java.util.ArrayList;

/** 
 * @author Jordan Ching - 300394044
 * 
 * Puzzle room, the rooms in which players interact with objects and move through / unlock doors
 *
 */
public class PuzzleRoom extends Room {
	
//	private Door door;
	public ArrayList<InventoryItem> keyItems =  new ArrayList<InventoryItem>();
	public ArrayList<MovableItem> movableItems = new ArrayList<MovableItem>();
	
	public PuzzleRoom(int size){
		super(size);
		playerSpawnPoints = new ArrayList<Location>();
	}
		
	
//	public Door getDoor(){
//		return this.door;
//	}
	
}
