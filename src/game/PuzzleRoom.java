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
	
}
