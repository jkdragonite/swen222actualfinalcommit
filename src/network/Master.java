package network;

import java.io.*;
import java.net.*;

/**
 * The server allows 3 clients to connect and exchange information.
 * It sends regular PING messages to clients (which will later simulate
 * the updating of game state). 
 * It is initialized via the console.
 * 
 * This is heavily influenced by DJPs Pacman game, so thanks!
 * 
 * @author Marielle
 *
 */
public final class Master extends Thread{
	//field for game world/level
	private int broadcastClock;
	/** The socket user to communicate with it's assigned Servant*/
	private final Socket socket;
	/** The unique user identifier for this particular server-client pair */
	private final int uid;
	
	public Master(int broadcastClock, Socket socket, int uid){
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
	}
	
	public void run(){
		try{
			//initialize the input and output streams for this master-client
			System.out.println("MASTER creating input and output streams");
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			//initialize a game window for the client connected
			System.out.println("MASTER writing uid to servant");
			output.writeInt(uid);
			
			//start the listening and processing, 
			//as well as the updates every so often
			boolean exit = false;
			while(!exit){
				try{
					//check for inputs and react to them
					if(input.available() != 0) {
						//read the character identifier denoting the event
						char id = input.readChar();
						switch(id){
						case 'k': 
							//read the directional identifier
							int dir = input.readInt();
							switch(dir){
							case 1:
								//queue the player for a left move
								System.out.println("MASTER RCVD EVENT: Player " + uid + "wants to move left");
								break;
							case 2:
								//queue the player for a right move
								System.out.println("MASTER RCVD EVENT: Player " + uid + "wants to move right");
								break;
							case 3:
								//queue the player for an up move
								System.out.println("MASTER RCVD EVENT: Player " + uid + "wants to move up");
								break;
							case 4: 
								//queue the player for a down move
								System.out.println("MASTER RCVD EVENT: Player " + uid + "wants to move down");
								break;
							}
						case 'm':
							int x = input.readInt();
							int y = input.readInt();
							System.out.println("MASTER RCVD EVENT: Player " + uid + "has clicked on position (" + x + ", " + y + ").");
							//check whether x/y are in in rendering window
							//ask the game whether there is a pickupable or movable object in that position
							//tell game to pick it up/ push it if not
						}
					}
					//broadcast the updated game state to the client
					Thread.sleep(broadcastClock);
				}
				catch(InterruptedException ie){}
			}
		}
		catch(IOException ioe){
			//the client has been disconnected
			//save their last instance in the game
			//transfer their items to someone else 
			//display message that they need to wait until the level is 
			//cleared before they can rejoin
		}
	}
}
