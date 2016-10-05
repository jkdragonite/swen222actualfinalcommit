package game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Room {
	// arraylist of players
	private Door door;
	
	public Board board;
	/**
	 * 
	 * Enum type covering all valid movement directions
	 * 
	 * @author Jordan
	 *
	 */
	public enum MovementDirection{
		UP, DOWN, LEFT, RIGHT
	}
	
	public Room(int size){
		this.board = new Board(size);
	}
	
	
	/**
	 * Places an instance of a player class at a given location
	 * 
	 * @param player
	 * @param location
	 */
	public void placePlayer(Player player, Location location){
		this.board.grid[location.getY()][location.getX()].addPlayer(player);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * Updates a board location to remove the current player
	 * 
	 * @param player
	 * @param location
	 */
	public void movePlayer(Player player, Location location){
		this.board.grid[player.getLocation().getY()][player.getLocation().getX()].removePlayer();
		this.board.grid[location.getY()][location.getX()].addPlayer(player);
		player.updateLocation(location);
	}
	
	
	/**
	 * 
	 * places item at given grid location
	 * 
	 * 
	 * @param item
	 * @param location
	 */
	public void PlaceItem(Item item, Location location){
		this.board.grid[location.getY()][location.getX()].setItem(item);
	}
	
	
	
	// check moves - empty square
	// check for item
	
	
//	
//	public ArrayList<MovementDirection> freeMoves(Player player){
//		ArrayList<MovementDirection> moves = new ArrayList<MovementDirection>();
//		for (Square square : board.getNeighbours(player.getLocation())){
//			if (square.isEmpty() == true){
//		}
//		return null;
//		
//	}
	
	
	
	
	/**
	 * iterates through neighbouring squares, adding empty ones to possible move locations
	 * @param player
	 * @return
	 */
	public ArrayList<Location> possibleMoves(Player player){
		ArrayList<Location> moves = new ArrayList<>();
		for (Square neighbour : this.board.getNeighbours(player.getLocation())){
			if (neighbour.isEmpty() == true){
				moves.add(neighbour.getLocation());
			}
		}
		return moves;
	}
	
	
	// method to test moves
	
	public void updatePlayerMoves(Player player){
		HashMap<MovementDirection, Square> neighbouringSquareHashMap = this.board.getNeighbours2(player.getLocation());
		// iterate through this hashmap, 
		// check each square
		// if empty, add to player class move squares,
		// if item, add to possible pickups
		// if movable item, test if item can move, store push + push direction?
		// if chest item, add search
		//
		//
		//checking for empty squares, if empty add to hashmap of empty location
		// update this in player class
		
		// when player takes an action
		
		
		// hashmap of neighbours
		
		// check hashmap for empty squares
		
		// check hashmap for pickup items
		
		// check hashmap for movable items
		
		
		
		
	}
	
	// update moves in player class?
	
	// move player, which clear player's available moves
	
	
	/**
	 * checks neighbouring squares for items, and adds them to list of possible pickups where applicable
	 * @param player
	 * @return
	 */
	public ArrayList<Item> possiblePickups(Player player){
		ArrayList<Item> items = new ArrayList<>();
		for (Square neighbour : this.board.getNeighbours(player.getLocation())){
			if (neighbour.getItem() != null){
				items.add(neighbour.getItem());
			}
		}
		return items;
	}
	
	
	public Door getDoor(){
		return this.door;
	}

}
