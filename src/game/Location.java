package game;

public class Location {
	private int xcoordinate;
	private int ycoordinate;
	
	public Location(int x, int y){
		this.xcoordinate = x;
		this.ycoordinate = y;
	}
	
	
	public void setXCoordinate(int x){
		this.xcoordinate = x;
	}
	
	public void setYCoordinate(int y){
		this.ycoordinate = y;
	}
	
	public int getX(){
		return this.xcoordinate;
	}
	
	public int getY(){
		return this.ycoordinate;
	}

}
