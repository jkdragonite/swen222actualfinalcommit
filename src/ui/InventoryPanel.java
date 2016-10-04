package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel implements ActionListener {

	// buttons
	private JButton item1;
	private JButton item2;
	private JButton item3;
	private JButton item4;
	private JButton use;
	private JButton drop;
	private JButton thing1;
	private JButton thing2;
	
	
	private int selected;
	
	
	/**
	 * Constructor for InventoryPanel
	 */
	public InventoryPanel() {
		setLayout(null);

		Dimension size = new Dimension(1600, 320);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.red));

		// setting the dimension used for the buttons
		Dimension button = new Dimension(250, 30);

		item1 = new JButton("Item 1");
		item1.addActionListener(this);
		item1.setBounds(35, 280, 250, 30);
		add(item1);

		item2 = new JButton("Item 2");
		item2.addActionListener(this);
		item2.setBounds(355, 280, 250, 30);
		this.add(item2);

		item3 = new JButton("Item 3");
		item3.addActionListener(this);
		item3.setBounds(675, 280, 250, 30);
		add(item3);

		item4 = new JButton("Item 4");
		item4.addActionListener(this);
		item4.setBounds(995, 280, 250, 30);
		add(item4);

		use = new JButton("Use");
		use.addActionListener(this);
		use.setBounds(1300, 150, 250, 30);
		add(use);

		thing1 = new JButton("thing1");
		thing1.addActionListener(this);
		thing1.setBounds(1300, 200, 250, 30);
		add(thing1);

		thing2 = new JButton("thing2");
		thing2.addActionListener(this);
		thing2.setBounds(1300, 240, 250, 30);
		add(thing2);

		drop = new JButton("drop");
		drop.addActionListener(this);
		drop.setBounds(1300, 280, 250, 30);
		add(drop);
	}

	/**
	 * Constructor for InventoryPanel
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		Object src = evt.getSource();
		if (src == item1) {
			System.out.println("Item 1 selected");
			selected = 1;
			// pop up 1
		}
		if (src == item2) {
			System.out.println("Item 2 selected");
			// pop up 2
			selected = 2;
		}
		if (src == item3) {
			System.out.println("Item 3 selected");
			// pop up 3
			selected = 3;

		}
		if (src == item4) {
			System.out.println("Item 4 selected");
			//diable button
			item4.setEnabled(false);
			selected = 4;

		}
		if (src == use) {
			System.out.println("use");
			// use method

		}
		if (src == thing1) {
			System.out.println("thing1");
			// thing method

		}
		if (src == thing2) {
			System.out.println("thing2");
			//thing 2 method

		}
		if (src == drop) {
			System.out.println("drop");
			// drop method

		}

	}

	/**
	 * the draw method for drawing the items in the players inventory
	 */
	@Override
	public void paintComponent(Graphics gr) {
		int imageSize = 250;
		int imageY = 10;
		super.paintComponent(gr);
		gr.fillRect(35, imageY, imageSize, imageSize);
		gr.fillRect(355, imageY, imageSize, imageSize);
		gr.fillRect(675, imageY, imageSize, imageSize);
		gr.fillRect(995, imageY, imageSize, imageSize);
		if (selected == 1) { // if there is an item in slot 1
			gr.setColor(Color.cyan);
			gr.fillRect(30, imageY-5, imageSize+10, imageSize+10);
			gr.setColor(Color.black);
			gr.fillRect(35, imageY, imageSize, imageSize);
			// draw item image
		}
		if (2 == 2) { // if there is an item in slot 1
			
			// draw item image
		}
		if (3 == 3) { // if there is an item in slot 1
			
			// draw item image
		}
		if (4 == 4) { // if there is an item in slot 1
		
			// draw item image
		}
	}
}
