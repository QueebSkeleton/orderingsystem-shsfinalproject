package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ModeOfDelivery;
import util.DBConnection;

public class ModeOfDeliveryDAO implements Dao<ModeOfDelivery> {
	
	@Override
	public List<ModeOfDelivery> getAllRecords() {
		List<ModeOfDelivery> modeOfDeliveryList = new ArrayList<ModeOfDelivery>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM modeofdelivery");
			
			while(results.next()) {
				ModeOfDelivery modeOfDelivery = new ModeOfDelivery();
				
				modeOfDelivery.setId(results.getLong(1));
				modeOfDelivery.setName(results.getString(2));
				modeOfDelivery.setDescription(results.getString(3));
				
				modeOfDeliveryList.add(modeOfDelivery);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return modeOfDeliveryList;
	}

	@Override
	public ModeOfDelivery getByID(long id) {
		ModeOfDelivery modeOfDelivery = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM modeofdelivery WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				modeOfDelivery = new ModeOfDelivery();
				
				modeOfDelivery.setId(result.getLong(1));
				modeOfDelivery.setName(result.getString(2));
				modeOfDelivery.setDescription(result.getString(3));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return modeOfDelivery;
	}

	@Override
	public int insert(ModeOfDelivery modeOfDelivery) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO modeofdelivery VALUES (null, ?, ?)");
			stmt.setString(1, modeOfDelivery.getName());
			stmt.setString(2, modeOfDelivery.getDescription());
			
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
	public int update(ModeOfDelivery modeOfDelivery) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE modeofdelivery SET name=?, description=? WHERE id=?");
			stmt.setString(1, modeOfDelivery.getName());
			stmt.setString(2, modeOfDelivery.getDescription());
			stmt.setLong(3, modeOfDelivery.getId());
			
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
	public int delete(ModeOfDelivery modeOfDelivery) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM modeofdelivery WHERE id=?");
			stmt.setLong(1, modeOfDelivery.getId());
			
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
