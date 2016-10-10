package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.Game;
import game.Game.viewDirection;
import render.GameRenderer;

public class RenderPanel extends JPanel implements ActionListener {

	// buttons
	private JButton rotateL; // rotate left
	private JButton rotateR; // rotate right

	private Game theGame;
	private GameRenderer gam;

	public RenderPanel(Game g) {

		theGame = g;
		Dimension size = new Dimension(1200, 400);
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.red));
		gam = new GameRenderer(g);

		setLayout(null);

		rotateL = new JButton("Rotate Left");
		rotateL.setBounds(605, 382, 600, 25);
		rotateL.addActionListener(this);
		add(rotateL);

		rotateR = new JButton("Rotate Right");
		rotateR.setBounds(5, 382, 595, 25);
		rotateR.addActionListener(this);
		add(rotateR);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == rotateL) {
			System.out.println("Rotate left");
			if (theGame.getDirection() == Game.viewDirection.NORTH){
				theGame.shiftView(viewDirection.WEST);
			}
			else if (theGame.getDirection() == Game.viewDirection.WEST){
				theGame.shiftView(viewDirection.SOUTH);
			}
			else if (theGame.getDirection() == Game.viewDirection.SOUTH){
				theGame.shiftView(viewDirection.EAST);
			}
			else if (theGame.getDirection() == Game.viewDirection.EAST){
				theGame.shiftView(viewDirection.NORTH);
			}
			gam.rotateCW();
			gam.rotateCW();
			gam.rotateCW();
			repaint();

		}
		if (src == rotateR) {
			System.out.println("Rotate right");
			if (theGame.getDirection() == Game.viewDirection.NORTH){
				theGame.shiftView(viewDirection.EAST);
			}
			else if (theGame.getDirection() == Game.viewDirection.EAST){
				theGame.shiftView(viewDirection.SOUTH);
			}
			else if (theGame.getDirection() == Game.viewDirection.SOUTH){
				theGame.shiftView(viewDirection.WEST);
			}
			else if (theGame.getDirection() == Game.viewDirection.WEST){
				theGame.shiftView(viewDirection.NORTH);
			}
			gam.rotateCW();

			repaint();

		}
	}

	@Override
	public void paintComponent(Graphics gr) {
		gr.clearRect(0, 0, 1400, 600);
		gam.render(gr);

	}

}
