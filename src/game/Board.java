package game;

import java.util.ArrayList;

public class Board {
	
	
	public Square[][] grid;

	
	
	public Board(int size){
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
	
	
}
