package web.employee;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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

@WebServlet("/employee/AddItemOrder.do")
public class ItemOrderAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemOrderAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersAssigned.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemOrder itemOrder = new ItemOrder();
		
		itemOrder.setOrder(new OrderDAO().getByID(Long.parseLong(request.getParameter("orderId"))));
		itemOrder.setFood(new FoodDAO().getByID(Long.parseLong(request.getParameter("foodId"))));
		
		itemOrder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		
		ItemOrderDAO itemOrderDao = new ItemOrderDAO();
		
		List<ItemOrder> itemOrderList = itemOrderDao.getItemOrderListByOrder(itemOrder.getOrder());
		
		boolean foundSame = false;
		
		for(ItemOrder item : itemOrderList) {
			if(item.getFood().getId() == itemOrder.getFood().getId())
				foundSame = true;
		}

		HttpSession session = request.getSession();
		if(foundSame) {
			session.setAttribute("alertType", "warning");
			session.setAttribute("alertMsg", "Same food item was found while adding. Please edit that item order instead.");
		} else {
			int status = itemOrderDao.insert(itemOrder);
			
			if(status == 1) {
				session.setAttribute("alertType", "success");
				session.setAttribute("alertMsg", "Item has been successfully added.");
			} else {
				session.setAttribute("alertType", "danger");
				session.setAttribute("alertMsg", "There has been an error while adding Item.");
			}
		}
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		Notification notif = new Notification();
		notif.setSender((Employee) session.getAttribute("employee"));
		notif.setRecipient(new UserDAO().getAdministrator());
		notif.setMessage("(Employee) " + employee.getLastName() + ", " + employee.getFirstName() + " just added " + itemOrder.getQuantity() + "x of " + itemOrder.getFood().getName() + " to " + itemOrder.getOrder().getClient().getFirstName() + "'s order.");
		notif.setDateCreated(Timestamp.from(Instant.now()));
		notif.setStatus(NotificationStatus.UNREAD);
		
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.insert(notif);
		
		response.sendRedirect("ViewOrder.jsp?id=" + itemOrder.getOrder().getId());
	}

}
