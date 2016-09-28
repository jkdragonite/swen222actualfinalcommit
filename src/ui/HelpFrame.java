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
					+ "..........Maybe\n";

	/**
	 * Constructor for HelpFrame
	 */
	public HelpFrame() {

		Dimension size = new Dimension(450, 300);
		setPreferredSize(size);

		JPanel hp = new JPanel();
		hp.setBorder(BorderFactory.createLineBorder(Color.yellow));

		setLayout(new BorderLayout());
		hp.add(new JTextArea(content));
		add(hp, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);

	}
}