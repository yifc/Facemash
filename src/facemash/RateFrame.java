package facemash;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RateFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	//	private static int WIDTH = 400, HEIGHT = 200;
//	private static String TITLE = "Girls Ranking";
	private JButton b1, b2;
	Competitor compA, compB;
	static int result = 0;
	public static boolean finished;
	
	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// use a lock to notify the main thread when a button is pressed
			synchronized(EloRanker.class) {
				String winner = ((JButton)e.getSource()).getText();
				if(winner.equals(compA.getName()))
					result = 1;
				else
					result = 2;
				finished = true;
				EloRanker.class.notifyAll();
			}
		}
	};

	/**
	 * Construct a new Frame, adding two buttons with the competitors' names on it
	 * @param competitorA
	 * @param competitorB
	 */
	public RateFrame(Competitor competitorA, Competitor competitorB) {
		finished = false;
		
		compA = competitorA;
		compB = competitorB;
		setLayout(new GridLayout(1, 2)); 
		Image scaledImageA = new ImageIcon(compA.getImageLink()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
		b1 = new JButton(compA.getName(), new ImageIcon(scaledImageA));
		// set the text to be right below the icon
		b1.setVerticalTextPosition(SwingConstants.BOTTOM);
		b1.setHorizontalTextPosition(SwingConstants.CENTER);
		
		Image scaledImageB = new ImageIcon(compB.getImageLink()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
		b2 = new JButton(compB.getName(), new ImageIcon(scaledImageB));
		b2.setVerticalTextPosition(SwingConstants.BOTTOM);
		b2.setHorizontalTextPosition(SwingConstants.CENTER);
		
		b1.addActionListener(listener);
		b2.addActionListener(listener);
	    add(b1); 
	    add(b2); 
	}
}
