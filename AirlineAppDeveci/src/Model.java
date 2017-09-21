import java.awt.Color;

import javax.swing.*;

public class Model {

	
	public void init() {
		JFrame jf = new JFrame("BookingApp");
		jf.setSize(600, 600);
		JPanel jp = new JPanel();
		jp.setBackground(Color.LIGHT_GRAY);
		jf.setLocationRelativeTo(null);
		jf.setContentPane(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	
	public static void main (String []args) {
		Model m = new Model();
		m.init();
		System.out.println();
	}

	
}
