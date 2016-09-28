package render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.*;


/**
 * 
 * 
 * @author Brooke 300321819
 *
 */
public class GameRenderer{

	//public enum Direction {NORTH, SOUTH, EAST, WEST, TOP}
	Game.viewDirection viewDir = Game.viewDirection.SOUTH;
	JFrame frame;
	Graphics gra;
	Square[][] stage;
	SpriteSet spriteSet = new SpriteSet();
	List<Image> sprites = (List<Image>) spriteSet.getSprites();
	Game game;
	int x, y;

	public static final int floor = 450;

	public GameRenderer(Game parent){
		
		game = parent;
		Room room = new PuzzleRoom(6);
		Board board = room.board;
		stage = board.grid;

		
	}

	public void rotateCW() {
		Square[][] mat = stage;
		final int M = mat.length;
		final int N = mat[0].length;
		Square[][] ret = new Square[N][M];
		for (int r = 0; r < M; r++) {
			for (int c = 0; c < N; c++) {
				ret[c][M-1-r] = mat[r][c];
			}
		}
		this.x = N;
		this.y = M;
		this.stage = ret;

		switch(viewDir){
		case NORTH:
			viewDir = Game.viewDirection.EAST;
			break;
		case EAST: 
			viewDir = Game.viewDirection.SOUTH;
			break;
		case SOUTH:
			viewDir = Game.viewDirection.WEST;
			break;
		case WEST:
			viewDir = Game.viewDirection.NORTH;
			break;
		default:
			break;
		}

	}
	
	public void paint(Graphics g){
		System.out.println("painting...");
		render(g);
	}
	

	//coordinates of the floor
	private static final int BASE = 100;
	private static final int LEFT = 100;
	private static final int SIZE = 100;

	/**
	 * This handles the actual process of rendering the scene.
	 * @param g
	 */
	public void render(Graphics g){		
		Game.viewDirection newDir = game.getDirection();

		//get our new direction
		while (viewDir != newDir){
			rotateCW();
		}

		//render the floor
		g.fillRect(LEFT, BASE, SIZE*10, 3);

		renderObject(LEFT, BASE, g);
		//render the wall

		//render the tiles
		//for (int y = 0; y < stage.length; x++){
			//for (int x = 0; x < stage.length; x++){
				//if (stage[x][y].getItem() != null){
					//renderObject(LEFT + (SIZE*y), BASE, g);
				//}
				//System.out.println("tile");
			//}
		//}

	}
	
	/**
	 * Render object windows - in this case all objects are boxes.
	 * @param x
	 * @param y
	 * @param g
	 */
	public void renderObject(int x, int y, Graphics g){
		//note: x and y are the bottom left things.
		Image img = sprites.get(0);
		System.out.println("box get");
		g.drawImage(img, x, y-100, null);
	}

	public static void main(String[] args){
		new GameRenderer(new Game());
		System.out.println("bleh.");
	}
}
