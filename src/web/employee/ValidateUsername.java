package web.employee;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Employee;
import dao.UserDAO;

@WebServlet("/employee/ValidateUsername.do")
public class ValidateUsername extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ValidateUsername() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		HttpSession session = request.getSession();
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		UserDAO userDao = new UserDAO();
		boolean foundSameUsername = userDao.findSameUsername(employee, username);
		
		PrintWriter out = response.getWriter();
		
		if(foundSameUsername) {
			out.print("Username is already taken.");
		} else {
			out.print("Username is ready to use.");
		}
	}

}
