package web.client;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import bean.Country;
import bean.Employee;
import bean.Food;
import bean.ItemOrder;
import bean.Notification;
import bean.Order;
import bean.OrderCoupon;
import dao.FoodDAO;
import dao.ModeOfDeliveryDAO;
import dao.ModeOfPaymentDAO;
import dao.NotificationDAO;
import dao.OrderDAO;
import dao.UserDAO;
import model.OrderManager;
import model.StocksHandler;
import util.NotificationStatus;
import util.OrderStatus;

@WebServlet("/Checkout.do")
public class CheckOutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CheckOutServlet() {
        super();
    }
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Order order = new Order();
		
		order.setClient((Client) session.getAttribute("client"));
		order.setDateOrdered(Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MINUTES)));
		
		order.setEmployee(new Employee());
		order.getEmployee().setId(0);
		
		order.setItemOrderList((List<ItemOrder>) session.getAttribute("cart"));
		order.setOrderCouponList((List<OrderCoupon>) session.getAttribute("couponList"));
		
		ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
		order.setModeOfPayment(modeOfPaymentDao.getByID(Long.parseLong(request.getParameter("modeOfPaymentId"))));

		ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
		order.setModeOfDelivery(modeOfDeliveryDao.getByID(Long.parseLong(request.getParameter("modeOfDeliveryId"))));
		
		order.setStatus(OrderStatus.NEW);
		order.setNotes(request.getParameter("notes"));
		order.setAddress(request.getParameter("address"));
		
		order.setCountry((Country) session.getAttribute("countrySet"));
		
		OrderDAO orderDao = new OrderDAO();
		int status = orderDao.insert(order);
		
		FoodDAO foodDao = new FoodDAO();
		for(ItemOrder itemOrder : order.getItemOrderList()) {
			itemOrder.setOrder(order);
			Food food = itemOrder.getFood();
			
			food.setUnitsInStock(food.getUnitsInStock() - itemOrder.getQuantity());
			foodDao.update(food);
		}
		
		StocksHandler.placeOrder(order);
		OrderManager.assignPendingOrders();
		
		session.removeAttribute("cart");
		
		if(status > 0) {
			Notification orderNotif = new Notification();
			orderNotif.setSender((Client) session.getAttribute("client"));
			orderNotif.setRecipient(new UserDAO().getAdministrator());
			orderNotif.setMessage(order.getClient().getLastName() + ", " + order.getClient().getFirstName() + " placed an order.");
			orderNotif.setDateCreated(Timestamp.from(Instant.now()));
			orderNotif.setStatus(NotificationStatus.UNREAD);
			
			NotificationDAO notificationDao = new NotificationDAO();
			notificationDao.insert(orderNotif);
			
			session.setAttribute("modalTitle", "Message.");
			session.setAttribute("modalMsg", "Thank you for ordering! Please wait while we process your order.");
			
			response.sendRedirect("index.jsp");
		}
	}

}
