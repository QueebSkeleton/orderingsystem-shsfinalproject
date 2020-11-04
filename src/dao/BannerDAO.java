package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Banner;
import util.DBConnection;

public class BannerDAO implements Dao<Banner> {

	@Override
	public List<Banner> getAllRecords() {
		List<Banner> bannerList = new ArrayList<Banner>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM banner");
			
			while(results.next()) { 
				Banner banner = new Banner();
				
				banner.setId(results.getLong(1));
				banner.setImage(results.getBytes(2));
				banner.setDescription(results.getString(3));
				
				bannerList.add(banner);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bannerList;
	}

	@Override
	public Banner getByID(long id) {
		Banner banner = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM banner WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) { 
				banner = new Banner();
				
				banner.setId(result.getLong(1));
				banner.setImage(result.getBytes(2));
				banner.setDescription(result.getString(3));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return banner;
	}

	@Override
	public int insert(Banner banner) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO banner VALUES (null, ?, ?)");
			stmt.setBytes(1, banner.getImage());
			stmt.setString(2, banner.getDescription());
			
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
	public int update(Banner t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Banner banner) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM banner WHERE id=?");
			stmt.setLong(1, banner.getId());
			
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
	
}
