package game;

import game.Game.itemType;

public class InventoryItem extends Item{
	private Player owner = null;
	private Location location;
	private String itemName;
	private final char character = 'Q';
	
	
	public InventoryItem(itemType type, String name){
		super(type);
		this.itemName = name;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
		
	/**
	 * An item which can be added to a player's inventory, an owner can be set so that
	 * the item can remain in a room's list of items, 
	 * @param player
	 */
	public void setOwner(Player player){
		this.owner = player;
		this.location = null;
	}
	
	public void removeOwner(Location location){
		this.owner = null;
		this.location = location;
	}
	
	public String getItemName(){
		return itemName;
	}
	
	@Override
	public void pickupItem(Player player){
		setOwner(player);
		player.addItem(this);
		
		// display picked up item sprite?
	}
	
	public char getCharacter(){
		return this.character;
	}
	
}
