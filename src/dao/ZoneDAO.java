package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Zone;
import util.DBConnection;

public class ZoneDAO implements Dao<Zone> {

	@Override
	public List<Zone> getAllRecords() {
		List<Zone> zoneList = new ArrayList<Zone>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM zone");
			
			while(results.next()) {
				Zone zone = new Zone();
				
				zone.setId(results.getLong(1));
				
				CurrencyDAO currencyDao = new CurrencyDAO();
				zone.setCurrency(currencyDao.getByID(results.getLong(2)));
				
				zone.setName(results.getString(3));
				zone.setDescription(results.getString(4));
				
				zoneList.add(zone);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return zoneList;
	}

	@Override
	public Zone getByID(long id) {
		Zone zone = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM zone WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				zone = new Zone();
				
				zone.setId(result.getLong(1));
				
				CurrencyDAO currencyDao = new CurrencyDAO();
				zone.setCurrency(currencyDao.getByID(result.getLong(2)));
				
				zone.setName(result.getString(3));
				zone.setDescription(result.getString(4));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return zone;
	}

	@Override
	public int insert(Zone zone) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO zone VALUES (null, ?, ?, ?)");
			stmt.setLong(1, zone.getCurrency().getId());
			stmt.setString(2, zone.getName());
			stmt.setString(3, zone.getDescription());
			
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
	public int update(Zone zone) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE zone SET currency_id=?, name=?, description=? WHERE id=?");
			stmt.setLong(1, zone.getCurrency().getId());
			stmt.setString(2, zone.getName());
			stmt.setString(3, zone.getDescription());
			stmt.setLong(4, zone.getId());
			
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
	public int delete(Zone zone) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM zone WHERE id=?");
			stmt.setLong(1, zone.getId());
			
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
