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
	
	public Boolean equals(int x, int y){
		if (x == this.xcoordinate && y == this.ycoordinate){
			return true;
		}
		return false;
	}
	
	public String toString(){
		String string = new String();
		string += "X Coordinate " + this.xcoordinate;
		string += "\nY Coordinate " + this.ycoordinate;
		return string;
	}

}
