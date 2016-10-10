package game;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import game.Game.itemType;

public abstract class Item {
	protected char character;
	protected Location location;
	private itemType itemType;
	private int uoid;
	
	private Map<Game.itemType, Integer> types;
	
	public Item(itemType type, char character, Location loc, int uoid){
		this.character = character;
		this.itemType = type; 
		this.location = loc;
		this.uoid = uoid;
		initializeItemCodes();
	}
	
	public int getUoid(){
		return this.uoid;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public Location getLocation(){
		return this.location;
	}

	public itemType getType(){
		return this.itemType;
	}
	
	public char getCharacter(){
		return character;
	}
	
	/**
	 * Writes basic item information to a given output stream.
	 * Basic data consists of it's unique character, unique identifier
	 * and x/y location co-ordinates.
	 * @param dout
	 */
	public void toOutputStream(DataOutputStream dout) throws IOException{
		dout.writeChar(character);
		dout.writeInt(uoid);
		dout.writeInt(location.getX());
		dout.writeInt(location.getY());
		dout.writeInt(getTypeInt(itemType));
	}
	
	private int getTypeInt(Game.itemType type){
		return types.get(type);
	}
	
	private void initializeItemCodes(){
		types = new HashMap<Game.itemType, Integer>();
		this.types.put(itemType.BOX, 610);
		this.types.put(itemType.BOOKSHELF, 611);
		this.types.put(itemType.BOOK, 612);
		this.types.put(itemType.BED, 614);
		this.types.put(itemType.CHAIR, 615);
		this.types.put(itemType.COMPUTER, 616);
		this.types.put(itemType.DARKNESS, 617);
		this.types.put(itemType.DESK, 618);
		this.types.put(itemType.KEY, 619);
		this.types.put(itemType.TABLE, 620);	
	}
}
