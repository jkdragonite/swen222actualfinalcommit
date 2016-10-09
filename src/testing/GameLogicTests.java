package testing;

import game.Game;
import game.InventoryItem;
import game.Player;
import game.PuzzleRoom;
import game.Room.MovementDirection;

import org.junit.Test;

public class GameLogicTests {
	
	
	@Test
	public void gameInit(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		assert(testGame.players.get(0).getLocation().equals(0,0));
	}

	@Test
	public void testPlayerCount(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		assert(testGame.players.get(0).getLocation().equals(0,0));
	}
	
	@Test
	public void testPlayerMovement(){

		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
//		System.out.println(testGame.players.get(0).getLocation().getY());
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.DOWN);
//		System.out.println(testGame.players.get(0).getLocation().getY());
		
		assert(testGame.players.get(0).getLocation().equals(0,1));
		
		
	}
	
	@Test
	public void testPlayerMovementDownUp(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
//		System.out.println(testGame.players.get(0).getLocation().getY());
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.DOWN);
//		System.out.println(testGame.players.get(0).getLocation().getY());
		assert(testGame.players.get(0).getLocation().equals(0,1));
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.UP);
		assert(testGame.players.get(0).getLocation().equals(0,0));
		
	}
	
	@Test
	public void testPlayerMovementRight(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
//		System.out.println(testGame.players.get(0).getLocation().getY());
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.RIGHT);
//		System.out.println(testGame.players.get(0).getLocation().getY());
		assert(testGame.players.get(0).getLocation().equals(1,0));
//		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.UP);
//		assert(testGame.players.get(0).getLocation().equals(0,0));
		
	}
	
	@Test
	public void testPlayerMovementRightLeft(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
//		System.out.println(testGame.players.get(0).getLocation().getY());
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.RIGHT);
//		System.out.println(testGame.players.get(0).getLocation().getY());
		assert(testGame.players.get(0).getLocation().equals(1,0));
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.LEFT);
		assert(testGame.players.get(0).getLocation().equals(0,0));
		
	}
	
	
	@Test
	public void testItemPlacement(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		testGame.players.get(0).addItem(new InventoryItem(Game.itemType.KEY));
		assert(testGame.players.get(0).getInventory().get(0).getType().equals(Game.itemType.KEY));
		
		
	}
	
	
	
}
