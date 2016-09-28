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
	
	public enum Direction {NORTH, SOUTH, EAST, WEST, TOP}
	JFrame frame;
	Graphics gra;
	Square[][] stage;
	int x, y;
	
	public static final int floor = 450;
	
	public GameRenderer(){
		frame = new JFrame();
		frame.setSize(600, 600);
		JPanel pan = new JPanel();
		gra = pan.getGraphics();
		
		frame.add(pan);
		frame.setVisible(true);
		
		//stage = loc.getSquares();
		
		render(gra);
	}
	
	public void render(Graphics g){		
		
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
	}
	
	public static void main(String[] args){
		new GameRenderer();
		System.out.println("bleh.");
	}
}
