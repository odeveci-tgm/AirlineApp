import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

import javax.swing.*;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



public class Model {
	

	
	JTextField userVal = new JTextField(8);
	JTextField portVal = new JTextField(8);
	JTextField pwdVal = new JTextField(8);
	JTextField serverVal = new JTextField(8);
	JTextField dbVal = new JTextField(8);
	JLabel serverL = new JLabel("Servername: ");
	JLabel userL = new JLabel("Username: ");
	JLabel pwdL = new JLabel("Passwort: ");
	JLabel portL = new JLabel("Portnummer: ");
	JLabel dbL = new JLabel("Datenbank: ");
	JLabel arvL = new JLabel("Flughafen / ANKUNFT: ");
	JLabel deptL = new JLabel("Flughafen / ABFLUG: ");
	JLabel fail = new JLabel("Connection failed!");
	JComboBox jcDept = new JComboBox();
	JComboBox jcArv = new JComboBox();
	JButton buttonServer = new JButton("connect");
	ActionListener l;
	
	public void init() {
		
		// GUI-Frame Modellierung
		
		JFrame jf = new JFrame("AirlineApp");
		jf.setSize(600, 600);
		JPanel jp = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		jp.add(userL,c);
		c.gridx++;
		jp.add(userVal,c);
		
		c.gridx++;
		jp.add(pwdL, c);
		c.gridx++;
		jp.add(pwdVal,c);
		
		c.gridy=1;
		c.gridx=0;
		jp.add(serverL,c);
		c.gridx=1;
		jp.add(serverVal,c);
		
		c.gridx++;
		jp.add(portL, c);
		c.gridx++;
		jp.add(portVal,c);
		
		c.gridx++;
		jp.add(dbL,c);
		c.gridx++;
		jp.add(dbVal,c);
		
		
		
		
		c.gridy++;
		c.gridx=2;
		c.gridwidth = 2;
		jp.add(buttonServer,c);
		
		
		c.gridy++;
		c.gridx=0;
		jp.add(deptL, c);
		c.gridx++;
		jp.add(jcDept,c);
		
		c.gridx=3;
		jp.add(arvL,c);
		c.gridx++;
		jp.add(jcArv, c);
		jp.setBackground(Color.LIGHT_GRAY);
		jf.setLocationRelativeTo(null);
		jf.setContentPane(jp);
		
		// Action-Listener für diverse Buttons (unter anderem für non-hardcoded parameters für ds)
		
		buttonServer.addActionListener(l);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.pack();
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
