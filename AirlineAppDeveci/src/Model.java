import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

import javax.swing.*;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



public class Model {
	JTextField userVal = new JTextField("root");
	JTextField portVal = new JTextField("3306");
	JTextField pwdVal = new JTextField("password");
	JTextField serverVal = new JTextField("localhost");
	JTextField dbVal = new JTextField("datenbank");
	JButton buttonServer = new JButton("bestätigen");
	ActionListener l;
	public void init() {
		
		// GUI-Frame Modellierung
		
		JFrame jf = new JFrame("AirlineApp");
		jf.setSize(600, 600);
		JPanel jp = new JPanel();
		jp.add(serverVal);
		jp.add(portVal);
		jp.add(userVal);
		jp.add(pwdVal);
		jp.add(dbVal);
		jp.add(buttonServer);
		buttonServer.setName("server");
	
		jp.setBackground(Color.LIGHT_GRAY);
		jf.setLocationRelativeTo(null);
		jf.setContentPane(jp);
		
		// Action-Listener für diverse Buttons (unter anderem für non-hardcoded parameters für ds)
		
		buttonServer.addActionListener(l);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	public void dataCheck () {
		 l = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MysqlDataSource ds = new MysqlDataSource();
				// DataSource Parameter werden gesetzt.
				ds.setUser(userVal.getText());
				ds.setPassword(pwdVal.getText());
				ds.setServerName(serverVal.getText());
				ds.setPortNumber(Integer.parseInt(portVal.getText()));
				try {
					
					// Driver werden geladen, sagt auf welche DB zugegriffen werden soll, (alternativ geht auch 
					// in der SELECT query database.tablename zu schreiben)
					String url = "jdbc:mysql://"+serverVal.getText()+":"
					+Integer.parseInt(portVal.getText())+"/"+dbVal.getText();
					Connection con2 = DriverManager.getConnection(url,userVal.getText(),pwdVal.getText());
					Statement st = con2.createStatement();
					// Test-Query
					ResultSet rs = st.executeQuery("SELECT * from airlines");
					rs.next();
					String test = rs.getString("name");
					System.out.println(test);
					rs.close(); st.close(); con2.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		
		buttonServer.addActionListener(l);
	}
	
	
	public static void main (String []args) {
		Model m = new Model();
		m.init();
		m.dataCheck();
	}

	
}
