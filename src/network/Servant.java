package network;

import java.awt.event.*;
import java.io.*;
import java.net.*;
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
import ui.Frame;

/**
 * The Servant takes updates to the game state from it's 
 * Master, updates the local game copy. The Servant notifies
 * it's master of player events.
 * 
 * @author Marielle
 *
 */
public final class Servant extends Thread{
	Game game;
	Parser parser;
	/** The socket user to communicate with it's assigned Master*/
	private final Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private int level;
	private Frame gui;
	
	public Servant(Socket socket){
		this.socket = socket;
		this.game = new Game();
		System.out.println("SERVANT creating input and output streams");
		try {
			input = new DataInputStream(this.socket.getInputStream());
			output = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	@Override
	public void run(){
		try {				
			uid = input.readInt();					
			System.out.println("CLIENT UID: " + uid);
			
			level = input.readInt();
			game = new Game();
			initGame(level);
			//game.setState(Game.WAITING);
			
			gui = new Frame("Existential Dread (client@" + socket.getInetAddress() + ") - Player " + uid, game, uid);
			
			boolean exit=false;
			System.out.println("SERVANT ready to send/recieve");
			int i = 0;
			while(!exit) {
				//read the board array		
			}
			//release socket!
			socket.close(); 
		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Passes information to the Master, which handles the game interactions which 
	 * then occur as a result of user interaction
	 */
	public void send() {
		
	}
	

	
	public static void main(String[] args){
		int port = 32768;
		InetAddress addr;
		try {
			addr = InetAddress.getByName("localhost");
			Socket s = new Socket(addr,port);
			System.out.println("Creating new SERVANT " + addr + ":" + port);			
			new Servant(s).run();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/******************************************************
	 * 				Initialization Methods 
	 *****************************************************/
	private void initGame(int level){
		parser = new Parser(game);
		game = parser.createGameFromFiles(level);
	}

}
