package web.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import bean.Notification;
import bean.Order;
import dao.NotificationDAO;
import dao.OrderDAO;
import dao.UserDAO;
import model.StocksHandler;
import util.NotificationStatus;
import util.OrderStatus;

@WebServlet("/CancelOrder.do")
public class OrderCancelServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderCancelServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDao = new OrderDAO();
		Order order = orderDao.getByID(Long.parseLong(request.getParameter("id")));
		
		order.setStatus(OrderStatus.CANCELLED);
		
		orderDao.update(order);
		StocksHandler.denyOrCancelOrder(order);
		
		HttpSession session = request.getSession();
		
		Notification cancelNotif = new Notification();
		cancelNotif.setSender((Client) session.getAttribute("client"));
		cancelNotif.setRecipient(new UserDAO().getAdministrator());
		cancelNotif.setMessage(order.getClient().getLastName() + ", " + order.getClient().getFirstName() + " cancelled an order.");
		cancelNotif.setDateCreated(Timestamp.from(Instant.now()));
		cancelNotif.setStatus(NotificationStatus.UNREAD);
		
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.insert(cancelNotif);
		
		response.sendRedirect("PendingOrders.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
