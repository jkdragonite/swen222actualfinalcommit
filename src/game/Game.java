
package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
	private viewDirection view;
//	private renderRoom currentRoom;
	public ArrayList<Room> rooms = new ArrayList<Room>();
	public HashMap<Integer, itemType> itemCodes = new HashMap<>();

	
	public ArrayList<Player> players = new ArrayList<Player>();
	
	
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
	
	
	/**
	 * 
	 * enum type for items
	 * 
	 * @author Jordan
	 *
	 */
	public enum itemType {
		BOX, BOOKSHELF, BOOK, BED, CHAIR,
		COMPUTER, DARKNESS, DESK, KEY, TABLE
	}
	
	
	public Game() {
		this.view = viewDirection.NORTH;	
//		this.currentRoom = renderRoom.ROOM1;
		initializeItemsCodes();
	}
	
	public void initializeItemsCodes(){
		this.itemCodes.put(610, itemType.BOX);
		this.itemCodes.put(611, itemType.BOOKSHELF);
		this.itemCodes.put(612, itemType.BOOK);
		this.itemCodes.put(614, itemType.BED);
		this.itemCodes.put(615, itemType.CHAIR);
		this.itemCodes.put(616, itemType.COMPUTER);
		this.itemCodes.put(617, itemType.DARKNESS);
		this.itemCodes.put(618, itemType.DESK);
		this.itemCodes.put(619, itemType.KEY);
		this.itemCodes.put(620, itemType.TABLE);
		
		
	}
	
	public void shiftView(viewDirection direction){
		this.view = direction;
	}
	
//	public void changeRoom(renderRoom room){
//		this.currentRoom = room;
//	}
	
	public void addInventoryItemToGame(InventoryItem item, int roomNumber){
		this.rooms.get(roomNumber).setInventoryItem(item, item.getLocation());
	}
	
	public void addMovableItemToGame(MovableItem item, int roomNumber){
		this.rooms.get(roomNumber).setMovableItem(item, item.getLocation());
	}
	
	public void addImmovableItemToGame(ImmovableItem item, int roomNumber){
		this.rooms.get(roomNumber).setImmovableItem(item, item.getLocation());
	}
	
	public void addPlayer(String nameString){
		int currentSize = players.size();
		this.players.add(new Player(currentSize, this.rooms.get(0)));
		
	}
		

	/**
	 * retrieves enum current view direction for use by the renderer
	 * 
	 * @return enum viewdirection
	 */
	public viewDirection getDirection(){
		return view;
	}
	
//	/**
//	 * returns current room the player is in for use by the renderer
//	 * 
//	 * @return enum renderRoom
//	 */
//	public renderRoom getRoom(){
//		return currentRoom;
//	}
	
	/**
	 * returns a list of room's doors
	 * 
	 * @return arraylist door
	 */
	public ArrayList<Door> getDoors(){
		ArrayList<Door> doors = new ArrayList<Door>();
		for (Room room : this.rooms){
			doors.add(room.getDoor());
		}
		return doors;
	}
	
	/**
	 * returns a list of rooms the players are currently in
	 * @return arraylist rooms
	 */
	public ArrayList<Room> occupiedRooms(){
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (Player player : this.players){
			rooms.add(player.getRoom());
		}
		return rooms;
	}
	
	public void setDestinationRooms(){
		int count = 1;
		for (Room room : this.rooms){
			if (room instanceof PuzzleRoom){
				room.getDoor().setDestination(this.rooms.get(count));	
			}
			count++;
		}
	}
	
}
