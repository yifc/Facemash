package facemash;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SwingConsole {	
	public static void run(final JFrame f, final int width, final int height) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
		    	f.setSize(width, height);
		    	f.setLocationRelativeTo(null);	// only after setting the set, the location can be correctly set.
		    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	f.setTitle("Girls Ranking");
		    	f.setVisible(true);
			}
		});
	}
}