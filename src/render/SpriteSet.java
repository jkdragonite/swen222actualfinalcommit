package render;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class SpriteSet {
	
	private BufferedImage spritesheet;
	private File spriteFile;
	public Map<String, Image> spriteList = new HashMap<String, Image>();
	
	public SpriteSet(){
		loadImage();
	}

	/**
	 * This method reads the packaged file and loads it up as the main 
	 * 
	 * @Author Brooke
	 */
	private void loadImage() {
		try{
			spriteFile = new File("images/spritesheet.png");
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
	 * 
	 * @author Brooke
	 */
	private void loadSprites(){
		
		BufferedImage tempSprite = null;

		//load the players and the sprites that look the same from every angle.
		tempSprite = spritesheet.getSubimage(200, 0, 100, 200);
		spriteList.put("00", tempSprite); //black hat
		tempSprite = spritesheet.getSubimage(300, 0, 100, 200);
		spriteList.put("01", tempSprite); //green hat
		tempSprite = spritesheet.getSubimage(200, 100, 100, 200);
		spriteList.put("02", tempSprite); //purple hat
		tempSprite = spritesheet.getSubimage(300, 100, 100, 200);
		spriteList.put("03", tempSprite); //yellow hat
		tempSprite = spritesheet.getSubimage(0, 0, 100, 100);
		spriteList.put("0a", tempSprite);
	}
	
	/**
	 * @return list of game sprites
	 * @author Brooke
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
