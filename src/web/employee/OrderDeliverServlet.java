package web.employee;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Employee;
import bean.Notification;
import bean.Order;
import dao.NotificationDAO;
import dao.OrderDAO;
import dao.UserDAO;
import util.NotificationStatus;
import util.OrderStatus;

@WebServlet("/employee/DeliverOrder.do")
public class OrderDeliverServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderDeliverServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDao = new OrderDAO();
		Order order = orderDao.getByID(Long.parseLong(request.getParameter("id")));
		
		order.setStatus(OrderStatus.DELIVERED);
		
		int status = orderDao.update(order);
		
		HttpSession session = request.getSession();
		if(status > 0) {
			Notification deliverNotif = new Notification();
			deliverNotif.setSender((Employee) session.getAttribute("employee"));
			deliverNotif.setRecipient(new UserDAO().getAdministrator());
			deliverNotif.setMessage("(Order) " + order.getEmployee().getLastName() + ", " + order.getEmployee().getFirstName() + " just delivered an order.");
			deliverNotif.setDateCreated(Timestamp.from(Instant.now()));
			deliverNotif.setStatus(NotificationStatus.UNREAD);
			
			NotificationDAO notificationDao = new NotificationDAO();
			notificationDao.insert(deliverNotif);
			
			session.setAttribute("alertType", "success");
			session.setAttribute("alertMsg", "Successfully updated status of Order ID " + order.getId() + " to " + order.getStatus().toString() + ".");
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "An error has occured while updating status of Order ID" + order.getId() + ".");
		}
		
		response.sendRedirect("OrdersAssigned.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
