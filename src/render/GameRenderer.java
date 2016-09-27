package render;

import game.*;

/**
 * 
 * 
 * @author Brooke
 *
 */
public class GameRenderer {
	
	Game game;
	SpriteSet sprites;
	
	public GameRenderer(Game parent){
		game = parent;
		sprites = new SpriteSet();
	}

}
