package model;

import java.util.ArrayList;
import java.util.List;

import bean.Employee;
import bean.Order;
import dao.OrderDAO;
import util.OrderStatus;

public class OrderManager {
	
	private static List<Employee> onlineEmployees = new ArrayList<Employee>();
	
	public static void addOnlineEmployee(Employee employee) {
		onlineEmployees.add(employee);
		assignPendingOrders();
	}
	
	public static void removeOnlineEmployee(Employee employee) {
		onlineEmployees.remove(employee);
	}
	
	public static void assignPendingOrders() {
		OrderDAO orderDao = new OrderDAO();
		
		List<Order> pendingOrders = orderDao.getPendingOrderListWithoutEmployee();
		
		if(pendingOrders.size() > 0 && onlineEmployees.size() > 0) {
			for(Order order : pendingOrders) {
				Employee lowestOrderCount = null;
				
				for(Employee employee : onlineEmployees) {
					if(lowestOrderCount == null)
						lowestOrderCount = employee;
					
					else if(orderDao.getOrderCountByEmployee(employee, OrderStatus.PROCESSING) < orderDao.getOrderCountByEmployee(lowestOrderCount, OrderStatus.PROCESSING))
						lowestOrderCount = employee;
				}
				
				order.setEmployee(lowestOrderCount);
				order.setStatus(OrderStatus.PROCESSING);
				orderDao.update(order);
			}
		}
	}

}
