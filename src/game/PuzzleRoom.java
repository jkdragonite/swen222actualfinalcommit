package game;

public class PuzzleRoom extends Room {
	private Door door;
	private Board board;
	
	public PuzzleRoom(int size){
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
