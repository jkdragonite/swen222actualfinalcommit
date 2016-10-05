
package game;

import java.util.ArrayList;

public class Game {
	private viewDirection view;
	private renderRoom currentRoom;
	public ArrayList<Room> rooms = new ArrayList<Room>();

	
	public ArrayList<Player> players;
	
	
	/**
	 * indicates the players current view direction, i.e the
	 * perspective to render from
	 * 
	 * @author Jordan
	 *
	 */
	public enum viewDirection {
		NORTH, SOUTH, EAST, WEST
	}
	
	/**
	 * indicates the player's current room i.e the room to render
	 * 
	 * @author Jordan
	 *
	 */
	public enum renderRoom {
		ROOM1, ROOM2, ROOM3, ROOM4, FINALROOM
	}
	
	public Game() {
		this.view = viewDirection.NORTH;	
		this.currentRoom = renderRoom.ROOM1;
		PuzzleRoom gamePackage = new PuzzleRoom(10);
		// puzzle rooms added before final room
		this.rooms.add(gamePackage);
//		PuzzleRoom uiPackage = new PuzzleRoom(10);
//		PuzzleRoom networkPackage = new PuzzleRoom(10);
//		PuzzleRoom renderPackage = new PuzzleRoom(10);
		// add rooms with names? like game package etc
		this.rooms.add(new FinalRoom(10));
	}
	
	public void shiftView(viewDirection direction){
		this.view = direction;
	}
	
	public void changeRoom(renderRoom room){
		this.currentRoom = room;
	}
	
	public void addPlayer(String nameString){
		this.players.add(new Player(nameString));
//		this.players.get(players.size()-1).setCharacter(players.size());
//		method to set player character based on current number of players
		
	}
	
	// checked for unlocked door?
	
	public void pickupItem(Player player, InventoryItem item){
		player.addItem(item);
		item.setOwner(player);
		// update location in room
		// check for proximity to item?
	}
	
	public void createInventoryItems(){
		
		InventoryItem classDiagram = new InventoryItem("Class Diagram");
		InventoryItem sequenceDiagram = new InventoryItem("Sequence Diagram");
		InventoryItem useCaseDiagram = new InventoryItem("Use Case Diagram");
//		etc InventoryItem useCaseDiagram = new InventoryItem("Use Case Diagram");
		
		this.rooms.get(0).PlaceItem(classDiagram, new Location(4, 4));
		this.rooms.get(0).PlaceItem(sequenceDiagram, new Location(6, 6));
		
		// javadoc
		
		
		
		
	}

	public void createMovableItems(){
		
		MovableItem box1 = new MovableItem("box1");
		MovableItem box2 = new MovableItem("box2");
		
		this.rooms.get(0).PlaceItem(box1, new Location(2, 2));
		this.rooms.get(0).PlaceItem(box2, new Location(3, 3));
		
//		MovableItem box3 = new MovableItem("box3");
//		MovableItem box4 = new MovableItem("box4");
//		
//		this.rooms.get(0).PlaceItem(box2, new Location(3, 3));
//		this.rooms.get(0).PlaceItem(box2, new Location(3, 3));
		
		
		
	}
	
	public void createImmovableItems(){
		ImmovableItem stone = new ImmovableItem("stone");
		
	}
	

	/**
	 * retrives enum current view direction for use by the renderer
	 * 
	 * @return enum viewdirection
	 */
	public viewDirection getDirection(){
		return view;
	}
	
	/**
	 * returns current room the player is in for use by the renderer
	 * 
	 * @return enum renderRoom
	 */
	public renderRoom getRoom(){
		return currentRoom;
	}
}
