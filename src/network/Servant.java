package network;

import java.awt.event.*;
import java.io.*;
import java.net.*;

import ui.Frame;

/**
 * The Servant takes updates to the game state from it's 
 * Master, updates the local game copy. The Servant notifies
 * it's master of player events.
 * 
 * @author Marielle
 *
 */
public final class Servant extends Thread implements KeyListener, MouseListener{
	//field for a game board
	/** The socket user to communicate with it's assigned Master*/
	private final Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private Frame gui;
	
	public Servant(Socket socket){
		this.socket = socket;
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
			
			gui = new Frame("Existential Dread (client@" + socket.getInetAddress() + ")", this);
			
			boolean exit=false;
			System.out.println("SERVANT ready to send/recieve");
			int i = 0;
			while(!exit) {
				if(input.available() != 0){
					uid = input.readInt();
					System.out.println("CLIENT UID: " + uid);
					exit = true;
				}					
				else{
					output.writeChar('t');
					output.flush();
					i++;
				}
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
	 * then occur as a result of this key press
	 */
	@Override
	public void keyPressed(KeyEvent ke) {
		try {
			int code = ke.getKeyCode();
			output.writeChar('k');
			if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				System.out.println("SERVANT SEND EVENT: left key press");
				output.writeInt(1);
			} else if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				System.out.println("SERVANT SEND EVENT: right key press");
				output.writeInt(2);
			} else if(code == KeyEvent.VK_UP) {				
				System.out.println("SERVANT SEND EVENT: up key press");
				output.writeInt(3);
			} else if(code == KeyEvent.VK_DOWN) {						
				System.out.println("SERVANT SEND EVENT: down key press");
				output.writeInt(4);
			}
			output.flush();
		} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server. Print an error message to the client.
			System.out.println(ioe.getMessage());
		}
	}
	
	/**
	 * This sends the co-ordinates to the server when the mouse is clicked.
	 * 
	 * ***Maybe do a check for whether it's in bounds first to prevent sending more than necessary?***
	 * 
	 * @param me
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		try{
			System.out.println("SERVANT SEND EVENT: mouse click");
			output.writeChar('m');
			output.writeInt(me.getX());
			output.writeInt(me.getY());
			output.flush();
		}
		catch(IOException ioe){
			//error trying to communicate the mouse click to the server so we print
			//an error
			System.out.println(ioe.getMessage());
		}
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
	 * 				Unused Required Methods 
	 *****************************************************/
	@Override
	public void keyReleased(KeyEvent arg0){}

	@Override
	public void keyTyped(KeyEvent arg0){}

	@Override
	public void mouseEntered(MouseEvent arg0){}

	@Override
	public void mouseExited(MouseEvent arg0){}

	@Override
	public void mousePressed(MouseEvent arg0){}

	@Override
	public void mouseReleased(MouseEvent arg0){}

}
