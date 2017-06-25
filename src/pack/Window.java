package pack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

public class Window extends JFrame{
	public Window() {
		setMinimumSize(new Dimension(1000, 700));
		setTitle("WekaApp");

		MainPanel panel = null;
		try {
			panel = new MainPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	

}
