package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Coupon;
import util.DBConnection;

public class CouponDAO implements Dao<Coupon> {
	
	@Override
	public List<Coupon> getAllRecords() {
		List<Coupon> couponList = new ArrayList<Coupon>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM coupon");
			
			while(results.next()) {
				Coupon coupon = new Coupon();
				
				coupon.setId(results.getLong(1));
				coupon.setDateCreated(results.getTimestamp(2));
				coupon.setValidUntil(results.getTimestamp(3));
				coupon.setCode(results.getString(4));
				coupon.setDiscountAmount(results.getDouble(5));
				
				couponList.add(coupon);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return couponList;
	}
	
	@Override
	public Coupon getByID(long id) {
		Coupon coupon = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM coupon WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				coupon = new Coupon();
				
				coupon.setId(result.getLong(1));
				coupon.setDateCreated(result.getTimestamp(2));
				coupon.setValidUntil(result.getTimestamp(3));
				coupon.setCode(result.getString(4));
				coupon.setDiscountAmount(result.getDouble(5));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return coupon;
	}
	
	public Coupon getByCode(String code) {
		Coupon coupon = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM coupon WHERE code=?");
			stmt.setString(1, code);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				coupon = new Coupon();
				
				coupon.setId(result.getLong(1));
				coupon.setDateCreated(result.getTimestamp(2));
				coupon.setValidUntil(result.getTimestamp(3));
				coupon.setCode(result.getString(4));
				coupon.setDiscountAmount(result.getDouble(5));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return coupon;
	}

	@Override
	public int insert(Coupon coupon) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO coupon VALUES (null, ?, ?, ?, ?)");
			stmt.setTimestamp(1, coupon.getDateCreated());
			stmt.setTimestamp(2, coupon.getValidUntil());
			stmt.setString(3, coupon.getCode());
			stmt.setDouble(4, coupon.getDiscountAmount());
			
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
	public int update(Coupon coupon) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE coupon SET dateCreated=?, validUntil=?, code=?, discountAmount=? WHERE id=?");
			stmt.setTimestamp(1, coupon.getDateCreated());
			stmt.setTimestamp(2, coupon.getValidUntil());
			stmt.setString(3, coupon.getCode());
			stmt.setDouble(4, coupon.getDiscountAmount());
			stmt.setLong(5, coupon.getId());
			
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
	public int delete(Coupon coupon) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM coupon WHERE id=?");
			stmt.setLong(1, coupon.getId());
			
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
