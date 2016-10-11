package game;

/**
 * @author Jordan Ching - 300394044
 * 
 * Squares which hold players and items
 *
 */
public abstract class Square {
	private Player playerOnSquare = null;
	private InventoryItem inventoryItem = null;
	private MovableItem movableItem = null;
	private ImmovableItem immovableItem = null;
	private Container container = null;
	private Boolean renderFlag = true;
	private Location location;
	
	/**
	 * Creates a square with given location
	 * @param location
	 */
	public Square(Location location) {
		this.location = location;
	}
	
	/**
	 * retrieves inventory item on square 
	 * @return inventoryitem
	 */
	public InventoryItem getInventory() {
		return inventoryItem;
	}

	
	/**
	 * Sets inventory item on square 
	 * @param inventory
	 */
	public void setInventory(InventoryItem inventory) {
		this.inventoryItem = inventory;
	}

	public MovableItem getMovableItem() {
		return movableItem;
	}

	public void setMovableItem(MovableItem movableItem) {
		this.movableItem = movableItem;
	}
	
	public void removeMovableItem(){
		this.movableItem = null;
	}

	public ImmovableItem getImmovableItem() {
		return immovableItem;
	}

	public void setImmovableItem(ImmovableItem immovableItem) {
		this.immovableItem = immovableItem;
	}

	
	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

		
	public void addPlayer(Player player){
		this.playerOnSquare = player;
	}
	
	
	public void removePlayer(){
		this.playerOnSquare = null;
	}
	

	
	public void setItem(InventoryItem item){
		this.inventoryItem = item;
	}
	
	
	public void removeInventoryItem(){
		this.inventoryItem = null;
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public Item getItem(){
		if ( this.inventoryItem != null){
			return inventoryItem;
		}
		if ( this.movableItem != null){
			return movableItem;
		}
		if ( this.immovableItem != null){
			return immovableItem;
		}
		if ( this.container != null){
			return container;
		}
		return null;
	}
	
	public Player getPlayer(){
		return this.playerOnSquare;
	}
	
	public void setRenderFlag(Boolean valueBoolean){
		this.renderFlag = valueBoolean;
	}
	
	public void resetRenderFlag(){
		this.renderFlag = true;
	}
	
	public Boolean getRenderFlag(){
		return this.renderFlag;
	}
	
	public Boolean isEmpty(){
		if (this.playerOnSquare == null && this.movableItem == null && this.immovableItem == null 
				&& this.container == null && renderFlag == true){
			return true;
		}
		else {
			return false;
		}
	}
	

	
}
