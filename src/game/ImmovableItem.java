package game;

public class ImmovableItem extends Item {
	private char character;
	
	public ImmovableItem(String nameString) {
		super(nameString);
		setCharacter();
	}
	
	public char getCharacter(){
		return this.character;
	}
	
	public void setCharacter(){
		this.character = 'I';
	}
	
}
