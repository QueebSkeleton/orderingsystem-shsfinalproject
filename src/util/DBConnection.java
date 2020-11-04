package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost:3306/oos_db";
	private static String USER = "root";
	private static String PASSWORD = "RIan_reyes123";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName(DRIVER);
			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
