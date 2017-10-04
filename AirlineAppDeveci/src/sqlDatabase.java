import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.jdesktop.swingx.autocomplete.*;
=======

import javax.swing.JOptionPane;

>>>>>>> branch 'master' of https://github.com/odeveci-tgm/AirlineApp.git
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class sqlDatabase {
	
	Connection con;
	java.sql.Statement st;
	java.sql.Statement st2;
	PreparedStatement st3;
	java.sql.Statement st4;
	ResultSet rs;
	MysqlDataSource ds = new MysqlDataSource();
	String url;
	
	

	
	public sqlDatabase() {
	
		
		
		String urlSql = "jdbc:mysql://"+Model.serverVal.getText()+":"
		+Integer.parseInt(Model.portVal.getText())+"/"+Model.dbVal.getText();
		
		String urlpSql = "jdbc:postgresql://"+Model.serverVal.getText()+":"
		+Integer.parseInt(Model.portVal.getText())+"/"+Model.dbVal.getText();
		
		String urlOracle = "jdbc:oracle:thin:@"+Model.serverVal.getText()+":"
				+Integer.parseInt(Model.portVal.getText())+":ORCL";
		
		try {
			
		if(Model.jcDb.getSelectedItem()=="MySQL") {
			con = DriverManager.getConnection(urlSql,Model.userVal.getText(),Model.pwdVal.getText());
		} 
		
		if(Model.jcDb.getSelectedItem()=="PostgreSQL") {
			con = DriverManager.getConnection(urlpSql,Model.userVal.getText(),Model.pwdVal.getText());
		} 
		
		if(Model.jcDb.getSelectedItem()=="Oracle") {
			con = DriverManager.getConnection(urlOracle);
		} 
		
		
		
			
		} catch (Exception e) {
			Model.resetFields();
			e.printStackTrace();
		}
	}
	
	public void connect() {
		
				
				ds.setUser(Model.userVal.getText());
				ds.setPassword(Model.pwdVal.getText());
				ds.setServerName(Model.serverVal.getText());
				ds.setPortNumber(Integer.parseInt(Model.portVal.getText()));
	}
	
	
	public void getAirports() {
		
		
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("select countries.name,airports.name from airports,countries WHERE airports.country = countries.code ORDER BY 1;");
			
			//ArrayList für alle Elemente
			
			
			ArrayList<String> airports = new ArrayList<String>();
			while(rs.next()) {
				String country = rs.getString("countries.name");
				String airport = rs.getString("airports.name");
				airports.add(airport+","+country);
			}
			
			
			
			//Umwandlung in Array anschließend Zuweisung für die jeweiligen Dropdowns
			
			String airArr[] = airports.toArray(new String[airports.size()]);
		
			for(String str: airArr) {
				Model.jcDept.addItem(str);
				Model.jcArv.addItem(str);
			}
			
			
			Model.fail.setForeground(Color.green);
			Model.fail.setText("Connection established!");
			
			
			
			
			Model.arvL.setVisible(true);
			Model.deptL.setVisible(true);
			Model.jcArv.setVisible(true);
			Model.jcDept.setVisible(true);
			Model.buttonFlight.setVisible(true);
			
			rs.close();st.close(); con.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			Model.resetFields();
			e1.printStackTrace();
		}
		
		
	
	
		
	}
	
	
	public void getFlights() {
		// Value von der DropDown wird geholt. Land und Flughafenname werden wieder getrennt per split().
		// Esentziell für die Query
		Model.jcFlights.removeAllItems();
		String dep =  String.valueOf(Model.jcDept.getSelectedItem());
		String arv = String.valueOf(Model.jcArv.getSelectedItem());
		String[] partsDep = dep.split(",");
		String[] arvDep = arv.split(",");
		try {
			st2 = con.createStatement();
			ResultSet rs2 = st2.executeQuery("select * from flights,(select airportcode as 'depcode' from airports "
					+ "WHERE name='"+partsDep[0]+"')dep,(select airportcode as 'arrcode' from airports WHERE name='"+arvDep[0]+"')arr "
					+ "WHERE depcode = departure_airport AND arrcode = destination_airport;");
					
					ArrayList<String> flights = new ArrayList<String>();
					ArrayList<Integer> flightnr = new ArrayList<Integer>();
					
					while(rs2.next()) {
							flights.add("Abflug von " + rs2.getString("departure_airport")+" am "+rs2.getDate("departure_time")+" um "+rs2.getTime("departure_time")+" Uhr || Ankunft in " 
							+ rs2.getString("destination_airport")+" am "+rs2.getDate("destination_time")+" um "+rs2.getTime("destination_time")+" Uhr,Flugnummer: "+rs2.getInt("flightnr")+" Airline: "+rs2.getString("airline"));
							
							
					}
					
					String[] flightsArr = flights.toArray(new String[flights.size()]);
					
					for (String str:flightsArr) {
						Model.jcFlights.addItem(str);
					}
					
				
					
					
					if (flightsArr.length==0) {
						Model.fail.setForeground(Color.red);
						Model.fail.setText("Keine Flüge vorhanden.");
						Model.resetFields();
						Model.jcDept.setVisible(true);
						Model.jcArv.setVisible(true);
						Model.arvL.setVisible(true);
						Model.deptL.setVisible(true);
						Model.buttonFlight.setVisible(true);
						Model.jcFlights.setEnabled(true);
						Model.jcFlights.removeAllItems();
					} else {
						Model.jcFlights.setVisible(true);
						Model.buttonFlight.setVisible(true);
						Model.buttonAcceptFlight.setVisible(true);
						Model.fail.setText("");
						
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void bookFlight() {
		
		String vorname = Model.vorName.getText();
		String nachname = Model.nachName.getText();
		String flightnr = getBookedFlightNr();
		String airline = getBookedAirline();
		String row = String.valueOf(Model.jcRow.getSelectedItem());
		String seat = String.valueOf(Model.jcSeat.getSelectedItem());
		boolean goIn = false;
		
		
		if (Model.vorName.getText().isEmpty() || !(Model.vorName.getText() instanceof String) || Model.nachName.getText().isEmpty() || !(Model.nachName.getText() instanceof String)) {
			JOptionPane.showMessageDialog(Model.jf, "Geben Sie Ihren Vor- und Nachnamen ein!");
		} else {
			goIn=true;
		}
		
		
		while (goIn==true) {
			
		
		
		try {
			String sql = "INSERT INTO passengers VALUES(NULL,'"+vorname+"','"+nachname+"','"+airline+"','"+Integer.parseInt(flightnr)+"','"+row+"','"+seat+"');";
			st3 = con.prepareStatement(sql);
			st3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Model.fail.setText("Buchung fehlgeschlagen");
		}
<<<<<<< HEAD
		Model.passL.setForeground(Color.green);
		Model.passL.setText("ERFOLGREICH GEBUCHT");
		Model.jf.setEnabled(false);
		
		try {
			Thread.sleep(5000);
			System.exit(0);
			System.out.println("Gebucht");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
=======
		}
		
>>>>>>> branch 'master' of https://github.com/odeveci-tgm/AirlineApp.git
	}
	
	
	public static String getBookedFlightNr() {
		String s = String.valueOf(Model.jcFlights.getSelectedItem());
		String[] splitted = s.split("Flugnummer: ");
		
		// Ausgabe wäre: <flugnummer> Airline: <airline>, deswegen zweites split nötig um Airline auszuschließen!
		
		String[] splitted2 = splitted[1].split(" Airline: ");
		String flugnr = splitted2[0];
		return flugnr;
	}
	
	public static String getBookedAirline() {
		String s = String.valueOf(Model.jcFlights.getSelectedItem());
		String[] splitted = s.split("Airline: ");
		String airline = splitted[1];
		return airline;
	}
	
	public int getMaxRows() {
		int maxrow = 0;
		try {
			st4 = con.createStatement();
			ResultSet rs = st4.executeQuery("select planes.maxseats,planes.seatsperrow from planes,flights WHERE flights.planetype = planes.id AND flights.flightnr ='"+getBookedFlightNr()+"' AND flights.airline ='"+getBookedAirline()+"';");
			
			while (rs.next()) {
				maxrow = rs.getInt("maxseats") / rs.getInt("seatsperrow");
			}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maxrow;
	}
	
	
	public void selectFlight() {
		
		Model.vorNameL.setVisible(true);
		Model.nachNameL.setVisible(true);
		Model.nachName.setVisible(true);
		Model.vorName.setVisible(true);
		Model.passL.setVisible(true);
		Model.buttonPass.setVisible(true);
		Model.rowL.setVisible(true);
		Model.jcRow.setVisible(true);
		Model.jcSeat.setVisible(true);
		Model.seatL.setVisible(true);
		
		Model.jcFlights.setEnabled(false);
		
		String[] seat = {"A","B","C","D","E","F","G"};
		ArrayList<Integer> row = new ArrayList<Integer>();
		for(int i=0;i<seat.length;i++) {
			Model.jcSeat.addItem(seat[i]);
		}
		
		for(int i=1;i<=getMaxRows();i++) {
			row.add(i);
		}
		
		Integer[] rowArr = row.toArray(new Integer[row.size()]);
		
		for (Integer nr:rowArr) {
			Model.jcRow.addItem(nr.toString());
		}
	
		
	}

}
