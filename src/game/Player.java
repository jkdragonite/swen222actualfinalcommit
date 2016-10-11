package game;

import game.Game.viewDirection;
import game.Room.MovementDirection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jordan Ching - 300394044
 * CLass representing a player
 *
 */
public class Player {
	
	private ArrayList<InventoryItem> playerInventory;
	private Location location;
	private int playerNumber;
	private Room currentRoom;
	private viewDirection view;
	
	/**
	 * Possible movement moves
	 */
	public HashMap<MovementDirection, Square> moves = new HashMap<MovementDirection, Square>();
	// Based on key press, up = UP etc, if in keyset, this is a valid move
	
	/**
	 * Possible push moves
	 */
	public HashMap<MovementDirection, Square> pushMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid push
	
	/**
	 * Possible pullmoves
	 */
	public HashMap<MovementDirection, Square> pullMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid pull
	
	/**
	 * Possible itempickups
	 */
	public HashMap<MovementDirection, Square> itemPickups = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid item pickup
	
	
	/**
	 * possible searchmoves
	 */
	public HashMap<MovementDirection, Square> searchMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid search
	
	
	/**
	 * possible usemoves
	 */
	public HashMap<MovementDirection, Square> useMoves = new HashMap<MovementDirection, Square>();
	// if keyset length not 0, there is a valid use item
	
	/**
	 * can go through
	 */
	public Boolean canGoThroughDoor = false;
	// if this is true, player can go through door they are standing on
	
	/**
	 * candropitem
	 */
	public Boolean canDropItem = false;
	
	/**
	 * player takes a unique id and a room
	 * 
	 * @param number
	 * @param room
	 */
	public Player(int number, Room room){
		this.view = Game.viewDirection.NORTH;
		this.playerInventory = new ArrayList<InventoryItem>();
		this.location = new Location(0, 0);
		this.playerNumber = number;
		this.currentRoom = room;
	}
	
	/**
	 * 
	 * shifts player's viewdireciton
	 * @param direction
	 */
	public void shiftView(viewDirection direction){
		this.view = direction;
	}
	
	/**
	 * returns viewdirection
	 * @return viewdireciton
	 */
	public viewDirection getView(){
//		System.out.println(view);
		return this.view;
	}
	
	/**
	 * resets player inventory, used when they go to new room
	 */
	public void wipeInventory(){
		this.playerInventory = new ArrayList<InventoryItem>();
	}
	
	/**
	 * gets payer's current room
	 * @return
	 */
	public Room getRoom(){
		return this.currentRoom;
	}
	
	/**
	 * changes player current room
	 * @param room
	 */
	public void updateRoom(Room room){
		this.currentRoom = room;
	}
	
	/**
	 * adds item to player inventory
	 * @param item
	 */
	public void addItem(InventoryItem item){
		this.playerInventory.add(item);
		item.setOwner(this);
	}
	
	/**
	 * retrive list fo items in inventory
	 * @return
	 */
	public ArrayList<InventoryItem> getInventory(){
		return this.playerInventory;
	}
	
	public void removeItem(InventoryItem item){
		playerInventory.remove(item);
		item.removeOwner();
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
	
	/**
	 * adds squares to player moves
	 * @param direction
	 * @param square
	 */
	public void addToMovement(MovementDirection direction, Square square){
		this.moves.put(direction, square);
//		System.out.println(moves);
	}
	
	/**
	 * adds to pushmove hashmap
	 * @param direction
	 * @param square
	 */
	public void addToPushMoves(MovementDirection direction, Square square){
		this.pushMoves.put(direction, square);
	}
	
	/**
	 * add to pull move hashmap
	 * @param direction
	 * @param square
	 */
	public void addToPullMoves(MovementDirection direction, Square square){
		this.pullMoves.put(direction, square);
	}
	
	/**
	 * add to item pickup hashmap
	 * @param direction
	 * @param square
	 */
	public void addToItemPickups(MovementDirection direction, Square square){
		this.itemPickups.put(direction, square);
	}
	
	/**
	 * add tosearch move hashmap
	 * @param direction
	 * @param square
	 */
	public void addToSearchMoves(MovementDirection direction, Square square){
		this.searchMoves.put(direction, square);
	}
	
	/**
	 * add to use move hashmap
	 * @param direction
	 * @param square
	 */
	public void addToUseMoves(MovementDirection direction, Square square){
		this.useMoves.put(direction, square);
	}
	
	/**
	 * set candrop boolean
	 * @param valueBoolean
	 */
	public void setCanDropItem(Boolean valueBoolean){
		this.canDropItem = valueBoolean;
	}
	
	/**
	 * reset player moves hashmaps
	 */
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
