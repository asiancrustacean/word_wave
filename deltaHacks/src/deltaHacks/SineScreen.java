package deltaHacks;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class SineScreen extends JPanel {

	JLabel jlbLabel1, jlbLabel2, jlbLabel3;
	
	public SineScreen() {
		
		JFrame frame = new JFrame("WAVES TO WORDS");
		frame.addWindowListener(new WindowAdapter() {

			// Shows code to Add Window Listener
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		} );
		
		frame.setContentPane(this);
		frame.pack();
		frame.setForeground(Color.BLACK);
		frame.setVisible(true);
		
		ImageIcon icon = new ImageIcon("cooltext1922295886.png",
				"WAVES TO WORDS");
		jlbLabel1 = new JLabel("Hello", icon, JLabel.CENTER);
		jlbLabel1.setVerticalTextPosition(JLabel.BOTTOM);
		jlbLabel1.setHorizontalTextPosition(JLabel.CENTER);
		jlbLabel1.setSize(300,300);
		
		jlbLabel1.setBackground(Color.GRAY);
		add(jlbLabel1);
		
		
		setLayout(new GridLayout(3, 1));
		// 3 rows, 1 column Panel having Grid Layout
		setSize(800,800);
		setVisible(true);
	}
	
	public void changeLabel(String word){
		
		
		//jlbLabel2 = new JLabel(word);
		jlbLabel1.setSize(300,300);
		jlbLabel1.setFont(new Font ("Candara",Font.BOLD,100));
		jlbLabel1.setHorizontalAlignment(JLabel.CENTER);
		jlbLabel1.setVerticalTextPosition(JLabel.BOTTOM);
		jlbLabel1.setForeground(Color.BLACK);
		
		add(jlbLabel2);
		setVisible(true);
	}
}
