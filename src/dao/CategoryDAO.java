package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Category;
import util.DBConnection;

public class CategoryDAO implements Dao<Category> {

	@Override
	public List<Category> getAllRecords() {
		List<Category> categoryList = new ArrayList<Category>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM category");
			
			while(results.next()) {
				Category category = new Category();
				
				category.setId(results.getLong(1));
				category.setName(results.getString(2));
				category.setDescription(results.getString(3));
				
				categoryList.add(category);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return categoryList;
	}

	@Override
	public Category getByID(long id) {
		Category category = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM category WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				category = new Category();
				
				category.setId(result.getLong(1));
				category.setName(result.getString(2));
				category.setDescription(result.getString(3));
			}

			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return category;
	}

	@Override
	public int insert(Category category) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO category VALUES (null, ?, ?)");
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			
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
	public int update(Category category) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE category SET name=?, description=? WHERE id=?");
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			stmt.setLong(3, category.getId());
			
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
	public int delete(Category category) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM category WHERE id=?");
			stmt.setLong(1, category.getId());
			
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
