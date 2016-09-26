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
public class Master extends Thread{
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
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			//initialize a game window for the client connected 
			
			//start the listening and processing, 
			//as well as the updates every so often
			boolean exit = false;
			while(!exit){
				try{
					//check for inputs and react to them
					if(input.available() != 0) {
						int dir = input.readInt();
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
