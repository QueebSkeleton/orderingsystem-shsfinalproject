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

@WebServlet("/employee/EditProfile.do")
public class ProfileEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ProfileEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("Home.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");
		
		employee.setFirstName(request.getParameter("firstName"));
		employee.setMiddleName(request.getParameter("middleName"));
		employee.setLastName(request.getParameter("lastName"));
		employee.setAddress(request.getParameter("address"));
		employee.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		employee.setEmailAddress(request.getParameter("emailAddress"));
		employee.setUsername(request.getParameter("username"));
		
		EmployeeDAO employeeDao = new EmployeeDAO();
		int status = employeeDao.update(employee);
		
		if(status == 1) {
			session.setAttribute("modalTitle", "Success...");
			session.setAttribute("modalMessage", "Your profile has been successfully updated.");
			session.setAttribute("employee", employee);
		} else {
			session.setAttribute("modalTitle", "Error...");
			session.setAttribute("modalMessage", "There has been an error while updating your profile.");
		}
		
		response.sendRedirect("Dashboard.jsp");
	}

}
