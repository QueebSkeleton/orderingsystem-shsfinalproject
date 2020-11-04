package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Supplier;
import util.DBConnection;

public class SupplierDAO implements Dao<Supplier> {
	
	@Override
	public List<Supplier> getAllRecords() {
		List<Supplier> supplierList = new ArrayList<Supplier>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM supplier");
			
			while(results.next()) {
				Supplier supplier = new Supplier();
				
				supplier.setId(results.getLong(1));
				supplier.setName(results.getString(2));
				supplier.setAddress(results.getString(3));
				supplier.setCity(results.getString(4));
				supplier.setRegion(results.getString(5));
				supplier.setPostalCode(results.getInt(6));
				supplier.setContactNumber(results.getLong(7));
				supplier.setFaxNumber(results.getLong(8));

				supplierList.add(supplier);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return supplierList;
	}

	@Override
	public Supplier getByID(long id) {
		Supplier supplier = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM supplier WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				supplier = new Supplier();
				
				supplier.setId(result.getLong(1));
				supplier.setName(result.getString(2));
				supplier.setAddress(result.getString(3));
				supplier.setCity(result.getString(4));
				supplier.setRegion(result.getString(5));
				supplier.setPostalCode(result.getInt(6));
				supplier.setContactNumber(result.getLong(7));
				supplier.setFaxNumber(result.getLong(8));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return supplier;
	}

	@Override
	public int insert(Supplier supplier) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO supplier VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, supplier.getName());
			stmt.setString(2, supplier.getAddress());
			stmt.setString(3, supplier.getCity());
			stmt.setString(4, supplier.getRegion());
			stmt.setInt(5, supplier.getPostalCode());
			stmt.setLong(6, supplier.getContactNumber());
			stmt.setLong(7, supplier.getFaxNumber());
			
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
	public int update(Supplier supplier) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE supplier SET name=?, address=?, city=?, region=?, postalCode=?, contactNumber=?, faxNumber=? WHERE id=?");
			stmt.setString(1, supplier.getName());
			stmt.setString(2, supplier.getAddress());
			stmt.setString(3, supplier.getCity());
			stmt.setString(4, supplier.getRegion());
			stmt.setInt(5, supplier.getPostalCode());
			stmt.setLong(6, supplier.getContactNumber());
			stmt.setLong(7, supplier.getFaxNumber());
			stmt.setLong(8, supplier.getId());
			
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
	public int delete(Supplier supplier) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM supplier WHERE id=?");
			stmt.setLong(1, supplier.getId());
			
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
