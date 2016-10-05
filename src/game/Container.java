package game;

public class Container extends ImmovableItem{
	private char character;
	
	public Container(String nameString) {
		super(nameString);		
	}
	
	@Override
	public void setCharacter(){
		this.character = 'C';
	}
	
	public char getCharacter(){
		return this.character;
	}

}
