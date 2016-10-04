package game;

import java.util.ArrayList;

public class Game {
	private viewDirection view;
	public ArrayList<Room> rooms = new ArrayList<Room>();

	
	public ArrayList<Player> players;
	
	
	public enum viewDirection {
		NORTH, SOUTH, EAST, WEST
	}
	
	public Game() {
		this.view = viewDirection.NORTH;	
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
	
	public void addPlayer(String nameString){
		this.players.add(new Player(nameString));	
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
	
	

	public viewDirection getDirection(){
		return view;
	}
	
}
