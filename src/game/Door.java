package game;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Door extends Square{
	
	private Room destinationRoom;
	private Boolean unlocked;
	private char character = 'D';
	
	public Door(Location location) {
		super(location);
		this.unlocked = true;
	}

	private ArrayList<InventoryItem> solution = new ArrayList<InventoryItem>();
	
	private ArrayList<InventoryItem> keyHole = new ArrayList<InventoryItem>();
	
	public void testItem(Player player, InventoryItem item){
		if (solution.contains(item)){
			keyHole.add(item);
			if (solution.size() == keyHole.size()){
				unlocked = true;
				this.character = 'd';
			}
		}		
	}
	
	/**
	 * Transitions player through door 
	 * 
	 * @param player
	 */
	public void goThroughDoor(Player player){
		player.updateRoom(this.destinationRoom);
	}
	
	public char getCharacter(){
		return this.character;
	}

	/**
	 * Adds item to solution which unlocks door
	 * 
	 * @param item
	 */
	public void addToSolution(InventoryItem item){
		this.solution.add(item);
	}
	
	
	public void setDestination(Room room){
		this.destinationRoom = room;
	}
	
	public ArrayList<InventoryItem> getKeyHole(){
		return this.keyHole;
	}
	
	public ArrayList<InventoryItem> getSolution(){
		return this.solution;
	}
	
	public Boolean isUnlocked(){
		return this.unlocked;
	}
	
	public void setUnlocked(Boolean unlocked){
		this.unlocked = unlocked;
	}
	
	public Room getDestinationRoom() {
		return destinationRoom;
	}
	
}
