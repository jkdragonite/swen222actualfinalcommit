package game;

public abstract class Item {
	private String nameString;
	private Location location;
	
	
	public Item(String nameString){
		this.nameString = nameString;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}

}
