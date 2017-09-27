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
	

	
	JTextField userVal = new JTextField(8);
	JTextField portVal = new JTextField(8);
	JPasswordField pwdVal = new JPasswordField(8);
	JTextField serverVal = new JTextField(8);
	JTextField dbVal = new JTextField(8);
	JTextField vorName = new JTextField(8);
	JTextField nachName = new JTextField(8);
	
	JLabel serverL = new JLabel("Servername: ");
	JLabel userL = new JLabel("Username: ");
	JLabel pwdL = new JLabel("Passwort: ");
	JLabel portL = new JLabel("Portnummer: ");
	JLabel dbL = new JLabel("Datenbank: ");
	JLabel arvL = new JLabel("Flughafen / ANKUNFT: ");
	JLabel deptL = new JLabel("Flughafen / ABFLUG: ");
	JLabel fail = new JLabel("");
	JLabel passL = new JLabel("Wählen Sie Ihren gewünschten Flug aus. Geben Sie anschließend Vor- und Nachname ein.");
	JLabel vorNameL = new JLabel("Vorname: ");
	JLabel nachNameL = new JLabel("Nachname: ");
	
	JComboBox<String> jcDept = new JComboBox();
	JComboBox<String> jcArv = new JComboBox();
	JComboBox<String> jcFlights = new JComboBox();
	
	JFrame jf = new JFrame("AdminAirlineApp");
	JPanel jp = new JPanel(new GridBagLayout());

	JButton buttonServer = new JButton("Verbinden");
	JButton buttonFlight = new JButton("Flüge anzeigen");
	JButton buttonPass = new JButton("Ausgewählten Flug buchen");
	
	
	ActionListener l;
	
	
	
	public void init() {
		
		/* 
		 * 
			GUI MODELLIERUNG
		 *	
		 */
		
		// Alle Felder NACH dem connect Button werden unsichtbar gestellt, um die Buchung schrittweise zu gestalten
		jcDept.setVisible(false);
		jcArv.setVisible(false);
		jcFlights.setVisible(false);
		buttonFlight.setVisible(false);
		buttonPass.setVisible(false);
		arvL.setVisible(false);
		deptL.setVisible(false);
		passL.setVisible(false);
		vorNameL.setVisible(false);
		nachNameL.setVisible(false);
		vorName.setVisible(false);
		nachName.setVisible(false);
		
		userVal.setText("root");
		portVal.setText("3306");
		serverVal.setText("localhost");
		dbVal.setText("list_3ahit");
		passL.setForeground(Color.blue);
		buttonServer.setActionCommand("connect");
		buttonFlight.setActionCommand("flights");
		
		
		
		
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
		
		c.gridy++;
		c.gridx=0;
		
		
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
		jcFlights.setPrototypeDisplayValue("...................................................................................................................................................................");
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
				MysqlDataSource ds = new MysqlDataSource();
				try {
					
					String url = "jdbc:mysql://"+serverVal.getText()+":"
					+Integer.parseInt(portVal.getText())+"/"+dbVal.getText();
					Connection con = DriverManager.getConnection(url,userVal.getText(),pwdVal.getText());
					
					switch (e.getActionCommand()) {
						
					// Je nach Button werden andere Querys eingeleitet
					
					case "connect":
										// DataSource Parameter werden gesetzt.
										
										ds.setUser(userVal.getText());
										ds.setPassword(pwdVal.getText());
										ds.setServerName(serverVal.getText());
										ds.setPortNumber(Integer.parseInt(portVal.getText()));
										
										// Driver werden geladen, sagt auf welche DB zugegriffen werden soll, (alternativ geht auch 
										// in der SELECT query database.tablename zu schreiben)
										
										
										Statement st = con.createStatement();
										ResultSet rs = st.executeQuery("select countries.name,airports.name from airports,countries WHERE airports.country = countries.code ORDER BY 1;");
										
									
										//ArrayList für alle Elemente
										
										ArrayList<String> airports = new ArrayList<String>();
										while(rs.next()) {
											String country = rs.getString("countries.name");
											String airport = rs.getString("airports.name");
											airports.add(airport+","+country);
										}
										
										
										
										//Umwandlung in Array anschließend Zuweisung für die jeweiligen Dropdowns
										
										String airArr[] = airports.toArray(new String[airports.size()]);
										int i = 0;
										
										for(String str: airArr) {
											jcDept.addItem(str);
											jcArv.addItem(str);
											i++;
										}
										System.out.println(i);
										
										fail.setForeground(Color.green);
										fail.setText("Connection established!");
										
										
										
										
										arvL.setVisible(true);
										deptL.setVisible(true);
										jcArv.setVisible(true);
										jcDept.setVisible(true);
										buttonFlight.setVisible(true);
										
										rs.close(); st.close(); con.close();
										break;
										
										
					case "flights":		
										// Value von der DropDown wird geholt. Land und Flughafenname werden wieder getrennt per split().
										// Esentziell für die Query
										jcFlights.removeAllItems();
										String dep =  String.valueOf(jcDept.getSelectedItem());
										String arv = String.valueOf(jcArv.getSelectedItem());
										String[] partsDep = dep.split(",");
										String[] arvDep = arv.split(",");
										System.out.println(partsDep[0]);
										Statement st2 = con.createStatement();
										ResultSet rs2 = st2.executeQuery("select * from flights,(select airportcode as 'depcode' from airports "
										+ "WHERE name='"+partsDep[0]+"')dep,(select airportcode as 'arrcode' from airports WHERE name='"+arvDep[0]+"')arr "
										+ "WHERE depcode = departure_airport AND arrcode = destination_airport;");
										
										ArrayList<String> flights = new ArrayList<String>();
										while(rs2.next()) {
												flights.add(rs2.getString("departure_airport")+","+rs2.getTime("departure_time")+"," 
												+ rs2.getString("destination_airport")+","+rs2.getTime("destination_time"));
												
										}
										
										String[] flightsArr = flights.toArray(new String[flights.size()]);
										
										for (String str:flightsArr) {
											jcFlights.addItem(str);
										}
										
										if (flightsArr.length==0) {
											fail.setForeground(Color.red);
											fail.setText("Keine Flüge vorhanden.");
										} else {
											jcFlights.setVisible(true);
											buttonFlight.setVisible(true);
											vorNameL.setVisible(true);
											nachNameL.setVisible(true);
											nachName.setVisible(true);
											vorName.setVisible(true);
											passL.setVisible(true);
											buttonPass.setVisible(true);
											
										}
										
										
										
										
										break;
					case "passagier":
										
									
										break;
										
										
					default:			
										System.out.println("Something went wrong.");
										break;
									}
					
					
									
					
					// Query für alle möglichen Flughäfen formatiert in "<countryname>,<airportname>"
					
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					fail.setForeground(Color.red);
					fail.setText("Connection failed! Überprüfen Sie Ihre Werte.");
					jcFlights.removeAllItems();
					jcDept.removeAllItems();
					jcArv.removeAllItems();
					e1.printStackTrace();	
				}
					// Wird wahrscheinlich nie eintreten
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
	}
	
	
	public static void main (String []args) {
		Model m = new Model();
		m.init();
		m.dataCheck();
	}

	
}
