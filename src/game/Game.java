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
		// puzzle rooms added before final room
		this.rooms.add(new PuzzleRoom(10));
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

	public viewDirection getDirection(){
		return view;
	}
	
	//movePlayer(int dir)
	//gets the next direction in the player queue and follows it (pickup, move up, drop)
	
	
	//gameToBytes
	//converts the game state to a byte array consisting of chars and ints in a specific 
	//sequence so that it can be sent to a client
	//returns a byte array?
	//there are many stages to the update as there are many objects.
	//each stage is preceded by a specific character and followed directly by the number of 
	//spaces in the byte array that belong to it (allowing it to be split)
	//
	//firstly we update doors 'd', followed by their room square co-ordinate and either a 0 or 1
	//		the 0-1 denotes lock state
	//e.g. locked door at 0,1 and an unlocked door at 3,0 = 'd010301'
	
	//we secondly update containers with storage, sending an 's'., followed by their x,y location
	//and either a 0 or 1, to denote whether the container is empty or not.
	//If 1, char 'i' is sent followed by a number denoting which object it is (as defined in a 
	//level file)
	
	//we then update movable container locations by sending 'm' followed by a container's old x/y
	//and then their new x/y
	
	//we then update pickupables/dropped items preceeded by 'p'. Each subsequent item is preceeded
	//by either a 0 or 1, which is whether it was picked up or dropped. This is followed by either
	//the x/y co-ordinates of the player, (or the player involved's uid?) and the x/y of where
	//te=he object either was or is now. 
	
	//update player
	//room, inventory, etc.
	
}
