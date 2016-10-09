package game;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Room {
	// arraylist of players
	private Door door;
	protected ArrayList<Location> playerSpawnPoints; 
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
	 * places item at given grid location
	 * 
	 * 
	 * @param item
	 * @param location
	 */
	public void setMovableItem(MovableItem item, Location location){
		this.board.grid[location.getY()][location.getX()].setMovableItem(item);
	}
	
	public void setInventoryItem(InventoryItem item, Location location){
		this.board.grid[location.getY()][location.getX()].setInventory(item);
	}
	
	public void setImmovableItem(ImmovableItem item, Location location){
		this.board.grid[location.getY()][location.getX()].setImmovableItem(item);
		for (Location secondaryLocation : item.getLocationsCovered()){
			this.board.grid[secondaryLocation.getY()][secondaryLocation.getX()].setRenderFlag(false);	
		}
	}
	
	public void updatePlayerMoves(Player player){
		HashMap<MovementDirection, Square> neighbouringSquareHashMap = this.board.getNeighbours(player.getLocation());
//		System.out.println("Update player moves");
//		System.out.println(neighbouringSquareHashMap.size());
		for (MovementDirection direction : neighbouringSquareHashMap.keySet()){
			System.out.println("Begin loop");
			System.out.println(direction);
			System.out.println(neighbouringSquareHashMap.get(direction).isEmpty());
			if (neighbouringSquareHashMap.get(direction).isEmpty() == true){
				player.addToMovement(direction, neighbouringSquareHashMap.get(direction));
			}
			else if (neighbouringSquareHashMap.get(direction).isEmpty() == false){
				if (neighbouringSquareHashMap.get(direction).getMovableItem() != null){
					if (testPush(direction, neighbouringSquareHashMap.get(direction)) == true){
						player.addToPushMoves(direction, neighbouringSquareHashMap.get(direction));
					}
					
					if (testPull(direction, board.getSquare(player.getLocation())) == true){
						player.addToPullMoves(direction, neighbouringSquareHashMap.get(direction));
					}
				}
			}
			else if (neighbouringSquareHashMap.get(direction).getInventory() != null){
				if(player.getInventory().size() < 5){
					player.addToItemPickups(direction, neighbouringSquareHashMap.get(direction));
				}
			}
			
			else if (neighbouringSquareHashMap.get(direction).getContainer() != null){
				player.addToSearchMoves(direction, neighbouringSquareHashMap.get(direction));
			}
			else if (board.getSquare(player.getLocation()) instanceof Door){
				player.addToUseMoves(direction, board.getSquare(player.getLocation()));
			}
			else if (board.getSquare(player.getLocation()) instanceof Door){
				if (((Door) board.getSquare(player.getLocation())).isUnlocked() == true){
					player.canGoThroughDoor = true;
				}
			}
			else if (player.getInventory().size() > 0 && board.getSquare(player.getLocation()).getItem() == null){
				player.canDropItem = true;
			}
		}	
		System.out.println(player.moves);
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
	 * Adds all valid player items from player inventory to door 
	 * 
	 * @param player player executing move
	 * @param door door player is standing on/by (door extends square)
	 */
	public void useItem(Player player){
		for (InventoryItem item : player.getInventory()){
			this.door.testItem(player, item);
		}
		player.resetMoves();
		updatePlayerMoves(player);
	}
	
	
	
	/**
	 * 
	 * Drops the first item in the player's inventory
	 * 
	 * @param player
	 */
	public void dropItem(Player player){
		board.getSquare(player.getLocation()).setItem(player.getItem(0));
		player.removeItem(player.getItem(0));
	}
	
	
	/**
	 * Searches a container on a given square, and adds the items found to the given player's inventory
	 * 
	 * @param player executing move
	 * @param square holding container
	 */
	public void searchContainer(Player player, Square square){
		ArrayList<InventoryItem> containerItems = square.getContainer().getItems();
		for (InventoryItem item : containerItems){
			player.addItem(item);
			square.getContainer().removeItem(item);
		}
		player.resetMoves();
		updatePlayerMoves(player);
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
	
	public void goThroughDoor(Player player){
		player.updateRoom(this.door.getDestinationRoom());
	}
	
	public void pickupItem(Player player, Square square){
		player.addItem(square.getInventory());
		square.removeInventoryItem();
		player.resetMoves();
		updatePlayerMoves(player);
	}
	
	public Door getDoor(){
		return this.door;
	}
	
	public void addPSP(Location loc){
		playerSpawnPoints.add(loc);
	}

}
