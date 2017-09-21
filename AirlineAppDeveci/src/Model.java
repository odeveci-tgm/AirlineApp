import java.awt.*;
import java.awt.Color;
import java.util.Scanner;

import javax.swing.*;

public class Model {
	JTextField userVal = new JTextField("username");
	JTextField portVal = new JTextField("portnummer");
	JTextField pwdVal = new JTextField("password");
	JTextField serverVal = new JTextField("servername");
	JButton buttonServer = new JButton("bestätigen");
	DataSourceInit ds;
	public void init() {
		JFrame jf = new JFrame("AirlineApp");
		jf.setSize(600, 600);
		JPanel jp = new JPanel();
		jp.add(userVal);
		jp.add(pwdVal);
		jp.add(serverVal);
		jp.add(portVal);
		jp.add(buttonServer);
		jp.setBackground(Color.LIGHT_GRAY);
		jf.setLocationRelativeTo(null);
		jf.setContentPane(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	
	public static void main (String []args) {
		Model m = new Model();
		m.init();
	}

	
}
