package web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Coupon;
import bean.Order;
import bean.OrderCoupon;
import dao.OrderCouponDAO;

@WebServlet("/admin/AddOrderCoupon.do")
public class OrderCouponAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderCouponAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderCoupon orderCoupon = new OrderCoupon();
		
		orderCoupon.setOrder(new Order());
		orderCoupon.getOrder().setId(Long.parseLong(request.getParameter("orderId")));
		
		orderCoupon.setCoupon(new Coupon());
		orderCoupon.getCoupon().setId(Long.parseLong(request.getParameter("couponId")));
		
		OrderCouponDAO orderCouponDao = new OrderCouponDAO();
		
		boolean foundSame = orderCouponDao.findSameCouponInOrder(orderCoupon);
		
		HttpSession session = request.getSession();
		if(foundSame) {
			session.setAttribute("alertType", "warning");
			session.setAttribute("alertMsg", "Same coupon was found on the same order.");
		} else {
			int status = orderCouponDao.insert(orderCoupon);
			
			if(status > 0) {
				session.setAttribute("alertType", "success");
				session.setAttribute("alertMsg", "Coupon has been successfully added.");
			} else {
				session.setAttribute("alertType", "danger");
				session.setAttribute("alertMsg", "There has been an error while adding Coupon.");
			}
		}
		
		response.sendRedirect("ViewOrder.jsp?id=" + orderCoupon.getOrder().getId());
	}

}
