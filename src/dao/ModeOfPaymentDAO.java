package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ModeOfPayment;
import util.DBConnection;

public class ModeOfPaymentDAO implements Dao<ModeOfPayment> {
	
	@Override
	public List<ModeOfPayment> getAllRecords() {
		List<ModeOfPayment> modeOfPaymentList = new ArrayList<ModeOfPayment>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM modeofpayment");
			
			while(results.next()) {
				ModeOfPayment modeOfPayment = new ModeOfPayment();
				
				modeOfPayment.setId(results.getLong(1));
				modeOfPayment.setName(results.getString(2));
				modeOfPayment.setDescription(results.getString(3));
				
				modeOfPaymentList.add(modeOfPayment);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return modeOfPaymentList;
	}

	@Override
	public ModeOfPayment getByID(long id) {
		ModeOfPayment modeOfPayment = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM modeofpayment WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				modeOfPayment = new ModeOfPayment();
				
				modeOfPayment.setId(result.getLong(1));
				modeOfPayment.setName(result.getString(2));
				modeOfPayment.setDescription(result.getString(3));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return modeOfPayment;
	}

	@Override
	public int insert(ModeOfPayment modeOfPayment) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO modeofpayment VALUES (null, ?, ?)");
			stmt.setString(1, modeOfPayment.getName());
			stmt.setString(2, modeOfPayment.getDescription());
			
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
	public int update(ModeOfPayment modeOfPayment) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE modeofpayment SET name=?, description=? WHERE id=?");
			stmt.setString(1, modeOfPayment.getName());
			stmt.setString(2, modeOfPayment.getDescription());
			stmt.setLong(3, modeOfPayment.getId());
			
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
	public int delete(ModeOfPayment modeOfPayment) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM modeofpayment WHERE id=?");
			stmt.setLong(1, modeOfPayment.getId());
			
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
