package game;

import java.util.ArrayList;

public class Container extends ImmovableItem{
	private final char character = 'C';
	private ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	
	public Container(game.Game.itemType type, Location loc, int uoid) {
		super(type, loc, uoid);
	}

	public char getCharacter(){
		return this.character;
	}
	
	public void addItem(InventoryItem item){
		this.items.add(item);
	}
	
	public void removeItem(Item item){
		this.items.remove(item);
	}
	
	public ArrayList<InventoryItem> getItems(){
		return items;
	}

	public boolean hasItems(){
		return !(items.isEmpty());
	}
}
