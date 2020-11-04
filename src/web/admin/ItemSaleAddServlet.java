package web.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import bean.Employee;
import bean.Food;
import bean.ItemOrder;
import bean.Order;
import dao.OrderDAO;
import util.OrderStatus;

@WebServlet("/admin/AddItemSale.do")
public class ItemSaleAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemSaleAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ItemSalesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Order order = new Order();
		
		order.setClient(new Client());
		order.getClient().setId(Long.parseLong(request.getParameter("clientId")));
		
		order.setEmployee(new Employee());
		order.getEmployee().setId(Long.parseLong(request.getParameter("employeeId")));
		
		try {
			order.setDateOrdered(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(request.getParameter("dateOrdered")).getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		order.setStatus(OrderStatus.DELIVERED);
		
		order.setItemOrderList(new ArrayList<ItemOrder>());
		
		ItemOrder itemOrder = new ItemOrder();
		
		itemOrder.setOrder(order);
		
		itemOrder.setFood(new Food());
		itemOrder.getFood().setId(Long.parseLong(request.getParameter("foodId")));
		
		itemOrder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		
		order.getItemOrderList().add(itemOrder);
		
		OrderDAO orderDao = new OrderDAO();
		orderDao.insert(order);
		
		response.sendRedirect("ItemSalesPanel.jsp");
	}

}
