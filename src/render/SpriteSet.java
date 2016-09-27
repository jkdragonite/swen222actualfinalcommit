package render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class SpriteSet {
	
	private BufferedImage spritesheet;
	private File spriteFile;
	private Map<String, Image> spriteList;
	
	public SpriteSet(){
		loadImage();
		spriteList = new HashMap<String, Image>();
	}

	/**
	 * This method reads the packaged file and loads it up as the main 
	 * 
	 */
	private void loadImage() {
		try{
			spriteFile = new File("../images/spritesheet.bmp");
			spritesheet = ImageIO.read(spriteFile);
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
	/**
	 * This method uses the BufferedImage getSubImage() method to
	 * split the given sprite sheet up into game sprites to be 
	 * accessed from the map when needed.
	 */
	private void loadSprites(){
		
	}
	
	/**
	 * This method will scale the image so that things further away
	 * from the 'camera' are rendered as smaller and distant.
	 * 
	 * @return the scaled image
	 */
	private Image scaleImage(){
		return null;
	}
	
	/**
	 * @return list of game sprites
	 */
	public Collection<Image> getSprites(){
		return spriteList.values();
	}
}
