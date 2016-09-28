package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class RenderPanel extends JPanel implements ActionListener{

	
	public RenderPanel(){
	Dimension size = new Dimension(1600, 400);
	setPreferredSize(size);
	setBorder(BorderFactory.createLineBorder(Color.red));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paintComponent(Graphics gr){
		gr.setColor(Color.CYAN);
		gr.fillRect(50, 50, 1500, 300);
		gr.setColor(Color.black);
	}
	
}
