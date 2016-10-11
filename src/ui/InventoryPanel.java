package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import game.Game;
import game.InventoryItem;
import game.Location;
import game.Player;
import game.Game.viewDirection;
import game.Room.MovementDirection;

public class InventoryPanel extends JPanel implements ActionListener, KeyListener {

	// images for the differnt items
	private static Image questIcon;
	private static Image bookIcon;
	private static Image boxIcon;
	private static Image keyIcon;

	// inventory slots
	private InventoryItem slot1 = null;
	private InventoryItem slot2 = null;
	private InventoryItem slot3 = null;
	private InventoryItem slot4 = null;

	// the game
	private Game theGame;

	// the players ID
	private final int playerID;

	// the render panel ref
	private RenderPanel render;

	// private final Player thePlayer;

	// buttons
	private JButton item1;
	private JButton item2;
	private JButton item3;
	private JButton item4;
	private JButton use;
	private JButton drop;
	private JButton pickup;
	private JButton push;
	private JButton pull;
	private JButton useDoor;
	private JButton up;
	private JButton down;
	private JButton left;
	private JButton right;

	// text area
	private JTextArea itemInfo;
	private String itemText = "Item info";
	private JTextArea handyInfo;

	// int selceted starts at slot 1
	private int selected = 1;

	/**
	 * Constructor for InventoryPanel
	 */
	public InventoryPanel(Game g, int userId, RenderPanel rend) {

		slot4 = new InventoryItem(Game.itemType.BOOK, new Location(0, 0), "A book for nerds", 66);// testing
		theGame = g;
		playerID = userId;
		render = rend;

		int utilButtonHeight = 30;
		int untiButtonWidth = 220;

		try {
			questIcon = ImageIO.read(new File("../existential-dread/images/QuestItemIcon.png"));
			boxIcon = ImageIO.read(new File("../existential-dread/images/BoxIcon.png"));
			bookIcon = ImageIO.read(new File("../existential-dread/images/BookIcon.png"));
			keyIcon = ImageIO.read(new File("../existential-dread/images/KeyIcon.png"));
		} catch (IOException e) {
			System.out.println("file error loading images from inventoy panel" + e.getMessage());
		}

		setLayout(null);

		Dimension size = new Dimension(1200, 240);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.red));

		// Text areas
		itemInfo = new JTextArea();
		itemInfo.setText(itemText);
		itemInfo.setBounds(980, 10, 240, 20);
		handyInfo = new JTextArea();
		handyInfo.setText("Handy dandy Information");
		handyInfo.setBounds(980, 210, 240, 20);
		this.add(itemInfo);
		this.add(handyInfo);

		// setting the dimension used for the buttons and adding them to the
		// inventoerty panel
		item1 = new JButton("Item 1");
		item1.addActionListener(this);
		item1.setBounds(10, 200, 180, 25);
		add(item1);

		item2 = new JButton("Item 2");
		item2.addActionListener(this);
		item2.setBounds(200, 200, 180, 30);
		this.add(item2);

		item3 = new JButton("Item 3");
		item3.addActionListener(this);
		item3.setBounds(390, 200, 180, 30);
		add(item3);

		item4 = new JButton("Item 4");
		item4.addActionListener(this);
		item4.setBounds(580, 200, 180, 30);
		add(item4);

		pickup = new JButton("Pickup");
		pickup.addActionListener(this);
		pickup.setBounds(980, 30, untiButtonWidth, utilButtonHeight);
		add(pickup);

		use = new JButton("Use");
		use.addActionListener(this);
		use.setBounds(980, 60, untiButtonWidth, utilButtonHeight);
		add(use);

		push = new JButton("Push");
		push.addActionListener(this);
		push.setBounds(980, 90, untiButtonWidth, utilButtonHeight);
		add(push);

		pull = new JButton("Pull");
		pull.addActionListener(this);
		pull.setBounds(980, 120, untiButtonWidth, utilButtonHeight);
		add(pull);

		drop = new JButton("Drop Item");
		drop.addActionListener(this);
		drop.setBounds(980, 150, untiButtonWidth, utilButtonHeight);
		add(drop);

		useDoor = new JButton("Use Door");
		useDoor.addActionListener(this);
		useDoor.setBounds(980, 180, untiButtonWidth, utilButtonHeight);
		add(useDoor);

		up = new JButton("Move forward");
		up.addActionListener(this);
		up.setBounds(770, 10, 200, 70);
		add(up);

		left = new JButton("Move left");
		left.addActionListener(this);
		left.setBounds(770, 80, 100, 80);
		add(left);

		right = new JButton("Move right");
		right.addActionListener(this);
		right.setBounds(870, 80, 100, 80);
		add(right);

		down = new JButton("Move back");
		down.addActionListener(this);
		down.setBounds(770, 160, 200, 70);
		add(down);

	}

	/**
	 * Constructor for InventoryPanel
	 * 
	 * Creates all the move buttons, Utility buttons and the item buttons. has
	 * places for the inventory item icons to be drawn, will default to a black
	 * square if there is no item in the inventory slot.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		Object src = evt.getSource();
		if (src == item1) {
			System.out.println("Item 1 selected");
			// Disable button && enable others
			item1.setEnabled(false);
			item2.setEnabled(true);
			item3.setEnabled(true);
			item4.setEnabled(true);
			selected = 1;
			if (slot1 != null) {
				itemInfo.setText(slot1.getType().toString());
			} else if (slot1 == null) {
				itemInfo.setText("Empty inventory slot");
			}
			repaint();
		} else if (src == item2) {
			System.out.println("Item 2 selected");
			// Disable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(false);
			item3.setEnabled(true);
			item4.setEnabled(true);
			selected = 2;
			if (slot2 != null) {
				itemInfo.setText(slot2.getType().toString());
			} else if (slot2 == null) {
				itemInfo.setText("Empty inventory slot");
			}
			repaint();
		} else if (src == item3) {
			slot3 = new InventoryItem(Game.itemType.KEY, new Location(4, 0), "The KeyBalde", 556); // testing
			System.out.println("Item 3 selected");
			// Disable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(true);
			item3.setEnabled(false);
			item4.setEnabled(true);
			if (slot3 != null) {
				itemInfo.setText(slot3.getType().toString());
			} else if (slot3 == null) {
				itemInfo.setText("Empty inventory slot");
			}
			selected = 3;
			repaint();

		} else if (src == item4) {
			System.out.println("Item 4 selected");
			// diable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(true);
			item3.setEnabled(true);
			item4.setEnabled(false);
			selected = 4;
			if (slot4 != null) {
				itemInfo.setText(slot4.getType().toString() + "-" + slot4.getItemName());
			} else if (slot4 == null) {
				itemInfo.setText("Empty item slot");
			}
			repaint();

		}

		else if (src == use) {
			System.out.println("use");
			// use method
			Player currentPlayer = theGame.getPlayer(playerID);
			currentPlayer.getRoom().useItem(currentPlayer);
		}

		else if (src == up) {
			System.out.println("UP");
			// use method
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.getView() == viewDirection.NORTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.UP)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.UP);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.SOUTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.DOWN)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.DOWN);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.EAST) {
				if (currentPlayer.moves.containsKey(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.RIGHT);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.WEST) {
				if (currentPlayer.moves.containsKey(MovementDirection.LEFT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.LEFT);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
		} else if (src == left) {
			System.out.println("LEFT");
			// use method
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.getView() == viewDirection.NORTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.LEFT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.LEFT);
					render.repaint();
				} else {
					handyInfo.setText("Move left failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.SOUTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.RIGHT);
					render.repaint();
				} else {
					handyInfo.setText("Move left failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.EAST) {
				if (currentPlayer.moves.containsKey(MovementDirection.UP)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.UP);
					render.repaint();
				} else {
					handyInfo.setText("Move left failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.WEST) {
				if (currentPlayer.moves.containsKey(MovementDirection.DOWN)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.DOWN);
					render.repaint();
				} else {
					handyInfo.setText("Move left failed");
				}
				repaint();
			}
		} else if (src == right) {
			System.out.println("RIGHT");
			// use method
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.getView() == viewDirection.NORTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.RIGHT);
					render.repaint();
				} else {
					handyInfo.setText("Move right failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.SOUTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.LEFT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.LEFT);
					render.repaint();
				} else {
					handyInfo.setText("Move right failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.EAST) {
				if (currentPlayer.moves.containsKey(MovementDirection.DOWN)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.DOWN);
					render.repaint();
				} else {
					handyInfo.setText("Move right failed");
				}
				repaint();
			}
			if (currentPlayer.getView() == viewDirection.WEST) {
				if (currentPlayer.moves.containsKey(MovementDirection.UP)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.UP);
					render.repaint();
				} else {
					handyInfo.setText("Move right failed");
				}
				repaint();
			}
		} else if (src == down) {
			System.out.println("DOWN");
			// use method
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.getView() == viewDirection.NORTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.DOWN)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.DOWN);
					render.repaint();
				} else {
					handyInfo.setText("Move down failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.SOUTH) {
				if (currentPlayer.moves.containsKey(MovementDirection.UP)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.UP);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.EAST) {
				if (currentPlayer.moves.containsKey(MovementDirection.LEFT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.LEFT);
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}
			if (currentPlayer.getView() == viewDirection.WEST) {
				if (currentPlayer.moves.containsKey(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.RIGHT);
					handyInfo.setText("Moved Right");
					render.repaint();
				} else {
					handyInfo.setText("Move up failed");
				}
			}

		} else if (src == push) {
			System.out.println("push ");
			// push method
			Player currentPlayer = theGame.getPlayer(playerID);
			// takes a keyset in player move and checks for each direction in
			// order of
			// preference, executes action using the direction found if
			// applicable,
			// no action occurs if the keyset is 0
			if (currentPlayer.pushMoves.keySet().size() > 0) {
				if (currentPlayer.pushMoves.keySet().contains(MovementDirection.UP)) {
					currentPlayer.getRoom().pushItem(currentPlayer, MovementDirection.UP,
							currentPlayer.pushMoves.get(MovementDirection.UP));
				} else if (currentPlayer.pushMoves.keySet().contains(MovementDirection.DOWN)) {
					currentPlayer.getRoom().pushItem(currentPlayer, MovementDirection.DOWN,
							currentPlayer.pushMoves.get(MovementDirection.DOWN));
				} else if (currentPlayer.pushMoves.keySet().contains(MovementDirection.LEFT)) {
					currentPlayer.getRoom().pushItem(currentPlayer, MovementDirection.LEFT,
							currentPlayer.pushMoves.get(MovementDirection.LEFT));
				} else if (currentPlayer.pushMoves.keySet().contains(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().pushItem(currentPlayer, MovementDirection.RIGHT,
							currentPlayer.pushMoves.get(MovementDirection.RIGHT));
				} else {
					handyInfo.setText("Pushed nothing");
				}
			}

		} else if (src == pull) {
			System.out.println("pull ");
			Player currentPlayer = theGame.getPlayer(playerID);
			// pull method
			// takes a keyset in player move and checks for each direction in
			// order of
			// preference, executes action using the direction found if
			// applicable,
			// no action occurs if the keyset is 0
			if (currentPlayer.pullMoves.keySet().size() > 0) {
				if (currentPlayer.pullMoves.keySet().contains(MovementDirection.UP)) {
					currentPlayer.getRoom().pullItem(currentPlayer, MovementDirection.UP,
							currentPlayer.pullMoves.get(MovementDirection.UP));
				} else if (currentPlayer.pullMoves.keySet().contains(MovementDirection.DOWN)) {
					currentPlayer.getRoom().pullItem(currentPlayer, MovementDirection.DOWN,
							currentPlayer.pullMoves.get(MovementDirection.DOWN));
				} else if (currentPlayer.pullMoves.keySet().contains(MovementDirection.LEFT)) {
					currentPlayer.getRoom().pullItem(currentPlayer, MovementDirection.LEFT,
							currentPlayer.pullMoves.get(MovementDirection.LEFT));
				} else if (currentPlayer.pullMoves.keySet().contains(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().pullItem(currentPlayer, MovementDirection.RIGHT,
							currentPlayer.pullMoves.get(MovementDirection.RIGHT));
				} else {
					handyInfo.setText("Pulled nothing");
				}
			}

		} else if (src == drop) {
			System.out.println("drop");
			// drop method
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.getRoom().inventoryItems.size() >= selected) {
				if (currentPlayer.getRoom().inventoryItems.size() >= selected) {
					currentPlayer.getRoom().dropItem(currentPlayer, selected - 1);
					handyInfo.setText("Item dropped");
					updateItemSlots();
				}
			} else {
				handyInfo.setText("Drop failed");
				updateItemSlots();
			}
		} else if (src == pickup) {
			System.out.println("pick up the thing");
			// pickup method
			// takes a keyset in player move and checks for each direction in
			// order of
			// preference, executes action using the direction found if
			// applicable,
			// no action occurs if the keyset is 0
			// will try pickup all items possible
			int amountOfItems = 0;
			Player currentPlayer = theGame.getPlayer(playerID);

			if (currentPlayer.itemPickups.keySet().size() > 0) {
				if (currentPlayer.itemPickups.keySet().contains(MovementDirection.UP)) {
					currentPlayer.getRoom().pickupItem(currentPlayer,
							currentPlayer.itemPickups.get(MovementDirection.UP));
					amountOfItems++;
				}
				if (currentPlayer.itemPickups.keySet().contains(MovementDirection.DOWN)) {
					currentPlayer.getRoom().pickupItem(currentPlayer,
							currentPlayer.itemPickups.get(MovementDirection.DOWN));
					amountOfItems++;
				}
				if (currentPlayer.itemPickups.keySet().contains(MovementDirection.LEFT)) {
					currentPlayer.getRoom().pickupItem(currentPlayer,
							currentPlayer.itemPickups.get(MovementDirection.LEFT));
					amountOfItems++;
				}
				if (currentPlayer.itemPickups.keySet().contains(MovementDirection.RIGHT)) {
					currentPlayer.getRoom().pickupItem(currentPlayer,
							currentPlayer.itemPickups.get(MovementDirection.RIGHT));
					amountOfItems++;
				}
				if (currentPlayer.itemPickups.keySet().contains(null)) {
					currentPlayer.getRoom().pickupItem(currentPlayer, currentPlayer.itemPickups.get(null));
					amountOfItems++;
				}
				updateItemSlots();
				if (amountOfItems == 1) {
					handyInfo.setText("1 item picked up");
				} else {
					handyInfo.setText(amountOfItems + " items picked up");
				}
			} else {
				handyInfo.setText("No items picked up");
			}
		} else if (src == useDoor) {
			System.out.println("Use the door");
			Player currentPlayer = theGame.getPlayer(playerID);
			if (currentPlayer.canGoThroughDoor) {
				currentPlayer.getRoom().goThroughDoor(currentPlayer);
				handyInfo.setText("Door sucess");
			} else {
				handyInfo.setText("Door failed");
			}
		}
	}

	/**
	 * Updates the item slots in the inventory panel by getting the current
	 * player and checking each of their inventory and updating it to the item
	 * slots.
	 */
	public void updateItemSlots() {
		Player currentPlayer = theGame.getPlayer(playerID);
		if (currentPlayer.getInventory().size() == 1) {
			slot1 = currentPlayer.getInventory().get(0);
		} else
			slot1 = null;
		if (currentPlayer.getInventory().size() == 2) {
			slot2 = currentPlayer.getInventory().get(1);
		} else
			slot2 = null;
		if (currentPlayer.getInventory().size() == 3) {
			slot3 = currentPlayer.getInventory().get(2);
		} else
			slot3 = null;
		if (currentPlayer.getInventory().size() == 4) {
			slot4 = currentPlayer.getInventory().get(3);
		} else
			slot4 = null;
		repaint();
		render.repaint();
	}

	/**
	 * the draw method for drawing the items in the players inventory
	 */
	@Override
	public void paintComponent(Graphics gr) {
		int imageSize = 180;
		int imageY = 10;
		super.paintComponent(gr);

		// used to do the yellow square around the selected item.
		if (selected == 1) {
			gr.setColor(Color.yellow);
			gr.fillRect(5, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
		}
		if (selected == 2) {
			gr.setColor(Color.yellow);
			gr.fillRect(195, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
		}
		if (selected == 3) {
			gr.setColor(Color.yellow);
			gr.fillRect(385, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
		}
		if (selected == 4) { // if there is an item in slot 1
			gr.setColor(Color.yellow);
			gr.fillRect(575, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
		}

		if (slot1 == null) {
			gr.fillRect(10, imageY, imageSize, imageSize);
		} else if (slot1.getType() == Game.itemType.KEY) {
			gr.drawImage(keyIcon, 10, imageY, null, null);
		} else {
			gr.drawImage(bookIcon, 10, imageY, null, null);
		}

		if (slot2 == null) {
			// if (theGame.getPlayer(playerID).getInventory().get(0) == null)
			// {//needs more logic
			gr.fillRect(200, imageY, imageSize, imageSize);
		} else if (slot2.getType() == Game.itemType.KEY) {
			gr.drawImage(keyIcon, 200, imageY, null, null);
		} else {
			gr.drawImage(bookIcon, 200, imageY, null, null);
		}
		if (slot3 == null) {
			gr.fillRect(390, imageY, imageSize, imageSize);
		} else if (slot3.getType() == Game.itemType.KEY) {
			gr.drawImage(keyIcon, 390, imageY, null, null);
		} else {
			gr.drawImage(bookIcon, 390, imageY, null, null);
		}
		if (slot4 == null) {
			gr.fillRect(580, imageY, imageSize, imageSize);
		} else if (slot4.getType() == Game.itemType.KEY) {
			gr.drawImage(keyIcon, 580, imageY, null, null);
		} else {
			gr.drawImage(bookIcon, 580, imageY, null, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("A key was pressed");
		Player currentPlayer = theGame.getPlayer(playerID);

		if (e.getKeyCode() == KeyEvent.VK_W) {
			System.out.println("UP");
			// use method
			if (currentPlayer.moves.containsKey(MovementDirection.UP)) {
				currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.UP);
				render.repaint();
			} else {
				handyInfo.setText("Move up failed");
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			System.out.println("LEFT");
			// use method

			if (currentPlayer.moves.containsKey(MovementDirection.LEFT)) {
				currentPlayer.getRoom().MovePlayer(currentPlayer, MovementDirection.LEFT);
				handyInfo.setText("Moved left");
				render.repaint();
			} else {
				handyInfo.setText("Move left failed");
			}
			render.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
