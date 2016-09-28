package game;

public class PuzzleRoom extends Room {
	
	private Door door;
	
	public PuzzleRoom(int size){
		super(size);
	}
	
	/**
	 * 
	 * replaces a square with an instance of a door
	 * 
	 * @param door
	 */
	public void addDoor(Door door){
		this.door = door;
		Location doorLocation = door.getLocation();
		this.board.grid[doorLocation.getY()][doorLocation.getX()] = door;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param item
	 * @param location 
	 */
	public void addItem(Item item, Location location){
		item.setLocation(location);
		this.board.grid[location.getY()][location.getX()].setItem(item);
	}
	
}
