package game;

import java.util.ArrayList;

public class Container extends ImmovableItem{
	private char character;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Container(game.Game.itemType type, Location loc) {
		super(type);
		setLocation(loc);
	}
	
	@Override
	public void setCharacter(){
		this.character = 'C';
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public void removeItem(Item item){
		this.items.remove(item);
	}
	
	public ArrayList<Item> getItem(){
		return items;
	}

	public boolean hasItem(){
		return !(items.isEmpty());
	}
}
