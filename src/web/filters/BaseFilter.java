package web.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ItemOrder;
import bean.OrderCoupon;
import util.Role;

@WebFilter("/*")
public class BaseFilter implements Filter {
	
    public BaseFilter() {
    	
    }
	
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
    @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		HttpSession session = request.getSession();
		
		Role role = (Role) session.getAttribute("userrole");
		
		String uri = request.getRequestURI();
		
		if(uri.contains("GetBannerImage") ||
		   uri.contains("GetClientImage") ||
		   uri.contains("GetEmployeeImage") ||
		   uri.contains("GetFoodImage") ||
		   uri.contains("GetLogo") ||
		   uri.contains("GetOrderInvoice") ||
		   uri.contains("Login.do") ||
		   uri.contains("Logout.do") || 
		   uri.indexOf("assets") > 0)
			chain.doFilter(request, response);
			
		
		else if(role == null || role == Role.CLIENT) {
			if(session.getAttribute("cart") == null)
				session.setAttribute("cart", new ArrayList<ItemOrder>());
			
			if(session.getAttribute("couponList") == null)
				session.setAttribute("couponList", new ArrayList<OrderCoupon>());
			
			chain.doFilter(req, res);
		} else {
			session.removeAttribute("cart");
			
			if(role == Role.ADMINISTRATOR) {
				if(uri.indexOf("admin") > 0)
					chain.doFilter(request, response);
				
				else
					response.sendRedirect(request.getContextPath() + "/admin/Dashboard.jsp");
			} else if(role == Role.EMPLOYEE) {
				if(uri.indexOf("employee") > 0)
					chain.doFilter(request, response);

				else
					response.sendRedirect(request.getContextPath() + "/employee/Dashboard.jsp");
			}
		}
	}
    
    @Override
	public void destroy() {
		
	}

}
