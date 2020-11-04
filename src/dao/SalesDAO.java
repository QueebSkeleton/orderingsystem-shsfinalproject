package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import bean.Employee;
import bean.Food;
import bean.SalesBean;
import util.DBConnection;
import util.TimeFrom;

public class SalesDAO {
	
	public List<SalesBean> getSalesTillNow(TimeFrom timeUnit, int numberFrom) {
		List<SalesBean> salesList = new ArrayList<SalesBean>();
		
		try {
			Connection conn = DBConnection.getConnection();

			Calendar c = Calendar.getInstance();
			
			if(timeUnit == TimeFrom.DAILY) {
				for(int i = numberFrom - 1; i >= 0; i--) {
					c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, -i);
					
					PreparedStatement stmt = conn.prepareStatement("SELECT SUM(food.unitPrice * itemorder.quantity) AS total" + "\n" +
							   "FROM itemorder JOIN food ON food.id = itemorder.food_id" + "\n" +
							   "LEFT JOIN orders ON orders.id = itemorder.order_id" + "\n" +
							   "WHERE MONTH(orders.dateOrdered)=? AND DAY(orders.dateOrdered)=? AND YEAR(orders.dateOrdered)=? AND orders.status = 'DELIVERED'");
					stmt.setInt(1, c.get(Calendar.MONTH) + 1);
					stmt.setInt(2, c.get(Calendar.DAY_OF_MONTH));
					stmt.setInt(3, c.get(Calendar.YEAR));
					
					ResultSet result = stmt.executeQuery();
					
					if(result.next()) {
						SalesBean salesBean = new SalesBean();
						
						salesBean.setDate(c.getTime());
						salesBean.setSales(result.getDouble(1));
						
						salesList.add(salesBean);
					}
					
					result.close();
					stmt.close();
				}
			}
			
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return salesList;
	}
	
	public List<SalesBean> getEmployeeSalesTillNow(Employee employee, TimeFrom timeUnit, int numberFrom) {
		List<SalesBean> salesList = new ArrayList<SalesBean>();
		
		try {
			Connection conn = DBConnection.getConnection();

			Calendar c = Calendar.getInstance();
			
			if(timeUnit == TimeFrom.DAILY) {
				for(int i = numberFrom - 1; i >= 0; i--) {
					c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, -i);
					
					PreparedStatement stmt = conn.prepareStatement("SELECT SUM(food.unitPrice * itemorder.quantity) AS total" + "\n" +
							   "FROM itemorder JOIN food ON food.id = itemorder.food_id" + "\n" +
							   "LEFT JOIN orders ON orders.id = itemorder.order_id" + "\n" +
							   "WHERE MONTH(orders.dateOrdered)=? AND DAY(orders.dateOrdered)=? AND YEAR(orders.dateOrdered)=? AND orders.employee_id=? AND orders.status = 'DELIVERED'");
					stmt.setInt(1, c.get(Calendar.MONTH) + 1);
					stmt.setInt(2, c.get(Calendar.DAY_OF_MONTH));
					stmt.setInt(3, c.get(Calendar.YEAR));
					stmt.setLong(4, employee.getId());
					
					ResultSet result = stmt.executeQuery();
					
					if(result.next()) {
						SalesBean salesBean = new SalesBean();
						
						salesBean.setDate(c.getTime());
						salesBean.setSales(result.getDouble(1));
						
						salesList.add(salesBean);
					}
					
					result.close();
					stmt.close();
				}
			}
			
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return salesList;
	}

	public List<SalesBean> getFoodSalesInFiveMonths(Food food) {
		List<SalesBean> salesList = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = null;
			
			salesList = new ArrayList<SalesBean>();
			
			Date dt = new Date();
			Calendar c = Calendar.getInstance();
			
			for(int i = 4; i >= 0; --i) {
				c.setTime(dt);
				c.add(Calendar.MONTH, -i);
				
				stmt = conn.prepareStatement("SELECT SUM(food.unitPrice * itemorder.quantity) AS total" + "\n" +
						   "FROM itemorder JOIN food ON food.id = itemorder.food_id" + "\n" +
						   "LEFT join orders ON orders.id = itemorder.order_id" + "\n" +
						   "WHERE food.id=? AND MONTH(orders.dateOrdered)=? AND YEAR(orders.dateOrdered)=? AND orders.status = 'DELIVERED'");
				stmt.setLong(1, food.getId());
				stmt.setInt(2, c.get(Calendar.MONTH) + 1);
				stmt.setInt(3, c.get(Calendar.YEAR));
				
				ResultSet result = stmt.executeQuery();
				
				if(result.next()) {
					SalesBean salesBean = new SalesBean();
					
					salesBean.setDate(c.getTime());
					salesBean.setSales(result.getDouble(1));
					
					salesList.add(salesBean);
				}
				
				result.close();
			}
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return salesList;
	}
	
}
