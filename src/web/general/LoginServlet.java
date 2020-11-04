package web.general;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import bean.Employee;
import bean.LoginBean;
import bean.User;
import dao.ClientDAO;
import dao.EmployeeDAO;
import dao.LoginDAO;
import util.Role;

@WebServlet("/Login.do")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(request.getParameter("username"));
		loginBean.setPassword(request.getParameter("password"));
		
		LoginDAO loginDao = new LoginDAO();
		Map<User, Role> userMap = loginDao.authenticate(loginBean);
		
		User user = null;
		Role role = null;
		
		for(Map.Entry<User, Role> entry : userMap.entrySet()) {
			user = entry.getKey();
			role = entry.getValue();
			break;
		}

		HttpSession session = request.getSession();
		if(user != null) {
			session.setAttribute("userrole", role);
			
			if(role == Role.ADMINISTRATOR) {
				session.setAttribute("admin", user);
				response.sendRedirect("admin/Dashboard.jsp");
			} else if(role == Role.EMPLOYEE) {
				EmployeeDAO employeeDao = new EmployeeDAO();
				Employee employee = employeeDao.getByID(user.getId());
				
				session.setAttribute("employee", employee);
				response.sendRedirect("employee/Dashboard.jsp");
			} else if(role == Role.CLIENT) {
				ClientDAO clientDao = new ClientDAO();
				Client client = clientDao.getByID(user.getId());
				
				session.setAttribute("client", client);
				
				String redirectURI = (String) session.getAttribute("redirectTo");
				
				if(redirectURI == null || redirectURI.equals(""))
					response.sendRedirect("index.jsp");
				
				else
					response.sendRedirect(redirectURI);
			}
		} else {
			session.setAttribute("modalTitle", "Login Failed");
			session.setAttribute("modalMsg", "Incorrect username or password. Please try again.");
			response.sendRedirect("index.jsp");
		}
	}

}
