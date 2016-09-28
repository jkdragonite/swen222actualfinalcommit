package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkTesting {
	
	private final int DEFAULT_BROADCAST_CLK_PERIOD = 5;
	private final int SERVER_UID = 100;
	
	private static int PLAYER_UID = 200;
	
	private final String DEFAULT_HOST = "localhost";
	private final int DEFAULT_PORT = 320542;
	
	/**
	 * Creates a server and two clients to ensure that something is connected
	 */
	public void testBasicNetwork(){
		runServer(1, 2, DEFAULT_BROADCAST_CLK_PERIOD, SERVER_UID);
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
	private void runServer(int port, int nclients, int broadcastClock, int uid){
		//ClockThread clk = new ClockThread(gameClock,null);	
		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Master[] connections = new Master[nclients];
			//Add a new connection for the number of clients requested
			ServerSocket ss = new ServerSocket(port);			
			for(int i=0; i < nclients; i++){
				Socket s = new Socket(DEFAULT_HOST, port);
				connections[i] = new Master(broadcastClock, s, uid++);
				connections[i].start();
			}
				connections[--nclients] = new Master(broadcastClock,s, uid);
				connections[nclients].start();				
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					//multiUserGame(clk,game,connections);
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
		} 
		catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}
}
