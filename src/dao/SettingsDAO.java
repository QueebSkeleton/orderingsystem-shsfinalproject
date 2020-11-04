package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.Setting;
import util.DBConnection;

public class SettingsDAO implements Dao<Setting> {

	@Override
	public List<Setting> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Setting getByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Setting getByKey(String keyname) {
		Setting setting = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM setting WHERE keyname=?");
			stmt.setString(1, keyname);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				setting = new Setting();
				
				setting.setId(result.getInt(1));
				setting.setKeyname(result.getString(2));
				setting.setSetvalue(result.getString(3));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return setting;
	}

	@Override
	public int insert(Setting t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Setting setting) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE setting SET setvalue=? WHERE keyname=?");
			stmt.setString(1, setting.getSetvalue());
			stmt.setString(2, setting.getKeyname());
			
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

	@Override
	public int delete(Setting t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
