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
import java.util.Scanner;

import game.Container;
import game.Door;
import game.FinalRoom;
import game.Game;
import game.Game.itemType;
import game.ImmovableItem;
import game.InventoryItem;
import game.Item;
import game.Location;
import game.MovableItem;
import game.Player;
import game.PuzzleRoom;
import game.Room;

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
	public Game game;
	
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
 * Reads in a single room instance from a file and adds it to the game object. 
 * Used during game initialization. 
 * 
 * @param game
 * @param file
 * @return
 * @throws IOException
 */
	public static Room roomFromFile(Game game, File file)throws IOException{
		//create the file readers ready for parsing
		FileReader fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);

		Room room = new PuzzleRoom(10);
		ArrayList<Container> containers = new ArrayList<Container>();
		
		ArrayList<String> lines = new ArrayList<String>();	//all lines in file
		String line; //current line in file
		int lineCount  = 0;
		
		//while the file has lines, process them
		while((line = in.readLine()) != null){
			lines.add(line);
			Scanner sc = new Scanner(line);
			
			//first line we need to process is line 12, for the room size
			if(lineCount == 11){
				char id = line.charAt(0);
				sc.next(); //skip char
				
				if(id == 'R'){
					room = new PuzzleRoom(sc.nextInt());
				}
				else if(id == 'F'){
					room = new FinalRoom(sc.nextInt());
				}
			}
			//process the items in the room
			else if(lineCount > 11){
				char id = line.charAt(0);
				sc.next(); //skip char
				//get one location as all items have at least one
				Location loc = new Location(sc.nextInt(), sc.nextInt());
				int itemID;
				itemType type;
				
				switch(id){
					case 'D':
						Door door = new Door(loc);
						room.addDoor(door);
						break;
					case 'C':
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						Container cont = new Container(type, loc);
						containers.add(cont);
						room.setImmovableItem(cont, loc);
						break;
					case 'Q':
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						String name = sc.nextLine();
						InventoryItem invItem = new InventoryItem(type, loc, name);
						//check whether this inventory item is in the same space as a container
						for(Container c: containers){
							if(c.getLocation().equals(loc)){
								c.addItem(invItem);
							}
						}
						//add item to room
						room.setInventoryItem(invItem, loc);
						break;
					case 'I':
						//process type
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						ImmovableItem immItem = new ImmovableItem(type, loc);
						
						if(type != Game.itemType.COMPUTER || type != Game.itemType.DARKNESS 
								|| type != Game.itemType.CHAIR){
							//process additional locations it covers, as most immovables cover two locations
							Location secondary = new Location(sc.nextInt(), sc.nextInt());
							immItem.addToLocationsCovered(secondary);
							
							if(type == Game.itemType.TABLE){
								//table has three locations covered, so process additional one
								Location tertiary = new Location(sc.nextInt(), sc.nextInt());
								immItem.addToLocationsCovered(tertiary);
								
							}
						}
						room.setImmovableItem(immItem, loc);
						break;
					case 'M':
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						MovableItem movItem = new MovableItem(type, loc);
						room.setMovableItem(movItem, loc);
						break;
					case 'S':
						room.addPSP(loc);
						break;						
				}
			}
			lineCount++;
		}
		
		return room;
	}
	
	/**
	 * Captures the data ready for sending across the connection for the 
	 * first time. 
	 * The main difference between this and stateUpdateToBytes is that
	 * @param game
	 * @return
	 * @throws IOException 
	 */
	public static byte[] initGameToBytes(Game game) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		
		dout.writeByte(game.getState());
		int numRooms = 1;
		for(Room r : game.rooms){
			//write room id (i.e.R 1/R 3 or F)
			if(r instanceof PuzzleRoom){
				dout.writeChar('R');
				dout.writeInt(numRooms);
				numRooms++;
			}
			else if(r instanceof FinalRoom){
				dout.writeChar('F');
				dout.writeInt(numRooms);	//don't increment numRooms as this should be last chronologically
			}
			
			//write door information
			doorToOutput(dout, r);
			//write the immovable/static items to the output
			immovablesToOutput(dout, r, game);
			//write the containers around the room to output
			containersToOutput(dout, r);
			//write movable items to output
			movablesToOutput(dout, r);
			//write players to output
			playersToOutput(dout, game, r);
			//write inventory items to output
			inventoryToOutput(dout, r);
		}
		return bout.toByteArray();
	}
	
	/**
	 * Used to send the current state of the board as an update to each client. 
	 * As this is an update we only send information on doors (for lock state),
	 * players (for movement) and pickupable/movable items (for movement/new ownership)
	 * 
	 * @param game
	 * @return
	 * @throws IOException
	 */
	public static byte[] stateUpdateToBytes(Game game) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		
		dout.writeByte(game.getState());
		int numRooms = 1;
		for(Room r : game.rooms){
			if(r instanceof PuzzleRoom){
				dout.writeChar('R');
				dout.writeInt(numRooms);
				numRooms++;
			}
			else if(r instanceof FinalRoom){
				dout.writeChar('F');
				dout.writeInt(numRooms);
			}
			
			doorToOutput(dout, r);
			containersToOutput(dout, r);
			movablesToOutput(dout, r);
			playersToOutput(dout, game, r);
			inventoryToOutput(dout, r);
		}
		
		return bout.toByteArray();
	}
	
	private static void doorToOutput(DataOutputStream dout, Room r) throws IOException{
		Door door = r.door;
		dout.writeChar(door.getCharacter());
		if (door.isUnlocked()) {dout.writeInt(0);}
		else {dout.writeInt(1);}
	}
	
	private static void containersToOutput(DataOutputStream dout, Room r) throws IOException{
		for(Container c : r.containers){
			c.toOutputStream(dout);
			
			if (c.hasItem()) {dout.writeInt(1);}
			else {dout.writeInt(0);}
		}
	}
	
	private static void immovablesToOutput(DataOutputStream dout, Room r, Game game) throws IOException{
		for(ImmovableItem im : r.immovableItems){
			im.toOutputStream(dout);
			itemType type = im.getType();
			if(type == Game.itemType.BED){
				dout.writeInt(614);
				Location loc = im.getLocationsCovered().get(0);
				dout.writeInt(loc.getX());
				dout.writeInt(loc.getY());
			}
			else if(type == Game.itemType.BOOKSHELF){
				dout.writeInt(611);
				Location loc = im.getLocationsCovered().get(0);
				dout.writeInt(loc.getX());
				dout.writeInt(loc.getY());
			}
			else if(type == Game.itemType.DESK){
				dout.writeInt(618);
				Location loc = im.getLocationsCovered().get(0);
				dout.writeInt(loc.getX());
				dout.writeInt(loc.getY());
			}
			else if(type == Game.itemType.TABLE){
				dout.writeInt(620);
				Location loc = im.getLocationsCovered().get(0);
				dout.writeInt(loc.getX());
				dout.writeInt(loc.getY());
				
				loc = im.getLocationsCovered().get(1);
				dout.writeInt(loc.getX());
				dout.writeInt(loc.getY());
			}
		}
	}
	
 	private static void movablesToOutput(DataOutputStream dout, Room r) throws IOException{
		for(MovableItem mi : r.movableItems){
			mi.toOutputStream(dout);
		}
	}
	
	private static void playersToOutput(DataOutputStream dout, Game game, Room r) throws IOException{
		for(Player p : game.players){
			if(p.getRoom().equals(r)){
				dout.writeChar(p.getCharacter());
				dout.writeInt(p.getLocation().getX());
				dout.writeInt(p.getLocation().getY());
				ArrayList<InventoryItem> inventory = p.getInventory();
				if(inventory.isEmpty()){
					dout.writeInt(0);
				}
				else{
					dout.writeInt(1);
				}
			}
		}
	}
	
	private static void inventoryToOutput(DataOutputStream dout, Room r) throws IOException{
		for(InventoryItem i : r.inventoryItems){
			i.toOutputStream(dout);
			if(i.hasOwner()){
				dout.writeInt(1);
				dout.writeInt(i.getOwnerID());
			}
			else{dout.writeInt(0);}
		}
	}
	
	public synchronized void fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		
		//state = din.readByte();
		
		//read in the door object information
		//game.getDoors
	}
}
