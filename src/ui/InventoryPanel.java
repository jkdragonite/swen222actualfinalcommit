package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import game.InventoryItem;

public class InventoryPanel extends JPanel implements ActionListener {

	//images for the differnt items
	private static Image questIcon;
	private static Image bookIcon;
	private static Image boxIcon;
	
	
	private InventoryItem slot1=null;
	private InventoryItem slot2=null;
	private InventoryItem slot3=null;
	private InventoryItem slot4=null;
	
	
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
	private JButton trade;

	private JTextArea itemInfo;
	private String itemText = "Item info";

	private int selected;

	/**
	 * Constructor for InventoryPanel
	 */
	public InventoryPanel() {
		int utilButtonHeight = 30;
		int untiButtonWidth = 240;
		
		try{
			questIcon = ImageIO.read(new File("../existential-dread/images/QuestItemIcon.png"));
			boxIcon = ImageIO.read(new File("../existential-dread/images/BoxIcon.png"));
			bookIcon = ImageIO.read(new File("../existential-dread/images/BookIcon.png"));
		}catch (IOException e) {
			System.out.println("file error loading images from inventoy panel" + e.getMessage());
		}

		setLayout(null);

		Dimension size = new Dimension(1200, 240);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.red));

		itemInfo = new JTextArea();
		itemInfo.setText(itemText);
		itemInfo.setBounds(960, 10, 240, 40);

		this.add(itemInfo);

		// setting the dimension used for the buttons

		item1 = new JButton("Item 1");
		item1.addActionListener(this);
		item1.setBounds(30, 200, 180, 30);
		add(item1);

		item2 = new JButton("Item 2");
		item2.addActionListener(this);
		item2.setBounds(270, 200, 180, 30);
		this.add(item2);

		item3 = new JButton("Item 3");
		item3.addActionListener(this);
		item3.setBounds(510, 200, 180, 30);
		add(item3);

		item4 = new JButton("Item 4");
		item4.addActionListener(this);
		item4.setBounds(750, 200, 180, 30);
		add(item4);

		pickup = new JButton("pickup");
		pickup.addActionListener(this);
		pickup.setBounds(970, 60, untiButtonWidth, utilButtonHeight);
		add(pickup);

		use = new JButton("Use");
		use.addActionListener(this);
		use.setBounds(970, 90, untiButtonWidth, utilButtonHeight);
		add(use);

		push = new JButton("push");
		push.addActionListener(this);
		push.setBounds(970, 120, untiButtonWidth, utilButtonHeight);
		add(push);

		pull = new JButton("pull");
		pull.addActionListener(this);
		pull.setBounds(970, 150, untiButtonWidth, utilButtonHeight);
		add(pull);

		drop = new JButton("drop");
		drop.addActionListener(this);
		drop.setBounds(970, 180, untiButtonWidth, utilButtonHeight);
		add(drop);

		trade = new JButton("trade???");
		trade.addActionListener(this);
		trade.setBounds(970, 210, untiButtonWidth, utilButtonHeight);
		add(trade);

	}

	/**
	 * Constructor for InventoryPanel
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
			itemInfo.setText( "lance of fire - used to burn things");//string will be item.getString
			repaint();
		}
		if (src == item2) {
			System.out.println("Item 2 selected");
			// Disable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(false);
			item3.setEnabled(true);
			item4.setEnabled(true);
			selected = 2;
			itemInfo.setText( "lance of Ice - used to freeze things");
			repaint();
		}
		if (src == item3) {
			System.out.println("Item 3 selected");
			// Disable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(true);
			item3.setEnabled(false);
			item4.setEnabled(true);
			itemInfo.setText("lance of Thunder - used to zap things");
			selected = 3;
			repaint();

		}
		if (src == item4) {
			System.out.println("Item 4 selected");
			// diable button && enable others
			item1.setEnabled(true);
			item2.setEnabled(true);
			item3.setEnabled(true);
			item4.setEnabled(false);
			selected = 4;
			itemInfo.setText("A box - just a box");
			repaint();

		}
		if (src == use) {
			System.out.println("use");
			// use method

		}
		if (src == push) {
			System.out.println("push ");
			// push method

		}
		if (src == pull) {
			System.out.println("pull");
			// pull method

		}
		if (src == drop) {
			System.out.println("drop");
			// drop method
		}
		if (src == pickup) {
			System.out.println("pick up the thing");
			// pickup method
		}
		if (src == trade) {
			System.out.println("try to trade....maybe.....if you are lucky");
			// drop method

		}
	}

	/**
	 * the draw method for drawing the items in the players inventory
	 */
	@Override
	public void paintComponent(Graphics gr) {
		int imageSize = 180;
		int imageY = 10;
		super.paintComponent(gr);
		gr.setColor(Color.red);
		gr.fillRect(30, imageY, imageSize, imageSize);
		gr.fillRect(270, imageY, imageSize, imageSize);
		gr.fillRect(510, imageY, imageSize, imageSize);
		gr.fillRect(750, imageY, imageSize, imageSize);
		gr.setColor(Color.black);
		// itemInfo.update(gr);
		if (selected == 1) { // if there is an item in slot 1
			gr.setColor(Color.yellow);
			gr.fillRect(25, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
			gr.drawImage(questIcon,30, imageY, null, null);
			// draw item image
		}
		if (selected == 2) { // if there is an item in slot 1
			gr.setColor(Color.yellow);
			gr.fillRect(265, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
			gr.drawImage(bookIcon,270, imageY, null, null);
			// draw item image
		}
		if (selected == 3) { // if there is an item in slot 1
			gr.setColor(Color.yellow);
			gr.fillRect(505, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
			gr.drawImage(boxIcon,510, imageY, null, null);
			// draw item image
		}
		if (selected == 4) { // if there is an item in slot 1
			gr.setColor(Color.yellow);
			gr.fillRect(745, imageY - 5, imageSize + 10, imageSize + 10);
			gr.setColor(Color.black);
			gr.drawImage(boxIcon,750, imageY, null, null);
			// draw item image
		}
	}
}
