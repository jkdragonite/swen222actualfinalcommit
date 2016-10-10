package game;

import game.Game.viewDirection;
import game.Room.MovementDirection;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	
	private ArrayList<InventoryItem> playerInventory;
	private Location location;
	private int playerNumber;
	private Room currentRoom;
	private viewDirection view;
	
	public HashMap<MovementDirection, Square> moves = new HashMap<MovementDirection, Square>();
	// Based on key press, up = UP etc, if in keyset, this is a valid move
	
	public HashMap<MovementDirection, Square> pushMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid push
	
	public HashMap<MovementDirection, Square> pullMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid pull
	
	public HashMap<MovementDirection, Square> itemPickups = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid item pickup
	
	
	public HashMap<MovementDirection, Square> searchMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid search
	
	
	public HashMap<MovementDirection, Square> useMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid use item
	
	public Boolean canGoThroughDoor = false;
	// if this is true, player can go through door they are standing on
	
	public Boolean canDropItem = false;
	
	public Player(int number, Room room){
		this.view = Game.viewDirection.NORTH;
		this.playerInventory = new ArrayList<InventoryItem>();
		this.location = new Location(0, 0);
		this.playerNumber = number;
		this.currentRoom = room;
	}
	
	public void shiftView(viewDirection direction){
		this.view = direction;
	}
	
	public Room getRoom(){
		return this.currentRoom;
	}
	
	public void updateRoom(Room room){
		this.currentRoom = room;
	}
	
	public void addItem(InventoryItem item){
		this.playerInventory.add(item);	
	}
	
	public ArrayList<InventoryItem> getInventory(){
		return this.playerInventory;
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
	
	public void setPlayerNumber(int newCharacter){
		this.playerNumber = newCharacter;
	}
	
	public int getCharacter(){
		return this.playerNumber;
	}
	
	public int getPlayerNumber(){
		return this.playerNumber;
	}
	
	public void addToMovement(MovementDirection direction, Square square){
		this.moves.put(direction, square);
		System.out.println(moves);
	}
	
	public void addToPushMoves(MovementDirection direction, Square square){
		this.pushMoves.put(direction, square);
	}
	
	public void addToPullMoves(MovementDirection direction, Square square){
		this.pullMoves.put(direction, square);
	}
	
	public void addToItemPickups(MovementDirection direction, Square square){
		this.itemPickups.put(direction, square);
	}
	
	public void addToSearchMoves(MovementDirection direction, Square square){
		this.searchMoves.put(direction, square);
	}
	
	public void addToUseMoves(MovementDirection direction, Square square){
		this.useMoves.put(direction, square);
	}
	
	public void setCanDropItem(Boolean valueBoolean){
		this.canDropItem = valueBoolean;
	}
	
	public void resetMoves(){
		
		this.moves = new HashMap<MovementDirection, Square>();
		this.pushMoves = new HashMap<MovementDirection, Square>();
		this.pullMoves = new HashMap<MovementDirection, Square>();
		this.itemPickups = new HashMap<MovementDirection, Square>();
		this.searchMoves = new HashMap<MovementDirection, Square>();
		this.canGoThroughDoor = false;
		this.canDropItem = false;
	}
	
	public InventoryItem getItem(int index){
		return this.playerInventory.get(index);
	}
}
