package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
 * Responsible for the methods to be used in game for translating 
 * game state to clients and allowing them to interpret game state onto 
 * their local copy of the board. Also provides functionality to create
 * a game from files and create rooms with files.
 * 
 * Basically invented to  make sure I didn't mess up the game class/
 * fill it with unnecessary clutter. 
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
	
	/**Default starting level information files*/
	private static final String L1R1 = "levels/L1R1.txt";
	private static final String L1FR = "levels/L1FR.txt";
	
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
	public static Room roomFromFile(Game game, String file)throws IOException{
		Room room = new PuzzleRoom(10);
		ArrayList<Container> containers = new ArrayList<Container>();
		
		List<String> lines = Files.readAllLines(Paths.get(file));	//all lines in file
		
		String line; //current line in file
		Scanner sc = new Scanner(lines.get(0));
		int lineCount  = 0;
		
		//while the file has lines, process them
		while(lineCount < lines.size()){
			line = lines.get(lineCount);
			sc = new Scanner(line);
			
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
						System.out.println("Door processed");
						break;
					case 'C':
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						Container cont = new Container(type, loc, CONTAINER_UOID++);
						containers.add(cont);
						room.setImmovableItem(cont, loc);
						System.out.println("Container processed");
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
						System.out.println("Inventory Item processed");
						break;
					case 'I':
						//process type
						itemID = sc.nextInt();
						type = game.itemCodes.get(itemID);
						ImmovableItem immItem = new ImmovableItem(type, loc, IMMOVABLE_UOID++);
						System.out.println(type);
						System.out.println(loc.getX() + " " + loc.getY());
						if(type == Game.itemType.DESK || type == Game.itemType.BOOKSHELF 
								|| type == Game.itemType.BED || type == Game.itemType.TABLE){
							//process additional locations it covers, as most immovables cover two locations
							Location secondary = new Location(sc.nextInt(), sc.nextInt());
							System.out.println(secondary.getX() + " " + secondary.getY());
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
		sc.close();
		return room;
	}
	
	/**
	 * Captures the data ready for sending across the connection for the 
	 * first time. 
	 * The main difference between this and stateUpdateToBytes is that
	 * the immovable items are sent in this as well.
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
					//write in the number of items in the inventory followed by each item's uoid
					dout.writeInt(c.getItems().size());
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
						dout.writeInt(inventory.size());
						for(InventoryItem i: inventory){
							dout.writeInt(i.getUoid());
						}
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
					din.readInt();			//skip over the itemType id, we don't need it for updates
					boolean hasItems = (din.readInt() == 0) ? false : true;
					Container c = (Container) room.itemsHashMap.get(cuoid);
					
					if(hasItems){
						int numItems = din.readInt();
						
						//process new inventory information
						ArrayList<InventoryItem> newInv = new ArrayList<InventoryItem>();
						for(int i = 0; i < numItems; i++){
							newInv.add((InventoryItem) room.itemsHashMap.get(din.readInt()));	
						}
						
						//inventory checks
						if(!c.hasItems()){
							for(InventoryItem i : newInv){
								c.addItem(i);
							}
						}
						else{
							//check for items adds
							for(InventoryItem i : newInv){
								if(!(c.getItems().contains(i))){
									c.addItem(i);
								}
							}
							//check for item removes
							for(InventoryItem i : c.getItems()){
								if(!(newInv.contains(i))){
									c.removeItem(i);
								}
							}
						}
					}
					else{
						if(c.hasItems()){
							for(InventoryItem i : c.getItems()){
								c.removeItem(i);
							}
						}
					}
					break;
				case 'M':
					int muoid = din.readInt();
					//get potentially new x/y
					int mX = din.readInt();
					int mY = din.readInt();
					din.readInt();			//skip over the itemType id, we don't need it for updates
					//find item in room, check location against the new one, move if different
					MovableItem mi = (MovableItem) room.itemsHashMap.get(muoid);
					Location mLoc = mi.getLocation();
					
					if(mLoc.getX() != mX || mLoc.getY() != mY){
						room.setMovableItem(mi, new Location(mX, mY));
						//need to remove it from previous position--updateMovableItem?
					}
					break;
				case 'p':
					int puid = din.readInt();
					Player player = game.getPlayer(puid);
					Location pLoc = player.getLocation();
					//read location, check against local copy, change if necessary
					int pX = din.readInt();
					int pY = din.readInt();
					
					if(pLoc.getX() != pX || pLoc.getY() != pY){
						room.placePlayer(player, new Location(pX, pY));
					}
					
					//read in has inventory boolean
					boolean hasInv = (din.readInt() == 0) ? false : true;
					if(hasInv){
						//read in inventory items, check against local copy
						int numItems = din.readInt();
						ArrayList<InventoryItem> newItms = new ArrayList<InventoryItem>();
						
						for(int i = 0; i < numItems; i++){
							newItms.add((InventoryItem) room.itemsHashMap.get(din.readInt()));
						}
						
						if(player.getInventory().isEmpty()){
							for(InventoryItem i : newItms){
								player.addItem(i);
							}
						}
						else{
							//check for items adds
							for(InventoryItem i : newItms){
								if(!(player.getInventory().contains(i))){
									player.addItem(i);
								}
							}
							//check for item removes
							for(InventoryItem i : player.getInventory()){
								if(!(newItms.contains(i))){
									player.removeItem(i);
								}
							}
						}
					}
					else{
						if(!(player.getInventory().isEmpty())){
							for(InventoryItem i : player.getInventory()){
								player.removeItem(i);
							}
						}
					}
					
					break;
				case 'Q':
					int iuoid = din.readInt();
					int iX = din.readInt();
					int iY = din.readInt();
					din.readInt();			//skip over the itemType id, we don't need it for updates
					
					InventoryItem item = (InventoryItem) room.itemsHashMap.get(iuoid);
					Location iLoc = item.getLocation();
					//check for movement
					if(iLoc.getX() != iX || iLoc.getY() != iY){
						//check for an item or player on the square
						Item possibleItem = room.getItemOnSquare(new Location(iX, iY));
						Player possiblePlayer = room.getPlayerOnSquare(new Location(iX, iY));
						Location doorLoc = room.getDoor().getLocation();
						
						if(possibleItem != null){
							if(possibleItem instanceof Container){
								//check contents of container for this item
								Container cont = (Container) possibleItem;
								if(!(cont.getItems().contains(item))){
									cont.addItem(item);
									item.setLocation(new Location(iX, iY));
								}
							}
							//add method to move item to next available square if there's another object 
							//type present?
						}
						else if(possiblePlayer != null){
							//check if the player's inventory has the item
							if(!(possiblePlayer.getInventory().contains(item))){
								possiblePlayer.addItem(item);
								item.setLocation(new Location(iX, iY));
							}
						}
						else if(doorLoc.getX() == iX && doorLoc.getY() == iY){
							//check if the item is in the keyhole
						}
					}
					else{
						item.setLocation(new Location(iX,iY));
					}
					break;
				}
				nextItem = din.readChar();
			}
		}
		
	}

	public static Game createGameFromFiles(int level){
		Game game = new Game();
		switch(level){
		case 1 : 
			try{
				game.rooms.add(roomFromFile(game, L1R1));
				game.rooms.add(roomFromFile(game, L1FR));
				
				game.setDestinationRooms();
			}
			catch(IOException ioe){
				System.out.println("Error parsing game file" + ioe.getMessage());
				System.exit(1);
			}
			break;
		}
		return game;
	}
	
	/**
	 * Reads in the game information sent on initialisation?
	 * @param bytes
	 * @param game
	 * @throws IOException
	 */
	public synchronized void initFromByteArray(byte[] bytes, Game game) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		
		//fields for storing game objects while checking consistency
		Room room;
		Door door;
		
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
					din.readInt();			//skip over the itemType id, we don't need it for updates
					boolean hasItems = (din.readInt() == 0) ? false : true;
					Container c = (Container) room.itemsHashMap.get(cuoid);
					
					if(hasItems){
						int numItems = din.readInt();
						
						//process new inventory information
						ArrayList<InventoryItem> newInv = new ArrayList<InventoryItem>();
						for(int i = 0; i < numItems; i++){
							newInv.add((InventoryItem) room.itemsHashMap.get(din.readInt()));	
						}
						
						//inventory checks
						if(!c.hasItems()){
							for(InventoryItem i : newInv){
								c.addItem(i);
							}
						}
						else{
							//check for items adds
							for(InventoryItem i : newInv){
								if(!(c.getItems().contains(i))){
									c.addItem(i);
								}
							}
							//check for item removes
							for(InventoryItem i : c.getItems()){
								if(!(newInv.contains(i))){
									c.removeItem(i);
								}
							}
						}
					}
					else{
						if(c.hasItems()){
							for(InventoryItem i : c.getItems()){
								c.removeItem(i);
							}
						}
					}
					break;
				case 'M':
					int muoid = din.readInt();
					//get potentially new x/y
					int mX = din.readInt();
					int mY = din.readInt();
					din.readInt();			//skip over the itemType id, we don't need it for updates
					//find item in room, check location against the new one, move if different
					MovableItem mi = (MovableItem) room.itemsHashMap.get(muoid);
					Location mLoc = mi.getLocation();
					
					if(mLoc.getX() != mX || mLoc.getY() != mY){
						room.setMovableItem(mi, new Location(mX, mY));
						//need to remove it from previous position--updateMovableItem?
					}
					break;
				case 'p':
					int puid = din.readInt();
					Player player = game.getPlayer(puid);
					Location pLoc = player.getLocation();
					//read location, check against local copy, change if necessary
					int pX = din.readInt();
					int pY = din.readInt();
					
					if(pLoc.getX() != pX || pLoc.getY() != pY){
						room.placePlayer(player, new Location(pX, pY));
					}
					
					//read in has inventory boolean
					boolean hasInv = (din.readInt() == 0) ? false : true;
					if(hasInv){
						//read in inventory items, check against local copy
						int numItems = din.readInt();
						ArrayList<InventoryItem> newItms = new ArrayList<InventoryItem>();
						
						for(int i = 0; i < numItems; i++){
							newItms.add((InventoryItem) room.itemsHashMap.get(din.readInt()));
						}
						
						if(player.getInventory().isEmpty()){
							for(InventoryItem i : newItms){
								player.addItem(i);
							}
						}
						else{
							//check for items adds
							for(InventoryItem i : newItms){
								if(!(player.getInventory().contains(i))){
									player.addItem(i);
								}
							}
							//check for item removes
							for(InventoryItem i : player.getInventory()){
								if(!(newItms.contains(i))){
									player.removeItem(i);
								}
							}
						}
					}
					else{
						if(!(player.getInventory().isEmpty())){
							for(InventoryItem i : player.getInventory()){
								player.removeItem(i);
							}
						}
					}
					
					break;
				case 'Q':
					int iuoid = din.readInt();
					int iX = din.readInt();
					int iY = din.readInt();
					din.readInt();			//skip over the itemType id, we don't need it for updates
					
					InventoryItem item = (InventoryItem) room.itemsHashMap.get(iuoid);
					Location iLoc = item.getLocation();
					//check for movement
					if(iLoc.getX() != iX || iLoc.getY() != iY){
						//check for an item or player on the square
						Item possibleItem = room.getItemOnSquare(new Location(iX, iY));
						Player possiblePlayer = room.getPlayerOnSquare(new Location(iX, iY));
						Location doorLoc = room.getDoor().getLocation();
						
						if(possibleItem != null){
							if(possibleItem instanceof Container){
								//check contents of container for this item
								Container cont = (Container) possibleItem;
								if(!(cont.getItems().contains(item))){
									cont.addItem(item);
									item.setLocation(new Location(iX, iY));
								}
							}
							//add method to move item to next available square if there's another object 
							//type present?
						}
						else if(possiblePlayer != null){
							//check if the player's inventory has the item
							if(!(possiblePlayer.getInventory().contains(item))){
								possiblePlayer.addItem(item);
								item.setLocation(new Location(iX, iY));
							}
						}
						else if(doorLoc.getX() == iX && doorLoc.getY() == iY){
							//check if the item is in the keyhole
						}
					}
					else{
						item.setLocation(new Location(iX,iY));
					}
					break;
				}
				nextItem = din.readChar();
			}
			if(rtype == 'F'){reading = false;}
		}
	}
}
