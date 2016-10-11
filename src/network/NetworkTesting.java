package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import game.Game;

public class NetworkTesting {
	
	private static final int DEFAULT_BROADCAST_CLK_PERIOD = 5000;
	private static final int SERVER_UID = 100;
	
	private static int PLAYER_UID = 200;
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 32768;
	
	/**
	 * Runs the server and allows the argument specified number of clients to connect
	 * (between 1 and 4)
	 * @param args
	 */
	public static void main(String[] args){
		/**************************************************
		 * 			Process command line arguments
		 * ************************************************/
		boolean server = false;
		int nclients = 1;
		String url = null; 
		int broadcastClock = DEFAULT_BROADCAST_CLK_PERIOD;
		int port = DEFAULT_PORT;
		
		for (int i = 0; i != args.length; ++i) {
			if (args[i].startsWith("-")) {
				String arg = args[i];
				if(arg.equals("-server")) {
					server = true;
					nclients = Integer.parseInt(args[++i]);
				} else if(arg.equals("-connect")) {
					url = args[++i];
				}else if(arg.equals("-port")) {
					port = Integer.parseInt(args[++i]);
				}
			}
		}
		
		InetAddress address;
		try {
			address = InetAddress.getByName(DEFAULT_HOST);
			Game game = new Game();
			Parser p = new Parser(game);
			game = p.createGameFromFiles(1);
			runServer(address, DEFAULT_PORT, 1, DEFAULT_BROADCAST_CLK_PERIOD, PLAYER_UID++, game,  1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a 'server' (a master and a servant) to test whether they're working together and sending
	 * /receiving on a basic level
	 */
	/*public static void testBasicNetwork(){
		try{
			ServerSocket ss = new ServerSocket(DEFAULT_PORT);
			InetAddress address = InetAddress.getByName(DEFAULT_HOST);
			Socket socket = new Socket(address, DEFAULT_PORT);
			
			Master m = new Master(DEFAULT_BROADCAST_CLK_PERIOD, socket, PLAYER_UID);
			m.start();
			
			Servant s = new Servant(socket);
			s.run();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
	}*/
	
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
	private static void runServer(InetAddress address, int port, int nclients, int broadcastClock, int uid, Game game, int level){		
		// Listen for connections
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Master[] connections = new Master[nclients];
			//Add a new connection for the number of clients requested
			ServerSocket ss = new ServerSocket(port);
			System.out.println(ss.getInetAddress());
			System.out.println("SERVER HOST INET ADDRESS" + ss.getInetAddress().getLocalHost().getHostAddress());
			for(int i=0; i < nclients; i++){
				Socket s = ss.accept();
				//game.addPlayer(uid++);
				connections[i] = new Master(broadcastClock, s, uid, game, level);
				connections[i].start();
				nclients--;
			}			
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					return; // done
				}
		} 
		catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 
	}
	
	private static void runClient(InetAddress addr, int port) throws IOException {		
		Socket s = new Socket(addr,port);
		System.out.println("Creating new SERVANT " + addr + ":" + port);			
		new Servant(s).run();		
	}
	
	
}
