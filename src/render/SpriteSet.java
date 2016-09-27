package render;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class reads in the spritesheet and retrieves the relevant picture
 * to render into the room.
 * 
 * 
 * @author Brooke
 *
 */
public class SpriteSet {
	
	File spritefile = new File ("../existential-dread/images/4box.bmp");
	BufferedImage spritesheet;
	List<Image> spriteList;
	
	public SpriteSet(){
		spriteList = new ArrayList<Image>();
		loadImage();
		boxSplit();
	}
	
	public void loadImage(){
		//try and update the spritesheet.
		try{
			spritesheet = ImageIO.read(spritefile);
			System.out.println("File loaded!");
		}catch(Exception e){
			System.out.println("File read exception! " + e);
		}
	}
	
	public void boxSplit(){
		BufferedImage temp = spritesheet.getSubimage(0, 0, 100, 100);
		spriteList.add(temp);
		
		temp = spritesheet.getSubimage(0, 100, 100, 100);
		spriteList.add(temp);
		
		temp = spritesheet.getSubimage(100, 0, 100, 100);
		spriteList.add(temp);
		
		temp = spritesheet.getSubimage(100, 100, 100, 100);
		spriteList.add(temp);
	}
	
    public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        if(sbi != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(sbi, at);
        }
        return dbi;
    }

	public static void main (String[] args){
		new SpriteSet();
	}
	
}
