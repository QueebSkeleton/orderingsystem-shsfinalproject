package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import bean.Client;
import bean.Employee;
import bean.ItemOrder;
import bean.Order;
import bean.OrderCoupon;
import util.DBConnection;
import util.OrderStatus;

public class OrderDAO implements Dao<Order> {
	
	@Override
	public List<Order> getAllRecords() {
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM orders ORDER BY dateOrdered DESC");
			
			while(results.next()) {
				Order order = new Order();
				
				order.setId(results.getLong(1));
				
				ClientDAO clientDao = new ClientDAO();
				order.setClient(clientDao.getByID(results.getLong(2)));
				
				EmployeeDAO employeeDao = new EmployeeDAO();
				order.setEmployee(employeeDao.getByID(results.getLong(3)));
				
				order.setDateOrdered(results.getTimestamp(4));
				
				ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
				order.setModeOfDelivery(modeOfDeliveryDao.getByID(results.getLong(5)));
				
				ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
				order.setModeOfPayment(modeOfPaymentDao.getByID(results.getLong(6)));
				
				order.setStatus(OrderStatus.valueOf(results.getString(7)));
				order.setNotes(results.getString(8));
				
				order.setAddress(results.getString(9));
				
				CountryDAO countryDao = new CountryDAO();
				order.setCountry(countryDao.getByID(results.getLong(10)));
				
				ItemOrderDAO itemOrderDao = new ItemOrderDAO();
				order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
				
				OrderCouponDAO orderCouponDao = new OrderCouponDAO();
				order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
				
				orderList.add(order);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	public List<Order> getPendingOrderListWithoutEmployee() {
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM orders WHERE employee_id IS NULL AND status='NEW'");
			
			while(results.next()) {
				Order order = new Order();
				
				order.setId(results.getLong(1));
				
				ClientDAO clientDao = new ClientDAO();
				order.setClient(clientDao.getByID(results.getLong(2)));
				
				order.setDateOrdered(results.getTimestamp(4));
				
				ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
				order.setModeOfDelivery(modeOfDeliveryDao.getByID(results.getLong(5)));
				
				ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
				order.setModeOfPayment(modeOfPaymentDao.getByID(results.getLong(6)));
				
				order.setStatus(OrderStatus.valueOf(results.getString(7)));
				order.setNotes(results.getString(8));
				
				order.setAddress(results.getString(9));
				
				CountryDAO countryDao = new CountryDAO();
				order.setCountry(countryDao.getByID(results.getLong(10)));
				
				ItemOrderDAO itemOrderDao = new ItemOrderDAO();
				order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
				
				OrderCouponDAO orderCouponDao = new OrderCouponDAO();
				order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
				
				order.setNotes(results.getString(8));
				
				orderList.add(order);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	public List<Order> getOrderListByEmployeeAndStatus(Employee employee, OrderStatus status) {
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt;
			
			if(status == OrderStatus.ALL)
				stmt = conn.prepareStatement("SELECT * FROM orders WHERE employee_id=? ORDER BY dateOrdered DESC");
			
			else {
				stmt = conn.prepareStatement("SELECT * FROM orders WHERE employee_id=? AND status=? ORDER BY dateOrdered DESC");
				stmt.setString(2, status.toString());
			}
			
			stmt.setLong(1, employee.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				Order order = new Order();
				
				order.setId(results.getLong(1));
				
				ClientDAO clientDao = new ClientDAO();
				order.setClient(clientDao.getByID(results.getLong(2)));
				
				EmployeeDAO employeeDao = new EmployeeDAO();
				order.setEmployee(employeeDao.getByID(results.getLong(3)));
				
				order.setDateOrdered(results.getTimestamp(4));
				
				ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
				order.setModeOfDelivery(modeOfDeliveryDao.getByID(results.getLong(5)));
				
				ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
				order.setModeOfPayment(modeOfPaymentDao.getByID(results.getLong(6)));
				
				order.setStatus(OrderStatus.valueOf(results.getString(7)));
				order.setNotes(results.getString(8));
				
				order.setAddress(results.getString(9));
				
				CountryDAO countryDao = new CountryDAO();
				order.setCountry(countryDao.getByID(results.getLong(10)));
				
				ItemOrderDAO itemOrderDao = new ItemOrderDAO();
				order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
				
				OrderCouponDAO orderCouponDao = new OrderCouponDAO();
				order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
				
				order.setNotes(results.getString(8));
				
				orderList.add(order);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	public List<Order> getPendingOrderListByClient(Client client) {
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt;
			
			stmt = conn.prepareStatement("SELECT * FROM orders WHERE client_id=? AND (status='NEW' OR status='PROCESSING') ORDER BY dateOrdered DESC");
			stmt.setLong(1, client.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				Order order = new Order();
				
				order.setId(results.getLong(1));
				
				ClientDAO clientDao = new ClientDAO();
				order.setClient(clientDao.getByID(results.getLong(2)));
				
				EmployeeDAO employeeDao = new EmployeeDAO();
				order.setEmployee(employeeDao.getByID(results.getLong(3)));
				
				order.setDateOrdered(results.getTimestamp(4));
				
				ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
				order.setModeOfDelivery(modeOfDeliveryDao.getByID(results.getLong(5)));
				
				ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
				order.setModeOfPayment(modeOfPaymentDao.getByID(results.getLong(6)));
				
				order.setStatus(OrderStatus.valueOf(results.getString(7)));
				order.setNotes(results.getString(8));
				
				order.setAddress(results.getString(9));
				
				CountryDAO countryDao = new CountryDAO();
				order.setCountry(countryDao.getByID(results.getLong(10)));
				
				ItemOrderDAO itemOrderDao = new ItemOrderDAO();
				order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
				
				OrderCouponDAO orderCouponDao = new OrderCouponDAO();
				order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
				
				order.setNotes(results.getString(8));
				
				orderList.add(order);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
	
	public List<Order> getOrderListByStatus(OrderStatus status) {
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			
			if(status == OrderStatus.ALL)
				orderList = getAllRecords();
			
			else {
				Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE status=? ORDER BY dateOrdered DESC");
				stmt.setString(1, status.toString());
				
				ResultSet results = stmt.executeQuery();
				
				while(results.next()) {
					Order order = new Order();
					
					order.setId(results.getLong(1));
					
					ClientDAO clientDao = new ClientDAO();
					order.setClient(clientDao.getByID(results.getLong(2)));
					
					EmployeeDAO employeeDao = new EmployeeDAO();
					order.setEmployee(employeeDao.getByID(results.getLong(3)));
					
					order.setDateOrdered(results.getTimestamp(4));
					
					ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
					order.setModeOfDelivery(modeOfDeliveryDao.getByID(results.getLong(5)));
					
					ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
					order.setModeOfPayment(modeOfPaymentDao.getByID(results.getLong(6)));
					
					order.setStatus(OrderStatus.valueOf(results.getString(7)));
					order.setNotes(results.getString(8));
					
					ItemOrderDAO itemOrderDao = new ItemOrderDAO();
					order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
					
					OrderCouponDAO orderCouponDao = new OrderCouponDAO();
					order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
					
					order.setNotes(results.getString(8));
					
					order.setAddress(results.getString(9));
					
					CountryDAO countryDao = new CountryDAO();
					order.setCountry(countryDao.getByID(results.getLong(10)));
					
					orderList.add(order);
				}
				
				results.close();
				stmt.close();
				conn.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderList;
	}

	@Override
	public Order getByID(long id) {
		Order order = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				order = new Order();
				
				order.setId(result.getLong(1));
				
				ClientDAO clientDao = new ClientDAO();
				order.setClient(clientDao.getByID(result.getLong(2)));
				
				EmployeeDAO employeeDao = new EmployeeDAO();
				order.setEmployee(employeeDao.getByID(result.getLong(3)));
				
				order.setDateOrdered(result.getTimestamp(4));
				
				ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
				order.setModeOfDelivery(modeOfDeliveryDao.getByID(result.getLong(5)));
				
				ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
				order.setModeOfPayment(modeOfPaymentDao.getByID(result.getLong(6)));
				
				order.setStatus(OrderStatus.valueOf(result.getString(7)));
				
				OrderCouponDAO orderCouponDao = new OrderCouponDAO();
				order.setOrderCouponList(orderCouponDao.getOrderCouponListByOrder(order));
				
				order.setNotes(result.getString(8));
				
				order.setAddress(result.getString(9));
				
				CountryDAO countryDao = new CountryDAO();
				order.setCountry(countryDao.getByID(result.getLong(10)));
				
				ItemOrderDAO itemOrderDao = new ItemOrderDAO();
				order.setItemOrderList(itemOrderDao.getItemOrderListByOrder(order));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return order;
	}

	@Override
	public int insert(Order order) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setLong(1, order.getClient().getId());
			
			if(order.getEmployee() == null || order.getEmployee().getId() == 0)
				stmt.setNull(2, Types.BIGINT);
			
			else
				stmt.setLong(2, order.getEmployee().getId());
			
			stmt.setTimestamp(3, order.getDateOrdered());
			
			if(order.getModeOfDelivery() != null && order.getModeOfDelivery().getId() != 0)
				stmt.setLong(4, order.getModeOfDelivery().getId());
			
			else
				stmt.setNull(4, Types.BIGINT);
			
			if(order.getModeOfPayment() != null && order.getModeOfPayment().getId() != 0)
				stmt.setLong(5, order.getModeOfPayment().getId());
			
			else
				stmt.setNull(5, Types.BIGINT);
			
			stmt.setString(6, order.getStatus().toString());
			stmt.setString(7, order.getNotes());
			stmt.setString(8, order.getAddress());
			
			if(order.getCountry() == null || order.getCountry().getId() == 0)
				stmt.setNull(9, Types.BIGINT);
			
			else
				stmt.setLong(9, order.getCountry().getId());
			
			status = stmt.executeUpdate();
			
			ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID()");
			
			long orderId = 0;
			if(result.next()) {
				orderId = result.getLong(1);
			}
			
			if(order.getItemOrderList() != null) {
				
				for(ItemOrder itemOrder : order.getItemOrderList()) {
					stmt = conn.prepareStatement("INSERT INTO itemorder VALUES (null, ?, ?, ?)");
					stmt.setLong(1, orderId);
					stmt.setLong(2, itemOrder.getFood().getId());
					stmt.setInt(3, itemOrder.getQuantity());
					
					stmt.executeUpdate();
				}
			}
			
			if(order.getOrderCouponList() != null) {
				for(OrderCoupon orderCoupon : order.getOrderCouponList()) {
					stmt = conn.prepareStatement("INSERT INTO ordercoupon VALUES (?, ?)");
					stmt.setLong(1, orderId);
					stmt.setLong(2, orderCoupon.getCoupon().getId());
					
					stmt.executeUpdate();
				}
			}
			
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
	public int update(Order order) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE orders SET client_id=?, employee_id=?, dateOrdered=?, modeOfDelivery_id=?, modeOfPayment_id=?, status=?, notes=?, address=?, country_id=? WHERE id=?");
			stmt.setLong(1, order.getClient().getId());
			
			if(order.getEmployee() == null || order.getEmployee().getId() == 0)
				stmt.setNull(2, Types.BIGINT);
			
			else
				stmt.setLong(2, order.getEmployee().getId());

			stmt.setTimestamp(3, order.getDateOrdered());
			stmt.setLong(4, order.getModeOfDelivery().getId());
			stmt.setLong(5, order.getModeOfPayment().getId());
			stmt.setString(6, order.getStatus().toString());
			stmt.setString(7, order.getNotes());
			stmt.setString(8, order.getAddress());
			
			if(order.getCountry() == null || order.getCountry().getId() == 0)
				stmt.setNull(9, Types.BIGINT);
			
			else
				stmt.setLong(9, order.getCountry().getId());
			
			stmt.setLong(10, order.getId());
			
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
	public int delete(Order order) {
		int status = 0;
	
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM orders WHERE id=?");
			stmt.setLong(1, order.getId());
			
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
	
	public long getOrderCount(OrderStatus type) {
		long count = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt;
			
			if(type == OrderStatus.ALL) {
				stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders");				
			} else {
				stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE status=?");
				stmt.setString(1, type.toString());
			}
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next())
				count = result.getLong(1);
			
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
	
	public long getOrderCountByEmployee(Employee employee, OrderStatus status) {
		long count = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = null;
			
			if(status == OrderStatus.ALL)
				stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE employee_id=?");
			
			else {
				stmt = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE employee_id=? AND status=?");
				stmt.setString(2, status.toString());
			}
			
			stmt.setLong(1, employee.getId());
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				count = result.getLong(1);
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
	
}
