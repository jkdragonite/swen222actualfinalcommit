package game;

public class InventoryItem extends Item{
	private String nameString;
	private Player owner = null;
	private Location location;
	private char character;
	
	
	public InventoryItem(String nameString){
		super(nameString);
		this.character = 'P';
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
	
	
	@Override
	public void pickupItem(Player player){
		setOwner(player);
		player.addItem(this);
		
		// display picked up item sprite?
	}
	
	public void setCharacter(char character){
		this.character = character;
	}
	
	public char getCharacter(){
		return this.character;
	}
	
}
