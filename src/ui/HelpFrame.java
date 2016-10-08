package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HelpFrame extends JFrame {

	static String content = // The string they will pop up for the help window
			"This is the help manu\n" + "\n"
					+ "There will be many helpful things in here that will help you escape the dread\n"
					+ "..........Maybe\n" + "\n" + "\n" + "but i doubt it\n" + "\n" + "\n" + "\n" + "good luck\n" + "\n"
					+ "\n" + "you will need it\n" + "\n" + "scrub\n" + "\n";

	/**
	 * Constructor for HelpFrame
	 */
	public HelpFrame() {

		Dimension size = new Dimension(600, 350);
		setPreferredSize(size);

		JPanel hp = new JPanel();
		hp.setBorder(BorderFactory.createLineBorder(Color.yellow));

		setLayout(new BorderLayout());
		hp.add(new JTextArea(content));
		add(hp, BorderLayout.CENTER);

	//	setDefaultCloseOperation(JFrame.);
		pack();
		setResizable(false);
		setVisible(true);

	}
}