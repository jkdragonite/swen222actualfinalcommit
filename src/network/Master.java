package network;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import game.Game;

/**
 * The Master class is responsible for processing updates from it's 
 * connected Servant via the socket. It also (ideally) sends updates of
 * it's local game copy (which will be updated in turn by it's 
 * brothers in a game situation) to it's connected Servant. 
 * 
 * This is heavily influenced by DJPs Pacman game, so thanks!
 * 
 * @author Marielle 300333473
 *
 */
public final class Master extends Thread{
	//field for game world/level
	private Game game;
	private int level;
	private int broadcastClock;
	/** The socket user to communicate with it's assigned Servant*/
	private final Socket socket;
	/** The unique user identifier for this particular server-client pair */
	private final int uid;
	
	public Master(int broadcastClock, Socket socket, int uid, Game game, int level){
		this.broadcastClock = broadcastClock;
		this.socket = socket;
		this.uid = uid;
		this.game = game;
		this.level = level;
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
			output.writeInt(level);
			output.flush();
			
			//start the listening and processing, 
			//as well as the updates every so often
			boolean exit = false;
			while(!exit){
				try{
					//check for inputs and react to them
					if(input.available() != 0) {
						//read the character identifier denoting the event
						char id = input.readChar();
						System.out.println("MASTER recieved " + id);
						switch(id){
						case 'k': 
							//read the directional identifier
							int dir = input.readInt();
							switch(dir){
							case 1:
								//queue the player for a left move
								System.out.println("MASTER RCVD EVENT: Player " + uid + "wants to move left");
								//game.getPlayer(uid).getRoom().MovePlayer(player, direction);
								//call the game.
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
						case 't':
							System.out.println("MASTER RCVD EVENT: Servant didn't recieve data");
						}
					}
					//broadcast the updated game state to the client
					
					Thread.sleep(5000);
				}
				catch(InterruptedException ie){}
			}
			socket.close(); // release socket!
		}
		catch(IOException ioe){
			//the client has been disconnected
			//save their last instance in the game
			//transfer their items to someone else 
			//display message that they need to wait until the level is 
			//cleared before they can rejoin
			System.exit(1);
		}
	}
	
	public int getUID(){
		return this.uid;
	}
	
	public static void main(String args[]){
		JFrame frame = new JFrame("Master/Server");
		JPanel printScreen = new JPanel();
		printScreen.setLayout(new BorderLayout());

		printScreen.add(new JLabel( "Notes:" ), BorderLayout.NORTH);
	    JTextArea textArea = new JTextArea();
	    textArea.setLineWrap(true);
	    printScreen.add(textArea, BorderLayout.CENTER);
	    
	    frame.add(printScreen);
	    frame.pack();
	    frame.setVisible(true);
	}
}
