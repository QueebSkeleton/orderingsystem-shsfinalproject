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
import bean.ItemOrder;
import bean.Notification;
import dao.ItemOrderDAO;
import dao.NotificationDAO;
import dao.OrderDAO;
import dao.UserDAO;
import util.NotificationStatus;

@WebServlet("/employee/DeleteItemOrder.do")
public class ItemOrderDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemOrderDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersAssigned.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemOrder itemOrder = new ItemOrder();
		
		itemOrder.setId(Long.parseLong(request.getParameter("id")));

		itemOrder.setOrder(new OrderDAO().getByID(Long.parseLong(request.getParameter("orderId"))));
		
		ItemOrderDAO itemOrderDao = new ItemOrderDAO();
		int status = itemOrderDao.delete(itemOrder);
		
		HttpSession session = request.getSession();
		if(status > 0) {
			session.setAttribute("alertType", "success");
			session.setAttribute("alertMsg", "Item Order has been successfully removed.");
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "There has been an error while removing Item Order.");
		}

		Employee employee = (Employee) session.getAttribute("employee");
		
		Notification notif = new Notification();
		notif.setSender((Employee) session.getAttribute("employee"));
		notif.setRecipient(new UserDAO().getAdministrator());
		notif.setMessage("(Employee) " + employee.getLastName() + ", " + employee.getFirstName() + " just deleted an item from " + itemOrder.getOrder().getClient().getFirstName() + "'s order.");
		notif.setDateCreated(Timestamp.from(Instant.now()));
		notif.setStatus(NotificationStatus.UNREAD);
		
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.insert(notif);
		
		response.sendRedirect("ViewOrder.jsp?id=" + itemOrder.getOrder().getId());
	}

}
