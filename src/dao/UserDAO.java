package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.User;
import util.DBConnection;

public class UserDAO {

	public User getAdministrator() {
		User administrator = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet result = stmt.executeQuery("SELECT * FROM user WHERE role = 'ADMINISTRATOR'");
			
			if(result.next()) {
				administrator = new User();
				
				administrator.setId(result.getLong(1));
				administrator.setUsername(result.getString(2));
				administrator.setPassword(result.getString(3));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return administrator;
	}
	
	public int update(User user) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setLong(3, user.getId());
			
			status = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean findSameUsername(User user, String username) {
		boolean foundSameUsername = false;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = null;
			
			if(user != null && user.getId() != 0) {
				stmt = conn.prepareStatement("SELECT * FROM user WHERE username=? AND id != ?");
				stmt.setString(1, username);
				stmt.setLong(2, user.getId());
			} else {
				stmt = conn.prepareStatement("SELECT * FROM user WHERE username=?");
				stmt.setString(1, username);
			}
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				foundSameUsername = true;
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return foundSameUsername;
	}
	
}
