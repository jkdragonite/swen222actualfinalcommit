package game;

import game.Game.itemType;

public abstract class Item {
	private String nameString;
	private Location location;
	private itemType itemType;
	
	
	public Item(itemType type){
		this.itemType = type; 
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Location getLocation(){
		return this.location;
	}

	public itemType getType(){
		return this.itemType;
	}
	
	public void pickupItem(Player player){
		
	}
	
}
