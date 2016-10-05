package game;

import java.util.ArrayList;

import jdk.internal.org.objectweb.asm.util.CheckAnnotationAdapter;

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

	
	
}
