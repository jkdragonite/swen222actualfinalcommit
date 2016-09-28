package game;

import java.util.ArrayList;

public class Game {
	private viewDirection view;
	
	public ArrayList<Player> players;
	
	
	public enum viewDirection {
		NORTH, SOUTH, EAST, WEST
	}
	
	public Game() {
		this.view = viewDirection.NORTH;		
	}
	
	public void shiftView(viewDirection direction){
		this.view = direction;
	}
	
	public void addPlayer(String nameString){
		this.players.add(new Player(nameString));
	}
	
	
	
	public void pickupItem(Player player, InventoryItem item){
		player.addItem(item);
		item.setOwner(player);
		// update location in room
		// check for proximity to item?
	}
	// create gui instance
	
	// create server?
	
	// create client?

}
