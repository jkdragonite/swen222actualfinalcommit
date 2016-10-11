package game;

import game.Game.itemType;
import game.Game.viewDirection;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Room {
	// arraylist of players
	public Door door;
	protected ArrayList<Location> playerSpawnPoints; 
	
	public ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
	public ArrayList<MovableItem> movableItems = new ArrayList<MovableItem>();
	public ArrayList<ImmovableItem> immovableItems = new ArrayList<ImmovableItem>();
	public ArrayList<Container> containers = new ArrayList<Container>();
	public HashMap<Integer, Item> itemsHashMap = new HashMap<Integer, Item>();
	public ArrayList<Player> playersInRoom = new ArrayList<Player>();
	
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
	
	public Item getItemOnSquare(Location location){
		Item returnedItem = board.getSquare(location).getItem();
		return returnedItem;
	}
	
	public Player getPlayerOnSquare(Location location){
		Player player = board.getSquare(location).getPlayer();
		return player;
	}
	
	
	public void addPlayer(Player player){
		this.playersInRoom.add(player);
	}
	
	public void removePlayer(Player player){
		this.playersInRoom.remove(player);
	}
	
	/**
	 * Places an instance of a player class at a given location
	 * 
	 * @param player
	 * @param location
	 */
	public void placePlayer(Player player, Location location){
		player.updateLocation(location);
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
//		System.out.println(this.door);
//		System.out.println(getDoor());
//		System.out.println(this.door);
//		System.out.println(door.getCharacter());
//		System.out.println(door.getLocation());
		Location doorLocation = door.getLocation();
//		System.out.println(door.getLocation().toString());
		this.board.grid[doorLocation.getY()][doorLocation.getX()] = door;
	}

	
	public Door getDoor(){
		return this.door;
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
	
	/**
	 * 
	 * Uses a player's location to generate a hashmap of neighbouring squares and their direction, iterates through 
	 * this hashmap to check if the player can carry out any of the possible actions. Adds possible moves to hashmaps
	 * within the player class for use by the ui and networking packages.
	 * 
	 * @param player
	 */
	public void updatePlayerMoves(Player player){
		HashMap<MovementDirection, Square> neighbouringSquareHashMap = this.board.getNeighbours(player.getLocation());
//		System.out.println("Update player moves");
//		System.out.println(neighbouringSquareHashMap.size());
		for (MovementDirection direction : neighbouringSquareHashMap.keySet()){
//			System.out.println("Begin loop");
//			System.out.println(direction);
//			System.out.println(neighbouringSquareHashMap.get(direction).getLocation().toString());
			if (neighbouringSquareHashMap.get(direction).isEmpty() == true){
				player.addToMovement(direction, neighbouringSquareHashMap.get(direction));
			}
			if (neighbouringSquareHashMap.get(direction).isEmpty() == false){
				if (neighbouringSquareHashMap.get(direction).getMovableItem() != null){
					if (testPush(direction, neighbouringSquareHashMap.get(direction)) == true){
						player.addToPushMoves(direction, neighbouringSquareHashMap.get(direction));
					}
					
					if (testPull(direction, board.getSquare(player.getLocation())) == true){
						player.addToPullMoves(direction, neighbouringSquareHashMap.get(direction));
					}
				}
			}
			
		
//			System.out.println(neighbouringSquareHashMap.get(direction).getInventory());
			
			if (neighbouringSquareHashMap.get(direction).getInventory() != null){
				System.out.println("Item here");
				if(player.getInventory().size() < 5){
					player.addToItemPickups(direction, neighbouringSquareHashMap.get(direction));
				}
			}
			if (board.getSquare(player.getLocation()).getInventory() != null){
				if(player.getInventory().size() < 5){
					player.addToItemPickups(null, board.getSquare(player.getLocation()));
				}
			}
			
			if (neighbouringSquareHashMap.get(direction).getContainer() != null){
				player.addToSearchMoves(direction, neighbouringSquareHashMap.get(direction));
			}
			
			if (board.getSquare(player.getLocation()) instanceof Door){
				player.addToUseMoves(direction, board.getSquare(player.getLocation()));
			}
			if (board.getSquare(player.getLocation()) instanceof Door){
				if (((Door) board.getSquare(player.getLocation())).isUnlocked() == true){
					player.canGoThroughDoor = true;
				}
			}
			if (player.getInventory().size() > 0 && board.getSquare(player.getLocation()).getInventory() == null){
				player.canDropItem = true;
			}
		}	
		System.out.println(player.moves);
	}
	
	
	/**
	 * 
	 * Takes a direction and a square, returns true if the next square across in the given
	 * direction is empty (i.e a box can be pushed to it)
	 * 
	 * @param direction
	 * @param square
	 * @return boolean 
	 */
	public boolean testPush(MovementDirection direction, Square square){
		boolean push = false;
		
		HashMap<MovementDirection, Square> boxNeighbours = board.getNeighbours(square.getLocation());
		if (boxNeighbours.containsKey(direction)){
			if(boxNeighbours.get(direction).isEmpty() == true) {
				push = true;
			}
		}
		return push;
	}
	
	
	public MovementDirection getOppositeDirection(MovementDirection currentDirection){
		MovementDirection view = null;
		if (currentDirection == MovementDirection.UP){
			view = MovementDirection.DOWN;
		}
		else if (currentDirection == MovementDirection.DOWN){
			view = MovementDirection.UP;
		}
		else if (currentDirection == MovementDirection.LEFT){
			view = MovementDirection.RIGHT;
		}
		else if (currentDirection == MovementDirection.RIGHT){
			view = MovementDirection.LEFT;
		}
		
		return view;
	}
	
	public Square getSecondSquare(MovementDirection direction, Square square){
		Square returnSquare = null;
		HashMap<MovementDirection, Square> boxNeighbours = board.getNeighbours(square.getLocation());
		if (boxNeighbours.containsKey(direction)){
			returnSquare = boxNeighbours.get(direction);
		}
		return returnSquare;
	}
	
	/**
	 * 
	 * Takes a square and a direction, checks the squares in the opposite direction
	 * and returns true if a box can be pushed there
	 * 
	 * 
	 * @param direction
	 * @param square
	 * @return boolean
	 */
	public boolean testPull(MovementDirection direction, Square square){
		boolean pull = false;
		HashMap<MovementDirection, Square> boxNeighbours = board.getNeighbours(square.getLocation());
		
		if (boxNeighbours.containsKey(direction) && boxNeighbours.containsKey(getOppositeDirection(direction))){
			if (direction == MovementDirection.UP){
				if (getSecondSquare(MovementDirection.DOWN, boxNeighbours.get(MovementDirection.DOWN)) != null
						&& getSecondSquare(MovementDirection.DOWN, boxNeighbours.get(MovementDirection.DOWN)).isEmpty() == true) {
						pull = true;
				}
			}

			if (direction == MovementDirection.DOWN){
				if (getSecondSquare(MovementDirection.UP, boxNeighbours.get(MovementDirection.UP)) != null
						&& getSecondSquare(MovementDirection.UP, boxNeighbours.get(MovementDirection.UP)).isEmpty() == true) {
						pull = true;
				}
			}
			if (direction == MovementDirection.LEFT){
				if (getSecondSquare(MovementDirection.RIGHT, boxNeighbours.get(MovementDirection.RIGHT)) != null
						&& getSecondSquare(MovementDirection.RIGHT, boxNeighbours.get(MovementDirection.RIGHT)).isEmpty() == true) {
						pull = true;
				}
			}
			if (direction == MovementDirection.RIGHT){
				if (getSecondSquare(MovementDirection.LEFT, boxNeighbours.get(MovementDirection.LEFT)) != null
						&& getSecondSquare(MovementDirection.LEFT, boxNeighbours.get(MovementDirection.LEFT)).isEmpty() == true) {
						pull = true;
				}
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
		System.out.println("Player inventory" + player.getInventory());
		for (InventoryItem item : player.getInventory()){
			System.out.println("item test "+item);
			this.door.testItem(player, item);
		}
//		for (InventoryItem item : door.getSolution()){
//			if (player.getInventory().contains(item)){
//				player.removeItem(item);
//			}
//		}
		player.resetMoves();
		updatePlayerMoves(player);
	}
	
	
	
	/**
	 * 
	 * Drops the first item in the player's inventory
	 * 
	 * @param player
	 */
	public void dropItem(Player player,int idexToRemove){
		if (board.getSquare(player.getLocation()).getInventory() == null){
			InventoryItem itemToRemove = player.getItem(idexToRemove);
			board.getSquare(player.getLocation()).setInventory(itemToRemove);
			itemToRemove.removeOwner();
			itemToRemove.setLocation(player.getLocation());
			player.removeItem(itemToRemove);
			System.out.println("dlgkjshdfhgll  "+board.getSquare(player.getLocation()).getInventory());
			updatePlayerMoves(player);
		}

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
			System.out.println(player.getLocation().toString());
			System.out.println(newLocation.toString());
			player.updateLocation(newLocation);
			System.out.println(this.board.toString());
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
			System.out.println(this.board.toString());
		}
		if (direction == MovementDirection.DOWN){
			Location newLocation = new Location(currentLocation.getX(), currentLocation.getY()+1);	
			System.out.println(player.getLocation().toString());
			System.out.println(newLocation.toString());
			System.out.println(this.board.toString());
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
			System.out.println(this.board.toString());
		}
		if (direction == MovementDirection.LEFT){
			Location newLocation = new Location(currentLocation.getX()-1, currentLocation.getY());	
			System.out.println(player.getLocation().toString());
			System.out.println(newLocation.toString());
			System.out.println(this.board.toString());
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
			System.out.println(this.board.toString());
		}
		if (direction == MovementDirection.RIGHT){
			Location newLocation = new Location(currentLocation.getX()+1, currentLocation.getY());	
			System.out.println(player.getLocation().toString());
			System.out.println(newLocation.toString());
			System.out.println(this.board.toString());
			player.updateLocation(newLocation);
			board.getSquare(currentLocation).removePlayer();
			board.getSquare(newLocation).addPlayer(player);
			System.out.println(this.board.toString());
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
			Location newLocation = new Location(squareX, squareY-1);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY-1][squareX].setMovableItem(square.getMovableItem());
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.UP);
		}
		if (direction == MovementDirection.DOWN){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX, squareY+1);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY+1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.DOWN);
			
		}
		if (direction == MovementDirection.LEFT){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX-1, squareY);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY][squareX-1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			MovePlayer(player, MovementDirection.LEFT);
		}
		if (direction == MovementDirection.RIGHT){
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX+1, squareY);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
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
			Location newLocation = new Location(squareX, squareY+1);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY+1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			updatePlayerMoves(player);
		}
		
		if (direction == MovementDirection.DOWN){
			MovePlayer(player, MovementDirection.UP);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX, squareY-1);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY-1][squareX].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			updatePlayerMoves(player);

		}
		
		if (direction == MovementDirection.LEFT){
			MovePlayer(player, MovementDirection.RIGHT);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX+1, squareY);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY][squareX+1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();		
			player.resetMoves();
			updatePlayerMoves(player);
		}
		
		if (direction == MovementDirection.RIGHT){
			MovePlayer(player, MovementDirection.LEFT);
			int squareX = square.getLocation().getX();
			int squareY = square.getLocation().getY();
			Location newLocation = new Location(squareX-1, squareY);
			this.board.grid[squareY][squareX].getMovableItem().setLocation(newLocation);
			this.board.grid[squareY][squareX-1].setMovableItem(square.getMovableItem());;
			board.getSquare(square.getLocation()).removeMovableItem();
			player.resetMoves();
			updatePlayerMoves(player);
		}
	}
	
	/**
	 * Transitions player to new room
	 * 
	 * @param player
	 */
	public void goThroughDoor(Player player){
		player.updateRoom(this.door.getDestinationRoom());
		Room newRoom = this.door.getDestinationRoom();
		if (newRoom instanceof PuzzleRoom){
			this.door.getDestinationRoom().addPlayer(player);
			this.door.getDestinationRoom().placePlayer(player, new Location(0, 9));
			removePlayer(player);
			player.updateRoom(this.door.getDestinationRoom());
			System.out.println("skdhgfjkshadg "+player.getRoom());
			player.resetMoves();
			updatePlayerMoves(player);
			player.wipeInventory();
		}
		else {
			this.door.getDestinationRoom().addPlayer(player);
			this.door.getDestinationRoom().placePlayer(player, new Location(0, 9));
			removePlayer(player);
			player.updateRoom(this.door.getDestinationRoom());
			System.out.println("askhfkjshdg "+player.getRoom());
			player.resetMoves();
			updatePlayerMoves(player);
			player.wipeInventory();
		}
	}
	
	
	/**
	 * Takes a square with an item on it, adds this to player inventory and then updates the given
	 * square to reflect its absence on the board
	 * 
	 * @param player
	 * @param square
	 */
	public void pickupItem(Player player, Square square){
		square.getInventory().setOwner(player);
		player.addItem(square.getInventory());
		square.removeInventoryItem();
		player.resetMoves();
		updatePlayerMoves(player);
	}
	

	
	public void unlockDoor(){
		this.door.setUnlocked(true);
	}
	
	public void addPSP(Location loc){
		playerSpawnPoints.add(loc);
	}

}
