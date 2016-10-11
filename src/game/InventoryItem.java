package game;

import game.Game.itemType;

/**
 *Represents an item which is able to be picked up/dropped,
 *and has additional methods to facilitate this. 
 * 
 * @author Jordan 300394044
 *
 */
public class InventoryItem extends Item{
	private Player owner = null;
	private String itemName;
	
	
	public InventoryItem(itemType type, Location loc, String name, int uoid){
		super(type, 'Q', loc, uoid);
		this.itemName = name;
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
	
	public void removeOwner(){
		this.owner = null;
//		this.location = location;
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public Player getOwner(){
		return this.owner;
	}
	
	/**
	 * Adds an item to the players inventory and updates the 
	 * items owner flag
	 * @param player
	 */
	public void pickupItem(Player player){
		setOwner(player);
		player.addItem(this);
		
		// display picked up item sprite?
	}
	
	/**
	 * Returns whether this item has an owning player or not.
	 * @return
	 */
	public boolean hasOwner(){
		return owner != null;
	}
	
	/**
	 * Gets the unique player id of this items owner. Should only be called
	 * after the item has had hasOwner() called on it and is confirmed not null.
	 * @return unique player ID of owning player
	 */
	public int getOwnerID(){
		return owner.getPlayerNumber();
	}
	
}
