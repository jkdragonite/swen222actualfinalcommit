package game;

public abstract class Square {
	private Player playerOnSquare;
	private Item itemOnSquare;
	
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
	
	
}
