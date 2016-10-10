package game;

import game.Room.MovementDirection;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	
	
	public Square getSquare(Location location){
		Square square = null;
		square = this.grid[location.getY()][location.getX()];
		return square;
	}
	
	public String toString(){
		String string = new String();
		for (int i = 0; i < 10; i++){
			string += "\n";
			for (int j = 0; j < 10; j++){
				if (grid[i][j].getPlayer() != null){
					string += "P";
				}
				else{
					string += "X";
				}
			}
		}
		return string;
	}
	
	
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
