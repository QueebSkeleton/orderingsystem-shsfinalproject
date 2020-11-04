package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ModeOfDelivery;
import bean.ModeOfPayment;
import bean.Order;
import dao.OrderDAO;

@WebServlet("/UpdateOrder.do")
public class OrderUpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderUpdateServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDao = new OrderDAO();
		Order order = orderDao.getByID(Long.parseLong(request.getParameter("id")));
		
		order.setModeOfPayment(new ModeOfPayment());
		order.getModeOfPayment().setId(Long.parseLong(request.getParameter("modeOfPaymentId")));

		order.setModeOfDelivery(new ModeOfDelivery());
		order.getModeOfDelivery().setId(Long.parseLong(request.getParameter("modeOfDeliveryId")));
		
		order.setNotes(request.getParameter("notes"));
		
		int status = orderDao.update(order);
		
		if(status > 0)
			response.sendRedirect("PendingOrders.jsp");
	}

}
