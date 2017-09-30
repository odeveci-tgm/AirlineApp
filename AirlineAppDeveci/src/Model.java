import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.*;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



public class Model {
	

	
	static JTextField userVal = new JTextField(8);
	static JTextField portVal = new JTextField(8);
	static JPasswordField pwdVal = new JPasswordField(8);
	static JTextField serverVal = new JTextField(8);
	static JTextField dbVal = new JTextField(8);
	static JTextField vorName = new JTextField(8);
	static JTextField nachName = new JTextField(8);
	
	JLabel serverL = new JLabel("Servername: ");
	JLabel userL = new JLabel("Username: ");
	JLabel pwdL = new JLabel("Passwort: ");
	JLabel portL = new JLabel("Portnummer: ");
	JLabel dbL = new JLabel("Datenbank: ");
	static JLabel arvL = new JLabel("Flughafen / ANKUNFT: ");
	static JLabel deptL = new JLabel("Flughafen / ABFLUG: ");
	static JLabel fail = new JLabel("");
	static JLabel passL = new JLabel("Wählen Sie Ihren gewünschten Flug aus. Geben Sie anschließend Vor- und Nachname ein.");
	static JLabel vorNameL = new JLabel("Vorname: ");
	static JLabel nachNameL = new JLabel("Nachname: ");
	static JLabel dbArt = new JLabel("Hersteller: ");
	
	static JComboBox<String> jcDept = new JComboBox();
	static JComboBox<String> jcArv = new JComboBox();
	static JComboBox<String> jcFlights = new JComboBox();
	static JComboBox<String> jcDb = new JComboBox();
	
	JFrame jf = new JFrame("AdminAirlineApp");
	JPanel jp = new JPanel(new GridBagLayout());

	JButton buttonServer = new JButton("Verbinden");
	static JButton buttonFlight = new JButton("Flüge anzeigen");
	static JButton buttonAcceptFlight = new JButton("Flug auswählen");
	static JButton buttonPass = new JButton("Ausgewählten Flug buchen");
	
	
	ActionListener l;
	
	
	public static void resetFields() {
		
		// Alle Felder NACH dem connect Button werden unsichtbar gestellt, um die Buchung schrittweise zu gestalten
		
		jcDept.setVisible(false);
		jcArv.setVisible(false);
		jcFlights.setVisible(false);
		buttonFlight.setVisible(false);
		buttonAcceptFlight.setVisible(false);
		buttonPass.setVisible(false);
		arvL.setVisible(false);
		deptL.setVisible(false);
		passL.setVisible(false);
		vorNameL.setVisible(false);
		nachNameL.setVisible(false);
		vorName.setVisible(false);
		nachName.setVisible(false);
	}
	
	
	public void init() {
		
		/* 
		 * 
			--- GUI MODELLIERUNG ---
		 *	
		 */
		
	
		resetFields();
		userVal.setText("root");
		portVal.setText("3306");
		serverVal.setText("localhost");
		dbVal.setText("list_3ahit");
		passL.setForeground(Color.blue);
		buttonServer.setActionCommand("connect");
		buttonFlight.setActionCommand("flights");
		buttonAcceptFlight.setActionCommand("accept");
		
		
		
		jf.setSize(1000, 700);
		jf.setResizable(false);
		
			// Positionierung der Felder mittels GBC-Layout
		
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
		
		c.gridx++;
		jp.add(dbArt,c);
		c.gridx++;
		jp.add(jcDb,c);
		
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
		
		jcDb.addItem("MySQL");
		jcDb.addItem("PostgreSQL");
		jcDb.addItem("Oracle");
		
		
		
		c.gridy++;
		c.gridx=2;
		c.gridwidth = 2;
		jp.add(buttonServer,c);
		
		
		fail.setForeground(Color.LIGHT_GRAY);
		c.gridy++;
		jp.add(fail,c);
		
		
		c.gridy++;
		c.gridx=0;
		c.anchor = GridBagConstraints.WEST;
		jp.add(deptL, c);
		c.gridx=3;
		c.anchor = GridBagConstraints.EAST;
		jp.add(arvL,c);
		
		c.gridy++;
		c.gridx=0;
		c.gridwidth=10;
		c.anchor = GridBagConstraints.WEST;
		jp.add(jcDept,c);
		c.gridx++;
		c.gridwidth=10;
		c.anchor = GridBagConstraints.EAST;
		jp.add(jcArv, c);
		
		c.gridwidth=6;
		c.gridy++;
		c.gridx=0;
		c.anchor = GridBagConstraints.CENTER;
		jp.add(buttonFlight,c);
		
		c.gridwidth=6;
		c.gridx=0;
		c.gridy++;
		c.anchor = GridBagConstraints.CENTER;
		jp.add(jcFlights, c);
		
		c.gridwidth=6;
		c.gridy++;
		c.gridx=0;
		c.anchor = GridBagConstraints.CENTER;
		jp.add(buttonAcceptFlight,c);
		
		c.gridy++;
		c.gridwidth=6;
		c.anchor = GridBagConstraints.CENTER;
		jp.add(passL,c);
		
		c.gridy++;
		c.gridx=0;
		c.gridwidth=2;
		c.anchor= GridBagConstraints.WEST;
		jp.add(vorNameL,c);
		
		c.gridx++;
		jp.add(vorName,c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx++;
		jp.add(nachNameL,c);
		
		c.anchor = GridBagConstraints.EAST;
		c.gridx++;
		jp.add(nachName,c);
		
		c.gridy++;
		c.gridx=0;
		c.gridwidth=8;
		c.anchor=GridBagConstraints.CENTER;
		jp.add(buttonPass,c);
		
		
		
		jcArv.setPrototypeDisplayValue("....................................................................");
		jcDept.setPrototypeDisplayValue(".....................................................................");
		jp.setBackground(Color.LIGHT_GRAY);
		jf.setLocationRelativeTo(null);
		jf.setContentPane(jp);
		
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	
	public void dataCheck () {
		 l = new ActionListener() {

			@Override
			
			//On-Click = actionPerformed
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					sqlDatabase sd = new sqlDatabase();
					
					
					
					switch (((JButton)e.getSource()).getActionCommand()) {
						
					// Je nach Button werden andere Querys eingeleitet
					
					case "connect":
										sd.connect();
										sd.getAirports();	
										break;
										
										
					case "flights":		
						
										sd.getFlights();
										break;
										
					case "accept":
								
										break;
										
					case "passagier":
										
									
										break;
										
						
					default:			
										System.out.println("Something went wrong. REALLY wrong.");
										break;
									}
					
					
									
					
					// Query für alle möglichen Flughäfen formatiert in "<countryname>,<airportname>"
					
					
					
				} // Wird wahrscheinlich nie eintreten
				catch(NullPointerException e2) {
					e2.printStackTrace();
					fail.setText("Geben Sie bitte überall gültige Werte ein!");
					fail.setForeground(Color.red);
					jcFlights.removeAllItems();
					jcDept.removeAllItems();
					jcArv.removeAllItems();
					
				}
				
				catch (NumberFormatException e3) {
					e3.printStackTrace();
					fail.setText("Ungültige Portnummer oder fehlende Eingaben!");
					fail.setForeground(Color.red);
					jcFlights.removeAllItems();
					jcDept.removeAllItems();
					jcArv.removeAllItems();
					
				}
				
				
			}
		};
		
		buttonServer.addActionListener(l);
		buttonFlight.addActionListener(l);
		buttonAcceptFlight.addActionListener(l);
	}
	
	
	public static void main (String []args) {
		Model m = new Model();
		m.init();
		m.dataCheck();
	}

	
}
