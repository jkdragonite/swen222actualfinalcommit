package game;

import java.util.ArrayList;

public class IntegrationDemo {
	
	public static void main(String args[]){
		
		// create instance of game
		Game game = new Game();
	
		// adds a player to the game
//		game.addPlayer("player 1");
		
		System.out.println(game.rooms.get(0).board.grid[0][0]);
		
		
		
		
		// empty arraylist of locations
		ArrayList<Location> playerMoves = new ArrayList<>();
		
		// retrieves board from a room, uses player to creat list of possible moves
		// this will be propperly implemented in the game class
		playerMoves = game.rooms.get(0).possibleMoves(game.players.get(0));
		
		
		
	}

}
