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
import dao.FoodDAO;
import dao.ItemOrderDAO;
import dao.NotificationDAO;
import dao.OrderDAO;
import dao.UserDAO;
import util.NotificationStatus;

@WebServlet("/employee/EditItemOrder.do")
public class ItemOrderEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemOrderEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersAssigned.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemOrder itemOrder = new ItemOrder();
		
		itemOrder.setId(Long.parseLong(request.getParameter("id")));
		
		itemOrder.setOrder(new OrderDAO().getByID(Long.parseLong(request.getParameter("orderId"))));
		itemOrder.setFood(new FoodDAO().getByID(Long.parseLong(request.getParameter("foodId"))));
		
		itemOrder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		
		ItemOrderDAO itemOrderDao = new ItemOrderDAO();
		int status = itemOrderDao.update(itemOrder);
		
		HttpSession session = request.getSession();
		if(status > 0) {
			session.setAttribute("alertType", "success");
			session.setAttribute("alertMsg", "Item Order has been successfully updated.");
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "There has been an error while updating Item Order.");
		}
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		Notification notif = new Notification();
		notif.setSender((Employee) session.getAttribute("employee"));
		notif.setRecipient(new UserDAO().getAdministrator());
		notif.setMessage("(Employee) " + employee.getLastName() + ", " + employee.getFirstName() + " just updated an item quantity from " + itemOrder.getOrder().getClient().getFirstName() + "'s order.");
		notif.setDateCreated(Timestamp.from(Instant.now()));
		notif.setStatus(NotificationStatus.UNREAD);
		
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.insert(notif);
		
		response.sendRedirect("ViewOrder.jsp?id=" + itemOrder.getOrder().getId());
	}

}
