package render;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.*;


/**
 * 
 * 
 * @author Brooke 300321819
 *
 */
public class GameRenderer {

	//public enum Direction {NORTH, SOUTH, EAST, WEST, TOP}
	Game.viewDirection viewDir = Game.viewDirection.SOUTH;
	JFrame frame;
	Graphics gra;
	Square[][] stage;
	Game game;
	int x, y;

	public static final int floor = 450;

	public GameRenderer(Game parent){

		game = parent;
		Room room = new PuzzleRoom(6);
		Board board = room.board;
		stage = board.grid;

		//render(gra);
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

		//render the wall

		//render the tiles
		for (int y = 0; y < stage.length; x++){
			for (int x = 0; x < stage.length; x++){
				if (stage[x][y].getItem() != null){
					renderObject(LEFT + (SIZE*y), BASE);
				}
			}
		}

	}
	
	public void renderObject(int x, int y){
		//note: x and y are the bottom left things.
		
	}

	public static void main(String[] args){
		new GameRenderer(new Game());
		System.out.println("bleh.");
	}
}
