package web.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AlertMessage;
import bean.Employee;
import dao.EmployeeDAO;
import dao.UserDAO;

@WebServlet("/admin/EditEmployee.do")
public class EmployeeEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public EmployeeEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("EmployeesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();

		boolean willProceedToUpdate = true;
		
		Employee employee = new Employee();
		
		try {
			employee.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToUpdate = false;
		}
		
		employee.setFirstName(request.getParameter("firstName"));
		if(employee.getFirstName() == null || employee.getFirstName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid First Name.");
			alertMessages.add(invalidFirstName);

			willProceedToUpdate = false;
		}
		
		employee.setMiddleName(request.getParameter("middleName"));
		if(employee.getMiddleName() == null || employee.getMiddleName().equals("")) {
			AlertMessage invalidMiddleName = new AlertMessage();
			invalidMiddleName.setType("warning");
			invalidMiddleName.setMessage("Invalid Middle Name.");
			alertMessages.add(invalidMiddleName);

			willProceedToUpdate = false;
		}
		
		employee.setLastName(request.getParameter("lastName"));
		if(employee.getLastName() == null || employee.getLastName().equals("")) {
			AlertMessage invalidLastName = new AlertMessage();
			invalidLastName.setType("warning");
			invalidLastName.setMessage("Invalid Last Name.");
			alertMessages.add(invalidLastName);

			willProceedToUpdate = false;
		}
		
		try {
			employee.setBirthdate(Date.valueOf(request.getParameter("birthdate")));
		} catch(IllegalArgumentException e) {
			AlertMessage invalidBirthdate = new AlertMessage();
			invalidBirthdate.setType("warning");
			invalidBirthdate.setMessage("Invalid Birthdate.");
			alertMessages.add(invalidBirthdate);

			willProceedToUpdate = false;
		}
		
		employee.setAddress(request.getParameter("address"));
		if(employee.getAddress() == null || employee.getAddress().equals("")) {
			AlertMessage invalidAddress = new AlertMessage();
			invalidAddress.setType("warning");
			invalidAddress.setMessage("Invalid Address.");
			alertMessages.add(invalidAddress);

			willProceedToUpdate = false;
		}
		
		try {
			employee.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidContact = new AlertMessage();
			invalidContact.setType("warning");
			invalidContact.setMessage("Invalid contact number.");
			alertMessages.add(invalidContact);
			
			willProceedToUpdate = false;
		}
		
		employee.setEmailAddress(request.getParameter("emailAddress"));
		if(!employee.getEmailAddress().contains("@")) {
			AlertMessage invalidEmail = new AlertMessage();
			invalidEmail.setType("warning");
			invalidEmail.setMessage("Invalid email address.");
			alertMessages.add(invalidEmail);
			
			willProceedToUpdate = false;
		}
		
		employee.setUsername(request.getParameter("username"));
		if(employee.getUsername().contains(" ")) {
			AlertMessage invalidUsername = new AlertMessage();
			invalidUsername.setType("warning");
			invalidUsername.setMessage("Invalid username.");
			alertMessages.add(invalidUsername);
			
			willProceedToUpdate = false;
		} else {
			UserDAO userDao = new UserDAO();
			if(userDao.findSameUsername(employee, employee.getUsername())) {
				AlertMessage sameUsername = new AlertMessage();
				sameUsername.setType("warning");
				sameUsername.setMessage("User with same username has been found. Please use another.");
				alertMessages.add(sameUsername);
				
				willProceedToUpdate = false;
			}
		}
		
		employee.setPassword(request.getParameter("password"));
		if(employee.getPassword() == null || employee.getPassword().length() < 8) {
			AlertMessage invalidPassword = new AlertMessage();
			invalidPassword.setType("warning");
			invalidPassword.setMessage("Invalid password.");
			alertMessages.add(invalidPassword);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			EmployeeDAO employeeDao = new EmployeeDAO();
			status = employeeDao.update(employee);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Employee was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Employee.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("EmployeesPanel.jsp");
	}

}
