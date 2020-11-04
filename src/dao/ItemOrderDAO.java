package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Employee;
import bean.ItemOrder;
import bean.Order;
import util.DBConnection;

public class ItemOrderDAO implements Dao<ItemOrder> {
	
	public List<ItemOrder> getItemOrderListByOrder(Order order) {
		List<ItemOrder> itemOrderList = new ArrayList<ItemOrder>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM itemorder WHERE order_id=?");
			stmt.setLong(1, order.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				ItemOrder itemOrder = new ItemOrder();
				
				itemOrder.setId(results.getLong(1));
				itemOrder.setOrder(order);
				
				FoodDAO foodDao = new FoodDAO();
				itemOrder.setFood(foodDao.getByID(results.getLong(3)));
				
				itemOrder.setQuantity(results.getInt(4));
				
				itemOrderList.add(itemOrder);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return itemOrderList;
	}
	
	public List<ItemOrder> getItemOrderListByEmployee(Employee employee) {
		List<ItemOrder> itemOrderList = new ArrayList<ItemOrder>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT itemorder.* FROM orders " + 
														   "LEFT JOIN itemorder ON itemorder.order_id = orders.id " + 
														   "LEFT JOIN employee ON employee.id = orders.employee_id " +
														   "WHERE employee.id=?");
			stmt.setLong(1, employee.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				ItemOrder itemOrder = new ItemOrder();
				
				itemOrder.setId(results.getLong(1));
				
				OrderDAO orderDao = new OrderDAO();
				itemOrder.setOrder(orderDao.getByID(results.getLong(2)));
				
				FoodDAO foodDao = new FoodDAO();
				itemOrder.setFood(foodDao.getByID(results.getLong(3)));
				
				itemOrder.setQuantity(results.getInt(4));
				
				itemOrderList.add(itemOrder);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return itemOrderList;
	}

	@Override
	public List<ItemOrder> getAllRecords() {
		List<ItemOrder> itemOrderList = new ArrayList<ItemOrder>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT itemorder.* FROM orders " + 
												  "LEFT JOIN itemorder ON itemorder.order_id = orders.id " + 
												  "ORDER BY orders.dateOrdered DESC");
			
			while(results.next()) {
				ItemOrder itemOrder = new ItemOrder();
				
				itemOrder.setId(results.getLong(1));
				
				OrderDAO orderDao = new OrderDAO();
				itemOrder.setOrder(orderDao.getByID(results.getLong(2)));
				
				FoodDAO foodDao = new FoodDAO();
				itemOrder.setFood(foodDao.getByID(results.getLong(3)));
				
				itemOrder.setQuantity(results.getInt(4));
				
				itemOrderList.add(itemOrder);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return itemOrderList;
	}

	@Override
	public ItemOrder getByID(long id) {
		ItemOrder itemOrder = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM itemorder WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				itemOrder = new ItemOrder();
				
				itemOrder.setId(result.getLong(1));
				
				OrderDAO orderDao = new OrderDAO();
				itemOrder.setOrder(orderDao.getByID(result.getLong(2)));
				
				FoodDAO foodDao = new FoodDAO();
				itemOrder.setFood(foodDao.getByID(result.getLong(3)));
				
				itemOrder.setQuantity(result.getInt(4));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return itemOrder;
	}

	@Override
	public int insert(ItemOrder itemOrder) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO itemorder VALUES (null, ?, ?, ?)");
			stmt.setLong(1, itemOrder.getOrder().getId());
			stmt.setLong(2, itemOrder.getFood().getId());
			stmt.setInt(3, itemOrder.getQuantity());
			
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
	public int update(ItemOrder itemOrder) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE itemorder SET order_id=?, food_id=?, quantity=? WHERE id=?");
			stmt.setLong(1, itemOrder.getOrder().getId());
			stmt.setLong(2, itemOrder.getFood().getId());
			stmt.setInt(3, itemOrder.getQuantity());
			stmt.setLong(4, itemOrder.getId());
			
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
	public int delete(ItemOrder itemOrder) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM itemorder WHERE id=?");
			stmt.setLong(1, itemOrder.getId());
			
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
