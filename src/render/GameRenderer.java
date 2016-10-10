package render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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

		game = new Game();
		Room room = game.rooms.get(0);
		Board board = room.board;
		stage = board.grid;

		if(board.grid[0][0] == null){
			System.out.println("null squares");
		}

		if (stage[0][0] == null){
			System.out.println("null titles");
		}


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
	private static final int BASE = 375;
	private static final int LEFT = 100;
	private static final int SIZE = 100;
	private static final int VERT_DISP = 8;
	private static final int HORZ_DISP = 20;
	private static final double SCALE_FAC = 0.04;



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
		int topLeft = (int)(LEFT + ((stage.length-1)*HORZ_DISP));
		int topWall = (int)(BASE - 150 - (stage.length*VERT_DISP));
		int topRight = LEFT + 820;
		int right = LEFT + 1000;

		//render the wall
		g.setColor(Color.GRAY);
		g.fillRect(topLeft, topWall-60, 640, 150);

		//left wall
		g.setColor(Color.lightGray);
		int[] leftX = {LEFT, LEFT, topLeft, topLeft};
		int[] leftY = {BASE, topWall-140, topWall-60, BASE};

		//right wall
		int[] rightX = {right, right, topRight, topRight};
		int[] rightY = {BASE, topWall-140, topWall-60, BASE};
		g.fillPolygon(leftX, leftY, 4);
		g.fillPolygon(rightX, rightY, 4);
		
		//render the floor
		g.setColor(Color.darkGray);
		g.fillRect(LEFT, BASE, SIZE*10, 3);
		int floorX[] = {LEFT, topLeft, topRight, right};
		int floorY[] = {BASE, topWall+90, topWall+90, BASE};
		g.fillPolygon(floorX, floorY, 4);

		//render the tiles
		for (int y = stage.length -1; y >= 0; y--){
			for (int x = 0; x < stage.length; x++){
				Image img = getImage(stage[x][y]);

				if (img != null){
					//scale the image
					int scaleX = (int)(img.getWidth(null)*(1-(y*SCALE_FAC)));
					int scaleY = (int)(img.getHeight(null)*(1-(y*SCALE_FAC)));
					Image newImg = getScaledImage(img, scaleX, scaleY);
					int imgX = (LEFT+y*(HORZ_DISP) + x*scaleX);
					int imgY = (BASE - y*VERT_DISP)+100 - img.getHeight(null);
					this.renderObject(newImg, imgX, imgY, g);
				}
			}
		}
	}

	public Image getImage(Square square){
		int dir = 0;
		//get an item on a square by creating the code to retrieve from
		//the spriteset hashmap
		switch(viewDir){
		case NORTH:
			dir = 0;
			break;
		case EAST:
			dir = 1;
			break;
		case SOUTH:
			dir = 2;
			break;
		case WEST:
			dir = 3;
			break;
		default:
			System.out.println("Invalid Direction");
			throw new RuntimeException();
		}

		//get the other half of the code
		if (square.getItem() != null){
			switch(square.getItem().getType()){
			case BOX:
				return spriteSet.getSprite("0a");
			case BOOKSHELF:
				return spriteSet.getSprite(dir + "4");
			case BOOK:
				return spriteSet.getSprite(dir + "c");
			case BED:
				return spriteSet.getSprite(dir + "8");
			case CHAIR:
				return spriteSet.getSprite(dir + "6");
			case COMPUTER:
				return spriteSet.getSprite(dir + "9");
			case DESK:
				return spriteSet.getSprite(dir + "5");
			case KEY:
				return spriteSet.getSprite(dir + "b");
			case TABLE:
				return spriteSet.getSprite(dir + "7");
			default:
				return null;
			}
		}
		return null;
	}



	/**
	 * Render object windows - in this case all objects are boxes.
	 * @param x
	 * @param y
	 * @param g
	 */
	public void renderObject(Image img, int x, int y, Graphics g){
		//note: x and y are the bottom left things.
		//System.out.println("box get");
		g.drawImage(img, x, y-100, null);
	}

	/**
	 * This method will scale the image so that things further away
	 * from the 'camera' are rendered as smaller and distant.
	 * 
	 * @return the scaled image
	 */
	private BufferedImage getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	public static void main(String[] args){
		new GameRenderer(new Game());
		System.out.println("bleh.");
	}
}
