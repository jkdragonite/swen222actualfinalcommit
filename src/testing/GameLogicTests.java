package testing;

import static org.junit.Assert.*;
import javafx.scene.shape.MoveTo;
import game.Container;
import game.Door;
import game.Game;
import game.InventoryItem;
import game.Location;
import game.MovableItem;
import game.Player;
import game.PuzzleRoom;
import game.Room;
import game.Room.MovementDirection;

import org.junit.Test;

public class GameLogicTests {
	
	
	@Test
	public void gameInit(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		assertTrue(testGame.players.get(0).getLocation().getX() == 0);
		assertTrue(testGame.players.get(0).getLocation().getY() == 0);
		testGame.rooms.get(0).updatePlayerMoves(testGame.players.get(0));
	}

	@Test
	public void testPlayerCount(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		System.out.println("Player Size " + testGame.players.size());
//		System.out.println(testGame.players);
		// we added a temporary player and room to constructor
		assertTrue(testGame.players.size() == 2);
	}
	
	@Test
	public void testPlayerMovement(){

		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
//		System.out.println(testGame.players.get(0).getLocation().getY());
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.DOWN);
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.DOWN);
		testGame.rooms.get(0).MovePlayer(testGame.players.get(0), MovementDirection.DOWN);
//		System.out.println(testGame.players.get(0).getLocation().getY());
		System.out.println(testGame.getPlayer(200).getLocation().toString());
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
		testGame.players.get(0).addItem(new InventoryItem(Game.itemType.KEY, "Key"));
		assert(testGame.players.get(0).getInventory().get(0).getType().equals(Game.itemType.KEY));
	}
	
	@Test
	public void testMultiplePlayerItems(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		testGame.players.get(0).addItem(new InventoryItem(Game.itemType.KEY, "Key"));
		testGame.players.get(0).addItem(new InventoryItem(Game.itemType.BOOK, "Book"));
		assert(testGame.players.get(0).getInventory().get(0).getType().equals(Game.itemType.KEY));
		assert(testGame.players.get(0).getInventory().get(0).getType().equals(Game.itemType.BOOK));
		assertTrue(testGame.players.get(0).getInventory().size() == 2);
	}
	
	@Test
	public void itemPlacementTet(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		Player player201 = new Player(201, testGame.rooms.get(0));
		testGame.players.add(player201);
		InventoryItem keyInventoryItem = new InventoryItem(Game.itemType.KEY, "Key");
		testGame.rooms.get(0).board.getSquare(player201.getLocation()).setItem(keyInventoryItem);
		assertTrue(testGame.rooms.get(0).board.getSquare(player201.getLocation()).getItem().equals(keyInventoryItem)); 
	}
	
	@Test
	public void playerItemPickupMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		InventoryItem keyInventoryItem = new InventoryItem(Game.itemType.KEY, "Key");
		testGame.rooms.get(0).board.getSquare(testGame.getPlayer(201).getLocation()).setInventory(keyInventoryItem);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
//		testGame.rooms.get(0).updatePlayerMoves(testGame.getPlayer(201));
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getInventory());
		System.out.println("Item pickups " + testGame.getPlayer(201).itemPickups);
		assertEquals(1, testGame.getPlayer(201).itemPickups.size());
		assertEquals(true,testGame.getPlayer(201).itemPickups.keySet().contains(MovementDirection.LEFT));		
	}
	
	@Test
	public void playerItemPickupItem(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		InventoryItem keyInventoryItem = new InventoryItem(Game.itemType.KEY, "Key");
		testGame.rooms.get(0).board.getSquare(testGame.getPlayer(201).getLocation()).setInventory(keyInventoryItem);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
//		testGame.rooms.get(0).updatePlayerMoves(testGame.getPlayer(201));
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getInventory());
		System.out.println("Item pickups " + testGame.getPlayer(201).itemPickups);
		assertEquals(1, testGame.getPlayer(201).itemPickups.size());
		assertEquals(true,testGame.getPlayer(201).itemPickups.keySet().contains(MovementDirection.LEFT));
		testGame.rooms.get(0).pickupItem(testGame.getPlayer(201), testGame.getPlayer(201).itemPickups.get(MovementDirection.LEFT));
		System.out.println(testGame.getPlayer(201).getInventory());
		assertEquals(keyInventoryItem, testGame.getPlayer(201).getInventory().get(0));
	}
	
	@Test
	public void getNeighboursLength(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		Player player201 = new Player(201, testGame.rooms.get(0));
		testGame.players.add(player201);
		testGame.rooms.get(0).MovePlayer(player201, MovementDirection.DOWN);
		testGame.rooms.get(0).MovePlayer(player201, MovementDirection.RIGHT);
//		System.out.println(testGame.rooms.get(0).board.getNeighbours(player201.getLocation()));
//		System.out.println(player201.getLocation().toString());
		assertEquals(4, testGame.rooms.get(0).board.getNeighbours(player201.getLocation()).size());
	}
	
	
	@Test
	public void testAvailablePlayerMoves(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		assertTrue(testGame.players.get(0).getLocation().getX() == 0);
		assertTrue(testGame.players.get(0).getLocation().getY() == 0);
		testGame.rooms.get(0).updatePlayerMoves(testGame.players.get(0));
		assertEquals(true, testGame.players.get(0).moves.keySet().contains(MovementDirection.RIGHT));
		assertEquals(true, testGame.players.get(0).moves.keySet().contains(MovementDirection.DOWN));
	}
	
	@Test
	public void playerSearchMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		InventoryItem keyInventoryItem = new InventoryItem(Game.itemType.KEY, "Key");
		Location containerLocation = new Location(2, 0);
		Container container = new Container(Game.itemType.BOX, containerLocation);
		container.addItem(keyInventoryItem);
		testGame.rooms.get(0).board.getSquare(containerLocation).setContainer(container);;
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getContainer());
		System.out.println("Item search " + testGame.getPlayer(201).searchMoves);
		assertEquals(1, testGame.getPlayer(201).searchMoves.size());
		assertEquals(true,testGame.getPlayer(201).searchMoves.keySet().contains(MovementDirection.RIGHT));		
	}	
	
	
	
	@Test
	public void playerPushMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		MovableItem box1 = new MovableItem(Game.itemType.BOX);
		Location boxLocation = new Location(2, 0);
//		Container container = new Container(Game.itemType.BOX, containerLocation);
//		container.addItem(keyInventoryItem);
		testGame.rooms.get(0).board.getSquare(boxLocation).setMovableItem(box1);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getContainer());
		System.out.println("Item search " + testGame.getPlayer(201).pushMoves);
		assertEquals(1, testGame.getPlayer(201).pushMoves.size());
		assertEquals(true,testGame.getPlayer(201).pushMoves.keySet().contains(MovementDirection.RIGHT));		
	}
	
	@Test
	public void playerPullMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		MovableItem box1 = new MovableItem(Game.itemType.BOX);
		Location boxLocation = new Location(2, 0);
//		Container container = new Container(Game.itemType.BOX, containerLocation);
//		container.addItem(keyInventoryItem);
		testGame.rooms.get(0).board.getSquare(boxLocation).setMovableItem(box1);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getMovableItem());
		System.out.println("Item search " + testGame.getPlayer(201).pullMoves);
		assertEquals(1, testGame.getPlayer(201).pullMoves.size());
		assertEquals(true,testGame.getPlayer(201).pullMoves.keySet().contains(MovementDirection.RIGHT));		
	}
	
	
	@Test
	public void testPullMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		MovableItem box1 = new MovableItem(Game.itemType.BOX);
		Location boxLocation = new Location(2, 0);
//		Container container = new Container(Game.itemType.BOX, containerLocation);
//		container.addItem(keyInventoryItem);
		testGame.rooms.get(0).board.getSquare(boxLocation).setMovableItem(box1);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getMovableItem());
		System.out.println("Item search " + testGame.getPlayer(201).pullMoves);
		testGame.rooms.get(0).pullItem(testGame.getPlayer(201), MovementDirection.RIGHT, testGame.rooms.get(0).board.getSquare(boxLocation));
		System.out.println(box1.getLocation().getX());
		assertEquals(1, testGame.rooms.get(0).board.getSquare(box1.getLocation()).getLocation().getX());
		assertNotEquals(1, testGame.getPlayer(201).pullMoves.size());
		assertNotEquals(true,testGame.getPlayer(201).pullMoves.keySet().contains(MovementDirection.RIGHT));		
	}
	
	
	@Test
	public void testPushMove(){
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(10));
		testGame.addPlayer(201);
		MovableItem box1 = new MovableItem(Game.itemType.BOX);
		Location boxLocation = new Location(2, 0);
//		Container container = new Container(Game.itemType.BOX, containerLocation);
//		container.addItem(keyInventoryItem);
		testGame.rooms.get(0).board.getSquare(boxLocation).setMovableItem(box1);
		Location currentLocation = testGame.getPlayer(201).getLocation();
		testGame.rooms.get(0).MovePlayer(testGame.getPlayer(201), MovementDirection.RIGHT);
		System.out.println(testGame.rooms.get(0).board.getSquare(currentLocation).getMovableItem());
		System.out.println("Item search " + testGame.getPlayer(201).pullMoves);
		testGame.rooms.get(0).pullItem(testGame.getPlayer(201), MovementDirection.RIGHT, testGame.rooms.get(0).board.getSquare(boxLocation));
		System.out.println(box1.getLocation().getX());
		assertEquals(1, testGame.rooms.get(0).board.getSquare(box1.getLocation()).getLocation().getX());
		assertNotEquals(1, testGame.getPlayer(201).pullMoves.size());
		assertNotEquals(true,testGame.getPlayer(201).pullMoves.keySet().contains(MovementDirection.RIGHT));		
	}
	
	@Test
	public void doorTransition(){
		
		Game testGame = new Game();
		testGame.rooms.add(new PuzzleRoom(9));
		testGame.players.add(new Player(201, testGame.rooms.get(0)));
		assertTrue(testGame.players.get(0).getLocation().getX() == 0);
		assertTrue(testGame.players.get(0).getLocation().getY() == 0);
		testGame.rooms.get(0).updatePlayerMoves(testGame.players.get(0));
		Door doorAdd = new Door(new Location(4, 4));
//		System.out.println(doorAdd.getCharacter());
		testGame.rooms.get(0).addDoor(doorAdd);
//		System.out.println(testGame.rooms.get(0).door);
//		System.out.println(testGame.rooms.get(0).door);
		testGame.rooms.get(0).unlockDoor();
		testGame.rooms.get(0).door.setUnlocked(true);
//		System.out.println("CHaracyer" + testGame.rooms.get(0).door.getCharacter());
		testGame.setDestinationRooms();
		Room currentRoom = testGame.getPlayer(201).getRoom();
		testGame.rooms.get(1).addDoor(new Door(new Location(0, 1)));
//		System.out.println(testGame.getPlayer(201).getRoom());
		testGame.rooms.get(0).goThroughDoor(testGame.getPlayer(201));;
		Room newRoom = testGame.getPlayer(201).getRoom();
//		System.out.println(testGame.getPlayer(201).getRoom());
		assertNotEquals(currentRoom, newRoom);	
	}
	
	
	
	
//	
//	@Test
//	public void test
//	
	
	
}