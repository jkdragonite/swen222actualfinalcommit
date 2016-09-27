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

	/**
	 * Constructor for InventoryPanel
	 */
	public InventoryPanel() {
		// frame = p;

		Dimension size = new Dimension(1600, 400);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.red));

		// setting the dimension used for the buttons
		Dimension button = new Dimension(395, 50);

		item1 = new JButton("Item slot 1 options");
		item1.setPreferredSize(button);
		item1.addActionListener(this);
		add(item1, BorderLayout.NORTH);

		item2 = new JButton("Item 2 options");
		item2.setPreferredSize(button);
		item2.addActionListener(this);
		add(item2, BorderLayout.NORTH);

		item3 = new JButton("Item 3 options");
		item3.setPreferredSize(button);
		item3.addActionListener(this);
		add(item3, BorderLayout.NORTH);

		item4 = new JButton("Item 4 options");
		item4.setPreferredSize(button);
		item4.addActionListener(this);
		add(item4, BorderLayout.NORTH);

	}
	
	
	
	/**
	 * Constructor for InventoryPanel
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		Object src = evt.getSource();
		if (src == item1) {
			System.out.println("Item 1 pop up go");
			// pop up 1
		}
		if (src == item2) {
			System.out.println("Item 2 pop up go");
			// pop up 2
		}
		if (src == item3) {
			System.out.println("Item 3 pop up go");
			// pop up 3

		}
		if (src == item4) {
			System.out.println("Item 4 pop up go");
			// pop up 4

		}

	}

	/**
	 * the draw method for drawing the items in the players inventory
	 */
	@Override
	public void paintComponent(Graphics gr) {
		int imageSize = 300;
		int imageY = 60;
		super.paintComponent(gr);
		if (1 == 1) { // if there is an item in slot 1
			gr.fillRect(50, imageY, imageSize, imageSize);
			// draw item image
		}
		if (2 == 2) { // if there is an item in slot 1
			gr.fillRect(450, imageY, imageSize, imageSize);
			// draw item image
		}
		if (3 == 3) { // if there is an item in slot 1
			gr.fillRect(850, imageY, imageSize, imageSize);
			// draw item image
		}
		if (4 == 4) { // if there is an item in slot 1
			gr.fillRect(1250, imageY, imageSize, imageSize);
			// draw item image
		}
	}
}
