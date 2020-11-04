package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.ImageSetting;
import util.DBConnection;

public class ImageSettingDAO implements Dao<ImageSetting> {

	@Override
	public List<ImageSetting> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageSetting getByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ImageSetting getByKey(String key) {
		ImageSetting setting = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM imagesetting WHERE keyname=?");
			stmt.setString(1, key);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				setting = new ImageSetting();
				
				setting.setId(result.getLong(1));
				setting.setKeyname(result.getString(2));
				setting.setImageval(result.getBytes(3));
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
	public int insert(ImageSetting t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ImageSetting setting) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE imagesetting SET imageval=? WHERE keyname=?");
			stmt.setBytes(1, setting.getImageval());
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
	public int delete(ImageSetting t) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
