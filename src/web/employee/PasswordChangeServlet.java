package web.employee;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Employee;
import dao.EmployeeDAO;

@WebServlet("/employee/ChangePassword.do")
public class PasswordChangeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public PasswordChangeServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Employee employee = (Employee) session.getAttribute("employee");
		employee.setPassword(request.getParameter("newPassword"));
		
		EmployeeDAO employeeDao = new EmployeeDAO();
		employeeDao.update(employee);
		
		session.invalidate();
		
		session = request.getSession(true);
		session.setAttribute("modalTitle", "Message from the server.");
		session.setAttribute("modalMsg", "Password has been changed. Please re-login.");
		
		response.sendRedirect("../index.jsp");
	}

}
