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
	
	public Image getSprite(String key){
		return spriteList.get(key);
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
		tempSprite = spritesheet.getSubimage(200, 0, 100, 200); //black hat
		spriteList.put("00", tempSprite); 
		tempSprite = spritesheet.getSubimage(300, 0, 100, 200); //green hat
		spriteList.put("01", tempSprite);
		tempSprite = spritesheet.getSubimage(200, 200, 100, 200); //purple hat
		spriteList.put("02", tempSprite);
		tempSprite = spritesheet.getSubimage(300, 200, 100, 200); //yellow hat
		spriteList.put("03", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 0, 100, 100);  //box
		spriteList.put("0a", tempSprite);
		tempSprite = spritesheet.getSubimage(900, 0, 100, 200); //door
		spriteList.put("0d", tempSprite);
		

		//load in the rest of the north facing sprites 
		tempSprite = spritesheet.getSubimage(400, 0, 200, 300); //bookshelf - front
		spriteList.put("04", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 500, 200, 100); //desk - front
		spriteList.put("05", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 0, 100, 150); //chair - front
		spriteList.put("06", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 400, 300, 100); //table - front
		spriteList.put("07", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 300, 200, 100); //bed - left
		spriteList.put("08", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 200, 75, 75); //computer - front
		spriteList.put("09", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 500, 50, 50); //gift thing - north
		spriteList.put("0b", tempSprite);
		tempSprite = spritesheet.getSubimage(100, 500, 70, 50); //books - front
		spriteList.put("0c", tempSprite);

		//load in east facing sprites.
		tempSprite = spritesheet.getSubimage(600, 0, 100, 300); //bookshelf - side
		spriteList.put("14", tempSprite);
		tempSprite = spritesheet.getSubimage(900, 500, 100, 100); //desk - side
		spriteList.put("15", tempSprite);
		tempSprite = spritesheet.getSubimage(800, 0, 100, 150); //chair - left
		spriteList.put("16", tempSprite);
		tempSprite = spritesheet.getSubimage(300, 400, 100, 100); //table - left
		spriteList.put("17", tempSprite);
		tempSprite = spritesheet.getSubimage(900, 400, 100, 100); //bed - back
		spriteList.put("18", tempSprite);
		tempSprite = spritesheet.getSubimage(75, 275, 75, 75); //computer - side
		spriteList.put("19", tempSprite);
		tempSprite = spritesheet.getSubimage(50, 500, 50, 50); //gift thing - north
		spriteList.put("1b", tempSprite);
		tempSprite = spritesheet.getSubimage(175, 500, 70, 50); //books side A
		spriteList.put("1c", tempSprite);
		
		//load in south facing sprites.
		tempSprite = spritesheet.getSubimage(400, 300, 200, 300); //bookshelf - back
		spriteList.put("24", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 500, 200, 100);//desk - back
		spriteList.put("25", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 150, 100, 150); //chair - back
		spriteList.put("26", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 400, 300, 100); //table - back
		spriteList.put("27", tempSprite);
		tempSprite = spritesheet.getSubimage(700, 400, 200, 100); //bed - side
		spriteList.put("28", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 275, 75, 75); //computer - back
		spriteList.put("29", tempSprite);
		tempSprite = spritesheet.getSubimage(0, 550, 50, 50); //gift thing - north
		spriteList.put("2b", tempSprite);
		tempSprite = spritesheet.getSubimage(100, 550, 70, 50); //books - back
		spriteList.put("2c", tempSprite);

		//load in west facing sprites.
		tempSprite = spritesheet.getSubimage(600, 300, 100, 300); //bookshelf - side
		spriteList.put("34", tempSprite);
		tempSprite = spritesheet.getSubimage(900, 500, 100, 100); //desk - side
		spriteList.put("35", tempSprite);
		tempSprite = spritesheet.getSubimage(800, 150, 100, 150); //chair - right
		spriteList.put("36", tempSprite);
		tempSprite = spritesheet.getSubimage(300, 400, 100, 100); //table - right
		spriteList.put("37", tempSprite);
		tempSprite = spritesheet.getSubimage(900, 300, 100, 100); //bed - back
		spriteList.put("38", tempSprite);
		tempSprite = spritesheet.getSubimage(75, 200, 75, 75); //computer - front
		spriteList.put("39", tempSprite);
		tempSprite = spritesheet.getSubimage(50, 550, 50, 50); //gift thing - north
		spriteList.put("3b", tempSprite);
		tempSprite = spritesheet.getSubimage(175, 550, 70, 50); //books - side B
		spriteList.put("3c", tempSprite);

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
