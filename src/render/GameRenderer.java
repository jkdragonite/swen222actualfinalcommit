package render;

import java.awt.Canvas;
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
@SuppressWarnings("serial")
public class GameRenderer extends Canvas{

	Game.viewDirection viewDir;
	Square[][] stage;
	SpriteSet spriteSet = new SpriteSet();
	Room renderRoom;
	List<Image> sprites = (List<Image>) spriteSet.getSprites();
	Game game;
	Graphics gra;
	int x, y;
	boolean sqPlayer = false;
	public static final int floor = 450;
	Player refPlayer;

	public GameRenderer(Game parent, int uid){

		game = parent;
		Player refPlayer = game.getPlayer(uid);
		renderRoom = refPlayer.getRoom();
		viewDir = game.getDirection(uid);
		Board board = renderRoom.board;
		stage = board.grid;

		if(board.grid[0][0] == null){
			System.out.println("null squares");
		}

		if (stage[0][0] == null){
			System.out.println("null titles");
		}
	}


	/**
	 * 
	 */
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

		this.gra = g;

		int topLeft = (int)(LEFT + ((stage.length-1)*HORZ_DISP));
		int topWall = (int)(BASE - 150 - (stage.length*VERT_DISP));
		int topRight = LEFT + 820;
		int right = LEFT + 1000;

		//render the wall
		g.setColor(Color.GRAY);
		g.fillRect(topLeft, topWall-60, 640, (BASE-100) - (topWall-60));

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
		//int offset = LEFT * (stage.length*HORZ_DISP);
		g.setColor(Color.darkGray);
		int floorX[] = {LEFT, topLeft, topRight, right};
		int floorY[] = {BASE, BASE-100, BASE-100, BASE};
		g.fillPolygon(floorX, floorY, 4);
		
		//render the door
		if (viewDir == Game.viewDirection.NORTH){
		Image doorImg = spriteSet.getSprite("0d");
		int scaleX = (int)(doorImg.getWidth(null)*(1-((stage.length)*SCALE_FAC)));
		int scaleY = (int)(doorImg.getHeight(null)*(1-((stage.length)*SCALE_FAC)));
		Image newImg = getScaledImage(doorImg, scaleX, scaleY);
		g.drawImage(newImg, topRight-(newImg.getWidth(null)), (BASE-100)-(newImg.getHeight(null)), null);
		}
		
		//render the tiles
		for (int y = 0; y < stage.length; y++){
			for (int x = 0; x < stage.length; x++){

				//if the square has a player
				if(stage[y][x].getPlayer() != null){
					//System.out.println("player.");
					//System.out.println("stage: " + y + ", " + x);
					Image pImg = getPlayerImage(stage[y][x]);
					drawScaledImage(pImg, g, x, y);
				}
				//now draw any items on the square
				Image img = getImage(stage[y][x]);
				if (img != null){
					drawScaledImage(img, g, x, y);
				}
			}
		}
	}

	/**
	 * Generates the scaled down dimensions for images further back from 
	 * the 'camera', and then draws the scaled image over its tile.
	 * 
	 * @param img
	 * @param g
	 * @param x
	 * @param y
	 */
	private void drawScaledImage(Image img, Graphics g, int x, int y){
		
		//generate scaled values for the new dimensions
		int scaleX = (int)(img.getWidth(null)*(1-((stage.length - y)*SCALE_FAC)));
		int scaleY = (int)(img.getHeight(null)*(1-((stage.length - y)*SCALE_FAC)));

		//use the new measurements to create a new, scaled image that is 
		//seperate to the base image.
		Image newImg = getScaledImage(img, scaleX, scaleY);

		int lineX = (int)(SIZE*(1-((stage.length - y)*SCALE_FAC)));

		//get the scaled top left/right to make sure we are drawing in the 
		//right scale and place
		int imgX = (LEFT+(stage.length - y)*(HORZ_DISP) + x*lineX);
		int imgY = (BASE - (stage.length - y)*VERT_DISP);

		//finally, draw the rendered object
		this.renderObject(newImg, imgX, imgY, g);
	}

	/**
	 * Render object windows - in this case all objects are boxes.
	 * @param x
	 * @param y
	 * @param g
	 */
	public void renderObject(Image img, int x, int y, Graphics g){
		g.drawImage(img, x, y-(img.getHeight(null)), null);
	}

	//-------------------------------------------------------------------------------//


	/**
	 * 
	 * @author Brooke
	 * @param square
	 * @return the required image
	 */
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
	 * Small helper method to get from room to room.
	 * @param newRoom
	 */
	public void updateRoom(){
		Room newRoom = refPlayer.getRoom();
		this.renderRoom = newRoom;
		stage = renderRoom.board.grid;
		render(gra);
	}
	
	public Room getRoom(){
		return renderRoom;
	}

	/**
	 * 
	 * @author Brooke
	 * @param square
	 * @return the desired sprite - player model
	 * @throws RuntimeException
	 */
	private Image getPlayerImage(Square square) throws RuntimeException {
		switch(square.getPlayer().getPlayerNumber()){
		case 200: 
			return spriteSet.getSprite("00");
		case 201:
			return spriteSet.getSprite("01");
		case 202: 
			return spriteSet.getSprite("02");
		case 203:
			return spriteSet.getSprite("03");
		default:
			System.out.println("Invalid player code");
			throw new RuntimeException();
		}
	}

	/**
	 * This method will scale the image so that things further away
	 * from the 'camera' are rendered as smaller and distant.
	 * 
	 * @author Brooke
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
}
