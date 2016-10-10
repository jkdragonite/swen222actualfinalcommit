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
	
	//static ints for generating unique object ids
	private static int CONTAINER_UOID = 300;
	private static int MOVABLE_UOID = 400;
	private static int INVENTORY_UOID = 500;
	private static int IMMOVABLE_UOID = 900;
	

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
						Container cont = new Container(type, loc, CONTAINER_UOID++);
						containers.add(cont);
						room.setImmovableItem(cont, loc);
						break;
					case 'Q':
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						String name = sc.nextLine();
						InventoryItem invItem = new InventoryItem(type, loc, name, INVENTORY_UOID++);
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
						ImmovableItem immItem = new ImmovableItem(type, loc, IMMOVABLE_UOID++);
						
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
						MovableItem movItem = new MovableItem(type, loc, MOVABLE_UOID++);
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
		int numRooms = 0;
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
			
			//indicate the room is done
			dout.writeChar('Z');
		}
		
		return bout.toByteArray();
	}
	
	private static void doorToOutput(DataOutputStream dout, Room r) throws IOException{
		Door door = r.door;
		if(r.door == null){
			dout.writeChar('X');
		}
		else{
			dout.writeChar(door.getCharacter());
			if (door.getKeyHole().isEmpty()) {dout.writeInt(0);}
			else {dout.writeInt(1);}
		}
	}
	
	private static void containersToOutput(DataOutputStream dout, Room r) throws IOException{
		if(r.containers.isEmpty()){
			dout.writeChar('X');
		}
		else{
			for(Container c : r.containers){
				c.toOutputStream(dout);
				
				if (c.hasItems()) {
					dout.writeInt(1);
					//write in the unique id of the item stored there
					for(InventoryItem i: c.getItems()){
						dout.writeInt(i.getUoid());
					}
				}
				else {dout.writeInt(0);}
			}
		}
			
	}
	
	private static void immovablesToOutput(DataOutputStream dout, Room r, Game game) throws IOException{
		if(r.immovableItems.isEmpty()){
			dout.writeChar('X');
		}
		else{
			for(ImmovableItem im : r.immovableItems){
				im.toOutputStream(dout);
				itemType type = im.getType();
				if(type == Game.itemType.BED || type == Game.itemType.BOOKSHELF || type == Game.itemType.DESK){
					Location loc = im.getLocationsCovered().get(0);
					dout.writeInt(loc.getX());
					dout.writeInt(loc.getY());
				}
				else if(type == Game.itemType.TABLE){
					Location loc = im.getLocationsCovered().get(0);
					dout.writeInt(loc.getX());
					dout.writeInt(loc.getY());
					
					loc = im.getLocationsCovered().get(1);
					dout.writeInt(loc.getX());
					dout.writeInt(loc.getY());
				}
			}
		}
		
	}
	
 	private static void movablesToOutput(DataOutputStream dout, Room r) throws IOException{
		if(r.movableItems.isEmpty()){
			dout.writeChar('X');
		}
		else{
			for(MovableItem mi : r.movableItems){
				mi.toOutputStream(dout);
			}
		}
	}
	
	private static void playersToOutput(DataOutputStream dout, Game game, Room r) throws IOException{
		if(r.playersInRoom.isEmpty()){
			dout.writeChar('X');
		}
		else{
			for(Player p : game.players){
				if(p.getRoom().equals(r)){
					dout.writeChar('p');
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
	
	/**
	 * Reads in the game information and updates the game and relevant objects based on the 
	 * stream information. 
	 * 
	 * If there is no item of a certain type (i.e. no InventoryItems
	 * 
	 * @param bytes
	 * @param game
	 * @throws IOException
	 */
	public synchronized void updateFromByteArray(byte[] bytes, Game game) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		
		//fields for storing game objects while checking consistency
		Room room;
		Door door;
		
		//Lists to store items which have changes flagged
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<MovableItem> movable = new ArrayList<MovableItem>();
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<InventoryItem> inventory = new ArrayList<InventoryItem>();
		
		game.setState(din.readInt());

		boolean reading = true;
		
		while(reading){
			//read in the room type and room number
			char rtype = din.readChar();
			int rNum = din.readInt();
			
			//get the relevant room object from the game 
			room = game.rooms.get(rNum);
			
			//check for whether there are item types first
			char nextItem = din.readChar();
			while(nextItem != 'Z'){
				switch(nextItem){
				case 'D':
				case 'd': 
					//door is now unlocked: check inventory items later and check consistency
					door = room.getDoor();
					if(!door.isUnlocked()){
						door.setUnlocked(true);
					}
					break;
				case 'C':
					//container object: parse uoid
					int cuoid = din.readInt();
					//parse location
					int cX = din.readInt();
					int cY = din.readInt();
					boolean hasItems = (din.readInt() == 0) ? false : true;
					Container c = (Container) room.itemsHashMap.get(cuoid);
					int suoid = din.readInt();
					
					if(hasItems && c.hasItems()){
						//check it's the same item?
						for(InventoryItem i: c.getItems()){
							i.getUoid();
						}
					}
					else if(!hasItems && c.hasItems()){
						//remove item from container, put in inventory arraylist temporarily
						InventoryItem toRemove = (InventoryItem) room.itemsHashMap.get(suoid);
						c.removeItem(toRemove);
					}
					else if(hasItems && !c.hasItems()){
						//add the item to the container
						InventoryItem toAdd = (InventoryItem) room.itemsHashMap.get(suoid);
						c.addItem(toAdd);
					}
					break;
				case 'M':
					//movableItem object: parse location
					int mX = din.readInt();
					int mY = din.readInt();

					break;
				case 'p':
					break;
				case 'Q':
					break;
				}
				nextItem = din.readChar();
			}
		}
		
	}

}
