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
	
	
	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 
//	 * Updates a board location to remove the current player
//	 * 
//	 * @param player
//	 * @param location
//	 */
//	public void movePlayer(Player player, Location location){
//		this.board.grid[player.getLocation().getY()][player.getLocation().getX()].removePlayer();
//		this.board.grid[location.getY()][location.getX()].addPlayer(player);
//		player.updateLocation(location);
//	}
	
	
	/**
	 * 
	 * places item at given grid location
	 * 
	 * 
	 * @param item
	 * @param location
	 */
	public void PlaceItem(Item item, Location location){
		// instanceof for particular items?
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
	
	
	// method to test moves
	
	public void updatePlayerMoves(Player player){
		HashMap<MovementDirection, Square> neighbouringSquareHashMap = this.board.getNeighbours(player.getLocation());
		// method to resetmoves?
		
		for (MovementDirection direction : neighbouringSquareHashMap.keySet()){
			if (neighbouringSquareHashMap.get(direction).isEmpty() == true){
				player.addToMovement(direction, neighbouringSquareHashMap.get(direction));
			}
			else if (neighbouringSquareHashMap.get(direction).isEmpty() == false){
				if (neighbouringSquareHashMap.get(direction).getMovableItem() != null){
					if (testPush(direction, neighbouringSquareHashMap.get(direction)) == true){
						player.addToPushMoves(direction, neighbouringSquareHashMap.get(direction));
					}
					
					if (testPull(direction, board.getSquare(player.getLocation())) == true){
						player.addToPushMoves(direction, neighbouringSquareHashMap.get(direction));
						// finish this pull move method
					}
				}
			}
			
			else if (neighbouringSquareHashMap.get(direction).getInventory() != null){
				player.addToItemPickups(direction, neighbouringSquareHashMap.get(direction));
			}
			
			else if (neighbouringSquareHashMap.get(direction).getContainer() != null){
				player.addToSearchMoves(direction, neighbouringSquareHashMap.get(direction));
			}
			
		}	
				
		// when player takes an action
		
		
		// hashmap of neighbours
		
		// check hashmap for empty squares
		
		// check hashmap for pickup items
		
		// check hashmap for movable items		
				
	}
	
	
	public boolean testPush(MovementDirection direction, Square square){
		boolean push = false;
		HashMap<MovementDirection, Square> boxNeighbours = board.getNeighbours(square.getLocation());
		if (boxNeighbours.get(direction).isEmpty() == true) {
			push = true;
		}
		return push;
	}
	
	
	public boolean testPull(MovementDirection direction, Square square){
		boolean pull = false;
		HashMap<MovementDirection, Square> boxNeighbours = board.getNeighbours(square.getLocation());
		
		if (direction == MovementDirection.UP){
			if (boxNeighbours.get(MovementDirection.DOWN).isEmpty() == true) {
				pull = true;
			}
		}
		if (direction == MovementDirection.DOWN){
			if (boxNeighbours.get(MovementDirection.UP).isEmpty() == true) {
				pull = true;
			}
		}
		if (direction == MovementDirection.LEFT){
			if (boxNeighbours.get(MovementDirection.RIGHT).isEmpty() == true) {
				pull = true;
			}
		}
		if (direction == MovementDirection.RIGHT){
			if (boxNeighbours.get(MovementDirection.LEFT).isEmpty() == true) {
				pull = true;
			}
		}	
		return pull;
	}
	
	
	
	/**
	 * Takes a player and a direction updates their location and the board's square's location to reflect a
	 * move in this direction 
	 * 
	 * @param player
	 * @param direction
	 */
	public void MovePlayer(Player player, MovementDirection direction){
		Location currentLocation = player.getLocation();
		if (direction == MovementDirection.UP){
			Location newLocation = new Location(currentLocation.getX(), currentLocation.getY()-1);
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
		}
		if (direction == MovementDirection.DOWN){
			Location newLocation = new Location(currentLocation.getX(), currentLocation.getY()+1);
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
		}
		if (direction == MovementDirection.LEFT){
			Location newLocation = new Location(currentLocation.getX()-1, currentLocation.getY());
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
		}
		if (direction == MovementDirection.RIGHT){
			Location newLocation = new Location(currentLocation.getX()+1, currentLocation.getY());
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
		}
		
		player.resetMoves();
		updatePlayerMoves(player);
	}
	
	
	/**
	 * Takes a square containing a movable item, as well as a direction
	 * and moves said item to another square based on the direction given
	 * 
	 * @param direction
	 * @param square
	 */
	public void pushItem(Player player, MovementDirection direction, Square square){
		if (direction == MovementDirection.UP){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY-1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.UP);
		}
		if (direction == MovementDirection.DOWN){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY+1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.DOWN);
			
		}
		if (direction == MovementDirection.LEFT){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY][squareX-1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.LEFT);
		}
		if (direction == MovementDirection.RIGHT){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY][squareX+1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.RIGHT);
		}
		
		
	
	}
	
	/**
	 * Takes a square and a pull direction, moves player and box based on these two factors
	 * 
	 * @param direction
	 * @param square
	 */
	public void pullItem(Player player, MovementDirection direction, Square square){
		if (direction == MovementDirection.UP){
			MovePlayer(player, MovementDirection.DOWN);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY+1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
		}
		
		if (direction == MovementDirection.DOWN){
			MovePlayer(player, MovementDirection.UP);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY-1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();

		}
		
		if (direction == MovementDirection.LEFT){
			MovePlayer(player, MovementDirection.RIGHT);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY][squareX+1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();		
		}
		
		if (direction == MovementDirection.RIGHT){
			MovePlayer(player, MovementDirection.LEFT);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			this.board.grid[squareY][squareX-1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();		
		}
	}
	
	
	/**
	 * checks neighbouring squares for items, and adds them to list of possible pickups where applicable
	 * @param player
	 * @return
	 */
//	public ArrayList<Item> possiblePickups(Player player){
//		ArrayList<Item> items = new ArrayList<>();
//		for (Square neighbour : this.board.getNeighbours(player.getLocation())){
//			if (neighbour.getItem() != null){
//				items.add(neighbour.getItem());
//			}
//		}
//		return items;
//	}
	
	
	public Door getDoor(){
		return this.door;
	}

}
