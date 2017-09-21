import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceInit extends MysqlDataSource {
	
	public DataSourceInit(String servername,int port,String user,String password) {
		MysqlDataSource ds = new MysqlDataSource();
		try {
			Connection con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		ds.setServerName(servername);
		ds.setPortNumber(port);
		ds.setUser(user);
		ds.setPassword(password);
	}
	
	
}
