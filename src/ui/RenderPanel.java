package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import game.Game;
import render.GameRenderer;

public class RenderPanel extends JPanel implements ActionListener{

	
	private Game theGame;
	private GameRenderer gam;
	
	public RenderPanel(Game g){
	theGame = g;
	Dimension size = new Dimension(1200, 400);
	setPreferredSize(size);
	setBorder(BorderFactory.createLineBorder(Color.red));
	gam = new GameRenderer (g);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent(Graphics gr){
		gam.render(gr);
	
	}
	
}
