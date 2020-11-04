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

@WebServlet("/employee/ValidatePassword.do")
public class ValidateOldPassword extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ValidateOldPassword() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		Employee employee = (Employee) session.getAttribute("employee");
		boolean isSame = employee.getPassword().equals(password);
		
		PrintWriter out = response.getWriter();
		
		if(!isSame) {
			out.print("Current password does not match.");
		} else {
			out.print("Password: OK");
		}
	}

}
