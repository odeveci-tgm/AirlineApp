import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class sqlDatabase {
	
	Connection con;
	java.sql.Statement st;
	java.sql.Statement st2;
	ResultSet rs;
	MysqlDataSource ds = new MysqlDataSource();
	String url;
	
	

	
	public sqlDatabase() {
		
		
		String urlSql = "jdbc:mysql://"+Model.serverVal.getText()+":"
		+Integer.parseInt(Model.portVal.getText())+"/"+Model.dbVal.getText();
		String urlpSql = "jdbc:postgres://"+Model.serverVal.getText()+":"
				+Integer.parseInt(Model.portVal.getText())+"/"+Model.dbVal.getText();
		try {
			
		if(Model.jcDb.getSelectedItem()=="MySQL") {
			con = DriverManager.getConnection(urlSql,Model.userVal.getText(),Model.pwdVal.getText());
			System.out.println("bin drin");
		} 
		
		if(Model.jcDb.getSelectedItem()=="PostgreSQL") {
			con = DriverManager.getConnection(urlpSql,Model.userVal.getText(),Model.pwdVal.getText());
		} 
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			int i = 0;
			
			for(String str: airArr) {
				Model.jcDept.addItem(str);
				Model.jcArv.addItem(str);
				i++;
			}
			System.out.println(i);
			
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
		System.out.println(partsDep[0]);
		try {
			st2 = con.createStatement();
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
						Model.jcFlights.addItem(str);
					}
					
					if (flightsArr.length==0) {
						Model.fail.setForeground(Color.red);
						Model.fail.setText("Keine Flüge vorhanden.");
					} else {
						Model.jcFlights.setVisible(true);
						Model.buttonFlight.setVisible(true);
						Model.vorNameL.setVisible(true);
						Model.nachNameL.setVisible(true);
						Model.nachName.setVisible(true);
						Model.vorName.setVisible(true);
						Model.passL.setVisible(true);
						Model.buttonPass.setVisible(true);
						
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
