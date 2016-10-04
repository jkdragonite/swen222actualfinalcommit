package game;

import java.util.ArrayList;

public abstract class Room {
	public Board board;

	
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
	
//	/**
//	 * iterates through neighbouring squares, adding empty ones to possible move locations
//	 * @param player
//	 * @return
//	 */
//	public ArrayList<Location> possibleMoves(Player player){
//		ArrayList<Location> moves = new ArrayList<>();
//		for (Square neighbour : this.board.getNeighbours(player.getLocation())){
//			if (neighbour.isEmpty() == true){
//				moves.add(neighbour.getLocation());
//			}
//		}
//		return moves;
//	}
//	
//	/**
//	 * checks neighbouring squares for items, and adds them to list of possible pickups where applicable
//	 * @param player
//	 * @return
//	 */
//	public ArrayList<Item> possiblePickups(Player player){
//		ArrayList<Item> items = new ArrayList<>();
//		for (Square neighbour : this.board.getNeighbours(player.getLocation())){
//			if (neighbour.getItem() != null){
//				items.add(neighbour.getItem());
//			}
//		}
//		return items;
//	}
	

}
