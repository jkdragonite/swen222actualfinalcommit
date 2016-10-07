package network;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import game.Game;

/**
 * Responsible for testing the methods to be used in game for translating 
 * game state to clients and allowing them to interpret game state onto 
 * their local copy of the board.
 * 
 * Basically invented to  make sure I didn't fuck up the game class
 * 
 * @author Marielle
 *
 */
public class Parser {
	private Game game;
	
	//list of constant ints representing how many bytes each game element
	//is typically comprised of when they are sent from the master
	//this excludes the char that preceeds them
	private final int DOOR_BYTES = 6;
	private final int STORAGE_BYTES = 3;
	public final int MOVABLE_BYTES = 4;
	
	public Parser(Game game){
		this.game = game;
	}
	
	/**
	 * Reads a file from the game folder containing the initial game 
	 * state and converts it into an array of bytes which can then be 
	 * read by the client at the receiving end and converted back into
	 * game state data. 
	 *  
	 * @param file
	 * @return
	 */
	public static Game gameFromFile(File file)throws IOException{
		//create the file readers ready for parsing
		FileReader fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);
		
		//initialize a new basic game which we will modify based on file contents
		Game game = new Game();
		
		ArrayList<String> lines = new ArrayList<String>();	//all lines in file
		String line; //current line in file
		int lineCount  = 0;
		
		//while the file has lines, process them
		while((line = in.readLine()) != null){
			lines.add(line);
			
			//process 
			if(lineCount == 0){
				
			}
		}
		
		return game;
	}
	
	public static byte[] stateToBytes() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);

		//dout.writeByte(game.getState());
		
		//first write the door updates
		return bout.toByteArray();
	}
	
	public synchronized void fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		
		//state = din.readByte();
		
		//read in the door object information
		//game.getDoors
	}
}
