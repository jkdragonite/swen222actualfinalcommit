package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 *HelpFrame is a frame that displays a message that is somewhat helpful but not 
 *too helpful as the idea is to not actually help that much
 * 
 * @author Josh 300278912
 *
 */
public class HelpFrame extends JFrame {

	static String content = // The string they will pop up for the help window
			"This is the help manu\n" + "\n"
					+ "You must try to escape the existential dread\n"
					+ "..........Maybe\n" + "\n" + "\n" + "but i doubt it\n" + "\n" + "try collect the correct items and unlock doors\n" + "\n" + "good luck\n" + "\n"
					+ "\n" + "you will need it\n" + "\n" + "scrub\n" + "\n";

	/**
	 * Constructor for HelpFrame 
	 * it sets the size ,layout and the text layout
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