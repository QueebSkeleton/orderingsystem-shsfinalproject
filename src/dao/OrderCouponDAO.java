package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Order;
import bean.OrderCoupon;
import util.DBConnection;

public class OrderCouponDAO {

	public List<OrderCoupon> getOrderCouponListByOrder(Order order) {
		List<OrderCoupon> orderCouponList = new ArrayList<OrderCoupon>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ordercoupon WHERE order_id=?");
			stmt.setLong(1, order.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				OrderCoupon orderCoupon = new OrderCoupon();
				
				orderCoupon.setOrder(order);
				
				CouponDAO couponDao = new CouponDAO();
				orderCoupon.setCoupon(couponDao.getByID(results.getLong(2)));
				
				orderCouponList.add(orderCoupon);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return orderCouponList;
	}
	
	public int insert(OrderCoupon orderCoupon) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO ordercoupon VALUES (?, ?)");
			stmt.setLong(1, orderCoupon.getOrder().getId());
			stmt.setLong(2, orderCoupon.getCoupon().getId());
			
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
	
	public int delete(OrderCoupon orderCoupon) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM ordercoupon WHERE order_id=? AND coupon_id=?");
			stmt.setLong(1, orderCoupon.getOrder().getId());
			stmt.setLong(2, orderCoupon.getCoupon().getId());
			
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
	
	public boolean findSameCouponInOrder(OrderCoupon orderCoupon) {
		boolean hasMatch = false;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ordercoupon WHERE order_id=? AND coupon_id=?");
			stmt.setLong(1, orderCoupon.getOrder().getId());
			stmt.setLong(2, orderCoupon.getCoupon().getId());
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next())
				hasMatch = true;

			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return hasMatch;
	}
	
}
