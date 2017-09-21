import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DataSourceInit extends MysqlDataSource {
	
	String servername;
	int port;
	String user;
	String password;
	
	public DataSourceInit(String servername,int port,String user,String password) {
		this.servername = servername;
		this.port = port;
		this.user = user;
		this.password = password;
		
		MysqlDataSource ds = new MysqlDataSource();
		ds.setServerName(servername);
		ds.setPortNumber(port);
		ds.setUser(user);
		ds.setPassword(password);
		System.out.println(ds.getServerName()+ds.getUser());
		
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	


	
	
}
