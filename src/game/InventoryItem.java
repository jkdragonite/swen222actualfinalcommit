package game;

public class InventoryItem extends Item{
	private String nameString;
	private Player owner = null;
	private Location location;
	
	
	public InventoryItem(String nameString){
		super(nameString);
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
}
