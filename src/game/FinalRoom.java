package game;

import java.util.ArrayList;

/**
 * @author Jordan Ching 300394044 
 * 
 * Last room the player can exist in
 *
 */
public class FinalRoom extends Room {
	
	
	public FinalRoom(int size){
		super(size);
		playerSpawnPoints = new ArrayList<Location>();
	}
	
}
