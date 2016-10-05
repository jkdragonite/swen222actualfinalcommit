package game;

public class ImmovableItem extends Item {
	private char character;
	
	public ImmovableItem(String nameString) {
		super(nameString);
		this.character = 'I';
	}
	
	public char getCharacter(){
		return this.character;
	}
	
}
