package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.LoginBean;
import bean.User;
import util.DBConnection;
import util.Role;

public class LoginDAO {
	
	public Map<User, Role> authenticate(LoginBean loginBean) {
		Map<User, Role> userMap = new HashMap<User, Role>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
			stmt.setString(1, loginBean.getUsername());
			stmt.setString(2, loginBean.getPassword());
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				User user = new User();
				
				user.setId(result.getLong(1));
				user.setUsername(result.getString(2));
				user.setPassword(result.getString(3));
				
				Role role = Role.valueOf(result.getString(4));
				
				userMap.put(user, role);
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return userMap;
	}
	
}
