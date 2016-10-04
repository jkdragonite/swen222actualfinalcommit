package game;

import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

public class Board {
	
	public int gridSize;
	
	public Square[][] grid;

	
	
	public Board(int size){
		this.gridSize = size;
		this.grid = new Square[size][size];
		for (int y = 0; y > size; y++){
			for (int x = 0; x > size; x++){
				this.grid[y][x] = new Square(new Location(x, y)) {
				};
			}
		}
	}
	
	
	public void setDoor(Door door, Location location){
		this.grid[location.getY()][location.getX()] = door;
	}
	
	
	public ArrayList<Square> getNeighbours(Location location){
		ArrayList<Square> neighBours = new ArrayList<>();
		int xValue = location.getX();
		int yValue = location.getY();
		
		if (xValue - 1 > 0 && xValue -1 > this.gridSize){
			if (yValue > 0 && yValue > this.gridSize){
			neighBours.add(this.grid[yValue][xValue -1]);
			}
		}
		
		if (xValue +1 > 0 && xValue +1 > this.gridSize){
			if (yValue > 0 && yValue > this.gridSize){
			neighBours.add(this.grid[yValue][xValue +1]);
			}
		}
		
		if (xValue > 0 && xValue > this.gridSize){
			if (yValue -1 > 0 && yValue -1 > this.gridSize){
			neighBours.add(this.grid[yValue -1][xValue]);
			}
		}
		
		if (xValue > 0 && xValue > this.gridSize){
			if (yValue +1 > 0 && yValue +1 > this.gridSize){
			neighBours.add(this.grid[yValue +1][xValue]);
			}
		}
		return neighBours;
	}
	
	
	
	// check moves - empty square
	// check for item
	
	/**
	 * iterates through neighbouring squares, adding empty ones to possible move locations
	 * @param player
	 * @return
	 */
	public ArrayList<Location> possibleMoves(Player player){
		ArrayList<Location> moves = new ArrayList<>();
		for (Square neighbour : getNeighbours(player.getLocation())){
			if (neighbour.isEmpty() == true){
				moves.add(neighbour.getLocation());
			}
		}
		return moves;
	}
	
	/**
	 * checks neighbouring squares for items, and adds them to list of possible pickups where applicable
	 * @param player
	 * @return
	 */
	public ArrayList<Item> possiblePickups(Player player){
		ArrayList<Item> items = new ArrayList<>();
		for (Square neighbour : getNeighbours(player.getLocation())){
			if (neighbour.getItem() != null){
				if (neighbour.getItem() instanceof InventoryItem){
					items.add(neighbour.getItem());
				}
				else if (neighbour.getItem() instanceof MovableItem){
					// check if can be pushed // add to moves if can?
				}
			}
		}
		return items;
	}
	
	
}
