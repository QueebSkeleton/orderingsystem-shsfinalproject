package web.client;

import java.io.IOException;
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

@WebServlet("/DeleteOrderCoupon.do")
public class OrderCouponDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderCouponDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CouponDAO couponDao = new CouponDAO();
		Coupon coupon = couponDao.getByID(Integer.parseInt(request.getParameter("id")));
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<OrderCoupon> orderCouponList = (List<OrderCoupon>) session.getAttribute("couponList");

		for(int i = 0; i < orderCouponList.size(); i++) {
			OrderCoupon ordCoupon = orderCouponList.get(i);
			
			if(ordCoupon.getCoupon().getId() == coupon.getId())
				orderCouponList.remove(i);
		}
		
		session.setAttribute("couponList", orderCouponList);
		
		response.sendRedirect("Checkout.jsp");
	}

}
