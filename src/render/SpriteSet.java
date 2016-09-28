package render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class SpriteSet {
	
	private BufferedImage spritesheet;
	private File spriteFile;
	private Map<String, Image> spriteList = new HashMap<String, Image>();
	
	public SpriteSet(){
		loadImage();
	}

	/**
	 * This method reads the packaged file and loads it up as the main 
	 * 
	 */
	private void loadImage() {
		try{
			spriteFile = new File("images/spritesheet.bmp");
			System.out.println("File created.");
			spritesheet = ImageIO.read(spriteFile);
		}catch(IOException e){
			System.out.println(e);
		}
		loadSprites();
	}
	
	/**
	 * This method uses the BufferedImage getSubImage() method to
	 * split the given sprite sheet up into game sprites to be 
	 * accessed from the map when needed.
	 */
	private void loadSprites(){
		BufferedImage smol = spritesheet.getSubimage(0, 0, 100, 100);
		if (spriteList == null){System.out.println("aaa");}
		spriteList.put("box", smol);
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
		Collection<Image> vals = spriteList.values();
		List<Image> temp = new ArrayList<Image>();
		for (Image v : vals){
			temp.add(v);
		}
		
		return temp;
	}
}
