package game;

import java.util.ArrayList;

public class Game {
	private viewDirection view;
	public ArrayList<Room> rooms;
	
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
	
}
