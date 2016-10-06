package game;

public abstract class Square {
	private Player playerOnSquare = null;
	private Item itemOnSquare = null;
	private Location location;
	
	public Square(Location location) {
		this.location = location;
	}
		
	public void addPlayer(Player player){
		this.playerOnSquare = player;
	}
	
	public void removePlayer(){
		this.playerOnSquare = null;
	}
	
	public void setItem(Item item){
		this.itemOnSquare = item;
	}
	
	public void removeItem(){
		this.itemOnSquare = null;
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
		if (this.playerOnSquare == null && this.itemOnSquare == null){
			return true;
		}
		else {
			return false;
		}
	}
	
}
