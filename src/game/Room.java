package game;

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

}
