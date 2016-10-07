package game;

public abstract class Square {
	private Player playerOnSquare = null;
	private Item itemOnSquare = null;
	private InventoryItem inventory = null;
	private MovableItem movableItem = null;
	private ImmovableItem immovableItem = null;
	private Container container = null;


	private Item item = null;
	private Location location;
	
	public Square(Location location) {
		this.location = location;
	}
	
	public InventoryItem getInventory() {
		return inventory;
	}

	public void setInventory(InventoryItem inventory) {
		this.inventory = inventory;
	}

	public MovableItem getMovableItem() {
		return movableItem;
	}

	public void setMovableItem(MovableItem movableItem) {
		this.movableItem = movableItem;
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
	

	
	public void setItem(Item item){
		this.item = item;
	}
	
	
	public void removeItem(){
		this.itemOnSquare = null;
		// update to change movable / immovable items
	}
	
	public Location getLocation(){
		return this.location;
	}
	
	public Item getItem(){
		return this.itemOnSquare;
	}
	
	public Player getPlayer(){
		return this.playerOnSquare;
	}
	
	public Boolean isEmpty(){
		if (this.playerOnSquare == null && this.movableItem == null && this.immovableItem == null){
			return true;
		}
		else {
			return false;
		}
	}
	
}
