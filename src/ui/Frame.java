package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import game.Game;
import render.GameRenderer;

public class Frame extends JFrame implements  ActionListener {

	//unique player ID
	public static int playerID;
	
	// panel fields
	
	public InventoryPanel ip;
	public RenderPanel rp;

	// menu bar stuff
	JMenuBar menuBar;
	JMenu menu;
	JMenu help;
	JMenu save;
	
	// the game
	private Game theGame;
   
	
	
    public Frame(String title, KeyListener kl,Game g,int UID) {
    	super(title);
    	theGame = g;
    	playerID = UID;
    	    	
		// Menu bar things
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Creating the Jmenus
		menu = new JMenu("Game options");
		help = new JMenu("Help options");
		save = new JMenu("Save options");

		JMenuItem start = new JMenuItem("Start game");
		start.setActionCommand("startGame");
		JMenuItem end = new JMenuItem("Exit");
		end.setActionCommand("endGame");
		end.setToolTipText("are you sure you really wanna end");
		JMenuItem showHelp = new JMenuItem("show help");
		showHelp.setActionCommand("showHelp");
		JMenuItem saveGame = new JMenuItem("Save game");
		JMenuItem loadGame = new JMenuItem("Load game");
		
		MenuItemListener menuItemListener = new MenuItemListener();

		start.addActionListener(menuItemListener);
		end.addActionListener(menuItemListener);
		showHelp.addActionListener(menuItemListener);

		menu.add(start);
		menu.add(end);
		help.add(showHelp);
		save.add(saveGame);
		save.add(loadGame);
		menuBar.add(menu);
		menuBar.add(help);
		menuBar.add(save);

		setLayout(new BorderLayout());

		


		ip = new InventoryPanel(theGame,playerID);
		add(ip, BorderLayout.SOUTH);
		
		rp = new RenderPanel(theGame, playerID);
		add(rp, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		addKeyListener(kl);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		//new Frame("testFrame", new KeyListener(););
	}

	/*
	 * Class to make the menu actions happen
	 */
	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("startGame")) {
				System.out.println("game started");
				// start game method
			}
			if (e.getActionCommand().equals("endGame")) {
				System.out.println("game ended");
				System.exit(0);
			}
			if (e.getActionCommand().equals("showHelp")) {
			//make a new help frame pop up
			new HelpFrame();
				
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
