package web.client;

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

import bean.Coupon;
import bean.OrderCoupon;
import dao.CouponDAO;

@WebServlet("/AddOrderCoupon.do")
public class OrderCouponAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderCouponAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		
		CouponDAO couponDao = new CouponDAO();
		Coupon coupon = couponDao.getByCode(code);
		
		HttpSession session = request.getSession();
		if(coupon != null) {
			if(coupon.getValidUntil().getTime() < Timestamp.from(Instant.now()).getTime()) {
				session.setAttribute("alertType", "warning");
				session.setAttribute("alertMsg", "Unable to place coupon. It is either expired or removed.");
			} else {
				@SuppressWarnings("unchecked")
				List<OrderCoupon> orderCouponList = (List<OrderCoupon>) session.getAttribute("couponList");
				
				OrderCoupon orderCoupon = new OrderCoupon();
				orderCoupon.setCoupon(coupon);
				
				orderCouponList.add(orderCoupon);
				
				session.setAttribute("couponList", orderCouponList);
				
				session.setAttribute("alertType", "success");
				session.setAttribute("alertMsg", "Coupon has been successfully added.");
			}
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "No coupon found with that code.");
		}
		
		response.sendRedirect("Checkout.jsp");
	}

}
