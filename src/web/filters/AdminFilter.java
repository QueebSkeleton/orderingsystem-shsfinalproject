package web.filters;

import java.io.IOException;

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

import util.Role;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
	
    public AdminFilter() {
    	
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
		
		boolean isLoggedIn = (role != null && session.getAttribute("admin") != null);
		boolean isAdmin = (role == Role.ADMINISTRATOR);
		
		String uri = request.getRequestURI();
		
		if(uri.contains("GetBannerImage") ||
		   uri.contains("GetClientImage") ||
		   uri.contains("GetEmployeeImage") ||
		   uri.contains("GetFoodImage") ||
		   uri.contains("GetLogo") ||
		   uri.contains("GetOrderInvoice"))
			chain.doFilter(request, response);
		
		else if(isLoggedIn && isAdmin) {
			if(uri.indexOf("admin") > 0)
				chain.doFilter(request, response);
			
			else
				response.sendRedirect("Dashboard.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/error/Error401.jsp");
		}
	}
    
    @Override
	public void destroy() {
		
	}

}
