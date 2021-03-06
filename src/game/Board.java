package game;

import game.Room.MovementDirection;

import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

/**
 * @author Jordan Ching 300394044
 * Holds squares, used by room class
 *
 */
public class Board {
	
	public int gridSize;
	
	public Square[][] grid;

	
	
	/**
	 * Fills a 2d array of squares for the board class with the desidered number of squares
	 * 
	 * @param size
	 */
	public Board(int size){
		this.gridSize = size;
//		System.out.println(size);
		this.grid = new Square[size][size];
		for (int y = 0; y < size; y++){
			for (int x = 0; x < size; x++){
//				System.out.println(y);
//				System.out.println(x);
				this.grid[y][x] = new PlayableSquare(new Location(x, y));
			}
		}
	}
	
	
	/**
	 * Sets a door square in the given location.
	 * 
	 * @param door
	 * @param location
	 */
	public void setDoor(Door door, Location location){
		this.grid[location.getY()][location.getX()] = door;
	}
	
	
	/**
	 * Returns the desired square from the boards grid.
	 * 
	 * @param location
	 * @return square
	 */
	public Square getSquare(Location location){
		Square square = null;
		square = this.grid[location.getY()][location.getX()];
		return square;
	}
	
	/**
	 * Creates a string representation of the board for testing
	 * purposes.
	 */
	public String toString(){
		String string = new String();
		for (int i = 0; i < 10; i++){
			string += "\n";
			for (int j = 0; j < 10; j++){
				if (grid[i][j].getPlayer() != null){
					string += "P";
				}
				else if (grid[i][j].getMovableItem() != null){
					string += "B";
				}
				else if (grid[i][j].getImmovableItem() != null){
					string += "H";
				}
				else if (grid[i][j] instanceof Door){
					string += "D";
				}
				else{
					string += "X";
				}
			}
		}
		return string;
	}
	
	/**
	 * Get the neighbour squares for a given square, storing them in a hashmap
	 * that links the neighbour to the direction desired to get there from the 
	 * input square.
	 * 
	 * @param location
	 * @return HashMap of neighbours.
	 */
	public HashMap<MovementDirection, Square> getNeighbours(Location location){
		HashMap<MovementDirection, Square> neighBours = new HashMap<MovementDirection, Square>();
		int xValue = location.getX();
		int yValue = location.getY();
		
		if (xValue - 1 > -1 && xValue -1 < this.gridSize){
//			System.out.println("Left");
			if (yValue > -1 && yValue < this.gridSize){
				neighBours.put(MovementDirection.LEFT, this.grid[yValue][xValue -1]);
			}
		}
		
		if (xValue +1 > -1 && xValue +1 < this.gridSize){
//			System.out.println("Right");
			if (yValue > -1 && yValue < this.gridSize){
				neighBours.put(MovementDirection.RIGHT, this.grid[yValue][xValue +1]);
			}
		}
		
		if (xValue > -1 && xValue < this.gridSize){
//			System.out.println("DOWN");
			if (yValue +1 > -1 && yValue +1 < this.gridSize){
				neighBours.put(MovementDirection.DOWN, this.grid[yValue +1][xValue]);
			}
		}
		
		if (xValue > -1 && xValue < this.gridSize){
//			System.out.println("UP");
			if (yValue -1 > -1 && yValue -1 < this.gridSize){
				neighBours.put(MovementDirection.UP, this.grid[yValue -1][xValue]);
			}
		}
		return neighBours;
	}
}
