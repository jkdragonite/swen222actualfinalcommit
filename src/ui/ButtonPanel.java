package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {

	// buttons
	private JButton rotateL; // rotate left
	private JButton rotateR; // rotate right
	private JButton rotateT; // rotate top

	/**
	 * Constructor for ButtonPanel
	 */
	public ButtonPanel() {

		Dimension size = new Dimension(1600, 100);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.blue));

		// setting the dimension used for the buttons
		Dimension button = new Dimension(140, 50);

		rotateL = new JButton("Rotate Left");
		rotateL.setPreferredSize(button);
		rotateL.addActionListener(this);
		add(rotateL, BorderLayout.NORTH);

		rotateR = new JButton("Rotate Right");
		rotateR.setPreferredSize(button);
		rotateR.addActionListener(this);
		add(rotateR, BorderLayout.NORTH);

		rotateT = new JButton("Rotate Top");
		rotateT.setPreferredSize(button);
		rotateT.addActionListener(this);
		add(rotateT, BorderLayout.NORTH);

	}

	/**
	 * Actions for each button and what they do for left right and top
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == rotateL) {
			System.out.println("left");
			// do the rotate left method
		}
		if (src == rotateR) {
			System.out.println("right");
			// do the rotate right method
		}
		if (src == rotateT) {
			System.out.println("top");
			// do the rotate top method

		}
	}
}