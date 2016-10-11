package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import game.Container;
import game.Door;
import game.FinalRoom;
import game.Game;
import game.ImmovableItem;
import game.InventoryItem;
import game.Location;
import game.MovableItem;
import game.PuzzleRoom;
import game.Room;
import game.Game.itemType;

public class Main {
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5000;
	
	//static ints for generating unique object ids
	private static int PLAYER_UID = 200;
	private static int CONTAINER_UOID = 300;
	private static int MOVABLE_UOID = 400;
	private static int INVENTORY_UOID = 500;
	private static int IMMOVABLE_UOID = 900;
	
	private static final int NUM_LEVELS = 1;
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 32768;
	
	
	/**
	 * Runs the server and allows the argument specified number of clients to connect
	 * (between 1 and 4)
	 * @param args
	 */
	public static void main(String[] args){
		//Process command line arguments
		boolean server = false;
		int nclients = 1;
		String url = null; 
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = DEFAULT_PORT;
		
		int level = 1;
		
		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if(arg.equals("-server")) {
					server = true;
					nclients = Integer.parseInt(args[++i]);
					level = Integer.parseInt(args[++i]);
				} else if(arg.equals("-connect")) {
					url = args[++i];
				}else if(arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				}
			}
		}
		
		//error checking
		if(server && level > NUM_LEVELS){
			System.out.println("The level number indicated is not valid.");
			System.exit(1);
		}
		else if (url != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		
		if(server){
			InetAddress address;
			try {
				address = InetAddress.getByName(DEFAULT_HOST);
				Game game = new Game();
				Parser p = new Parser(game);
				game = p.createGameFromFiles(level);
				runServer(address, DEFAULT_PORT, nclients, DEFAULT_BROADCAST_CLK_PERIOD, game, PLAYER_UID++, level);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		else if(url != null){
			try{
				runClient(InetAddress.getByName(url), port);
			}
			catch(IOException ioe){
				System.out.println("Error parsing game file" + ioe.getMessage());
				System.exit(1);
			}
		}
		System.exit(0);
	}
	
	/************************************************
	 * 				HELPER METHODS
	 ***********************************************/
	
	/**
	 * Sets up a server from given parameters, and runs some tests from the multiUserTests method 
	 * after the clients have connected. 
	 * @param port
	 * @param nclients
	 * @param gameClock
	 * @param broadcastClock
	 * @param uid
	 */
	private static void runServer(InetAddress address, int port, int nclients, int broadcastClock, Game game, int uid, int level){		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Master[] connections = new Master[nclients];
			//Add a new connection for the number of clients requested
			ServerSocket ss = new ServerSocket(port);			
			for(int i=0; i < nclients; i++){
				Socket s = ss.accept();
				connections[i] = new Master(broadcastClock, s, uid++, game, level);
				connections[i].start();
				nclients--;
			}			
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					runGame(1, connections);
					return; // done
				}
		} 
		catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}
	
	/**
	 * Creates a new instance of the servant class to make a client connected to our server
	 * @param addr
	 * @param port
	 * @throws IOException
	 */
	private static void runClient(InetAddress addr, int port) throws IOException {		
		Socket s = new Socket(addr,port);
		System.out.println("Creating new Existential Dread client " + addr + ":" + port);			
		new Servant(s).run();		
	}
	
	
	/**Default starting level information files*/
	private static final File L1R1 = new File("L1R1.txt");
	private static final File L1FR = new File("L1FR.txt");
	
	private static Game createGameFromFiles(int level){
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
	 * Reads in a single room instance from a file and adds it to the game object. 
	 * Used during game initialization. 
	 * 
	 * @param game
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static Room roomFromFile(Game game, File file)throws IOException{
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
	 * Sets up and runs a multi- user game of Existential Dread given the
	 * connections and an integer representing the desired level to 
	 * start on. 
	 * @param level
	 * @param connections
	 * @throws IOException
	 */
	private static void runGame(int level, Master...connections) throws IOException{
		//first read in the relevant level files
		Game game = createGameFromFiles(level);
		for(Master m : connections){
			game.addPlayer(m.getUID());
		}
		
		while(atLeastOneConnection(connections)){
			
		}
	}
	
	private static boolean atLeastOneConnection(Master...connections){
		for (Master m : connections) {
			if (m.isAlive()) {
				return true;
			}			
		}
		return false;
	}
}
