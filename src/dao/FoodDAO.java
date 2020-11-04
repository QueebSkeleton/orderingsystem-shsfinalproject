package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bean.Category;
import bean.Food;
import bean.StocksBean;
import util.DBConnection;

public class FoodDAO implements Dao<Food> {
	
	@Override
	public List<Food> getAllRecords() {
		List<Food> foodList = new ArrayList<Food>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM food");
			
			while(results.next()) {
				Food food = new Food();
				
				food.setId(results.getLong(1));
			
				SupplierDAO supplierDao = new SupplierDAO();
				food.setSupplier(supplierDao.getByID(results.getLong(2)));
				
				food.setImage(results.getBytes(3));
				food.setName(results.getString(4));

				CategoryDAO categoryDao = new CategoryDAO();
				food.setCategory(categoryDao.getByID(results.getLong(5)));
				
				food.setDescription(results.getString(6));
				food.setUnitsInStock(results.getInt(7));
				food.setUnitPrice(results.getDouble(8));
				
				foodList.add(food);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return foodList;
	}
	
	public List<Food> getFoodListByCategory(Category category) {
		List<Food> foodList = new ArrayList<Food>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM food WHERE category_id=?");
			stmt.setLong(1, category.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				Food food = new Food();
				
				food.setId(results.getLong(1));
			
				SupplierDAO supplierDao = new SupplierDAO();
				food.setSupplier(supplierDao.getByID(results.getLong(2)));

				food.setImage(results.getBytes(3));
				food.setName(results.getString(4));
				
				CategoryDAO categoryDao = new CategoryDAO();
				food.setCategory(categoryDao.getByID(results.getLong(5)));
				
				food.setDescription(results.getString(6));
				food.setUnitsInStock(results.getInt(7));
				food.setUnitPrice(results.getDouble(8));
				
				foodList.add(food);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return foodList;
	}
	
	public List<StocksBean> getStocksBeanList() {
		List<StocksBean> stocksBeanList = new ArrayList<StocksBean>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			for(Food food : getAllRecords()) {
				StocksBean stocksBean = new StocksBean();
				stocksBean.setFood(food);
				
				stocksBeanList.add(stocksBean);
			}
												  
			ResultSet results = stmt.executeQuery("SELECT food.id, SUM(itemorder.quantity) FROM food" + "\n" +
												  "LEFT JOIN itemorder ON itemorder.food_id = food.id" + "\n" +
												  "INNER JOIN orders ON orders.id = itemorder.order_id" + "\n" +
												  "WHERE orders.status = 'NEW' OR orders.status = 'PROCESSING'" + "\n" +
												  "GROUP BY food.id");
			
			while(results.next()) {
				for(StocksBean stocksBean : stocksBeanList) {
					if(stocksBean.getFood().getId() == results.getLong(1))
						stocksBean.setPendingStocks(results.getInt(2));
				}
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return stocksBeanList;
	}
	
	
	public List<Food> getOneFoodEachCategory() {
		List<Food> foodList = new ArrayList<Food>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			CategoryDAO categoryDao = new CategoryDAO();
			List<Category> categoryList = categoryDao.getAllRecords();
			
			for(Category category : categoryList) {
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM food WHERE category_id=? AND image IS NOT NULL LIMIT 1");
				stmt.setLong(1, category.getId());
				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					Food food = new Food();
					
					food.setId(result.getLong(1));
				
					SupplierDAO supplierDao = new SupplierDAO();
					food.setSupplier(supplierDao.getByID(result.getLong(2)));

					food.setImage(result.getBytes(3));
					food.setName(result.getString(4));
					food.setCategory(category);
					food.setDescription(result.getString(6));
					food.setUnitsInStock(result.getInt(7));
					food.setUnitPrice(result.getDouble(8));
					
					foodList.add(food);
				}
				
				result.close();
				stmt.close();
			}
			
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return foodList;
	}
	
	
	public int getFoodCountByCategory(Category category) {
		int count = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM food WHERE category_id=?");
			stmt.setLong(1, category.getId());
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				count = result.getInt(1);
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public Food getByID(long id) {
		Food food = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM food WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				food = new Food();
				
				food.setId(result.getLong(1));
			
				SupplierDAO supplierDao = new SupplierDAO();
				food.setSupplier(supplierDao.getByID(result.getLong(2)));

				food.setImage(result.getBytes(3));
				food.setName(result.getString(4));
				
				CategoryDAO categoryDao = new CategoryDAO();
				food.setCategory(categoryDao.getByID(result.getLong(5)));
				
				food.setDescription(result.getString(6));
				food.setUnitsInStock(result.getInt(7));
				food.setUnitPrice(result.getDouble(8));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return food;
	}

	@Override
	public int insert(Food food) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO food VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
			
			if(food.getSupplier() == null || food.getSupplier().getId() == 0)
				stmt.setNull(1, Types.BIGINT);
			
			else
				stmt.setLong(1, food.getSupplier().getId());
			
			stmt.setBytes(2, food.getImage());
			stmt.setString(3, food.getName());
			stmt.setLong(4, food.getCategory().getId());
			stmt.setString(5, food.getDescription());
			stmt.setInt(6, food.getUnitsInStock());
			stmt.setDouble(7, food.getUnitPrice());
			
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
	public int update(Food food) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE food SET supplier_id=?, name=?, category_id=?, description=?, unitsInStock=?, unitPrice=? WHERE id=?");

			if(food.getSupplier() == null || food.getSupplier().getId() == 0)
				stmt.setNull(1, Types.BIGINT);
			
			else
				stmt.setLong(1, food.getSupplier().getId());

			stmt.setString(2, food.getName());
			stmt.setLong(3, food.getCategory().getId());
			stmt.setString(4, food.getDescription());
			stmt.setInt(5, food.getUnitsInStock());
			stmt.setDouble(6, food.getUnitPrice());
			stmt.setLong(7, food.getId());
			
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
	
	public int updateImage(Food food) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE food SET image=? WHERE id=?");
			stmt.setBytes(1, food.getImage());
			stmt.setLong(2, food.getId());
			
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
	public int delete(Food food) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM food WHERE id=?");
			stmt.setLong(1, food.getId());
			
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
