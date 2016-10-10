package game;

import java.io.DataOutputStream;
import java.io.IOException;

import game.Game.itemType;

public abstract class Item {
	private char character;
	protected Location location;
	private itemType itemType;
	
	
	public Item(itemType type, char character, Location loc){
		this.character = character;
		this.itemType = type; 
		this.location = loc;
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
	 * Basic data consists of it's unique character and x/y 
	 * location co-ordinates.
	 * @param dout
	 */
	public void toOutputStream(DataOutputStream dout) throws IOException{
		dout.writeChar(character);
		dout.writeInt(location.getX());
		dout.writeInt(location.getY());
	}
}
