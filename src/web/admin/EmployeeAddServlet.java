package web.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.AlertMessage;
import bean.Employee;
import dao.EmployeeDAO;
import dao.UserDAO;

@WebServlet("/admin/AddEmployee.do")
@MultipartConfig
public class EmployeeAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public EmployeeAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("EmployeesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();

		boolean willProceedToInsert = true;
		
		Employee employee = new Employee();
		
		Part imagePart = request.getPart("image");
		InputStream is = imagePart.getInputStream();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		
		byte[] data = new byte[1024];
		while((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		
		buffer.flush();
		employee.setImage(buffer.toByteArray());
		
		employee.setFirstName(request.getParameter("firstName"));
		if(employee.getFirstName() == null || employee.getFirstName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid First Name.");
			alertMessages.add(invalidFirstName);

			willProceedToInsert = false;
		}
		
		employee.setMiddleName(request.getParameter("middleName"));
		if(employee.getMiddleName() == null || employee.getMiddleName().equals("")) {
			AlertMessage invalidMiddleName = new AlertMessage();
			invalidMiddleName.setType("warning");
			invalidMiddleName.setMessage("Invalid Middle Name.");
			alertMessages.add(invalidMiddleName);

			willProceedToInsert = false;
		}
		
		employee.setLastName(request.getParameter("lastName"));
		if(employee.getLastName() == null || employee.getLastName().equals("")) {
			AlertMessage invalidLastName = new AlertMessage();
			invalidLastName.setType("warning");
			invalidLastName.setMessage("Invalid Last Name.");
			alertMessages.add(invalidLastName);

			willProceedToInsert = false;
		}
		
		try {
			employee.setBirthdate(Date.valueOf(request.getParameter("birthdate")));
		} catch(IllegalArgumentException e) {
			AlertMessage invalidBirthdate = new AlertMessage();
			invalidBirthdate.setType("warning");
			invalidBirthdate.setMessage("Invalid Birthdate.");
			alertMessages.add(invalidBirthdate);

			willProceedToInsert = false;
		}
		
		employee.setAddress(request.getParameter("address"));
		if(employee.getAddress() == null || employee.getAddress().equals("")) {
			AlertMessage invalidAddress = new AlertMessage();
			invalidAddress.setType("warning");
			invalidAddress.setMessage("Invalid Address.");
			alertMessages.add(invalidAddress);

			willProceedToInsert = false;
		}
		
		try {
			employee.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidContact = new AlertMessage();
			invalidContact.setType("warning");
			invalidContact.setMessage("Invalid contact number.");
			alertMessages.add(invalidContact);
			
			willProceedToInsert = false;
		}
		
		employee.setEmailAddress(request.getParameter("emailAddress"));
		if(!employee.getEmailAddress().contains("@")) {
			AlertMessage invalidEmail = new AlertMessage();
			invalidEmail.setType("warning");
			invalidEmail.setMessage("Invalid email address.");
			alertMessages.add(invalidEmail);
			
			willProceedToInsert = false;
		}
		
		employee.setUsername(request.getParameter("username"));
		if(employee.getUsername().contains(" ")) {
			AlertMessage invalidUsername = new AlertMessage();
			invalidUsername.setType("warning");
			invalidUsername.setMessage("Invalid username.");
			alertMessages.add(invalidUsername);
			
			willProceedToInsert = false;
		} else {
			UserDAO userDao = new UserDAO();
			if(userDao.findSameUsername(null, employee.getUsername())) {
				AlertMessage sameUsername = new AlertMessage();
				sameUsername.setType("warning");
				sameUsername.setMessage("User with same username has been found. Please use another.");
				alertMessages.add(sameUsername);
				
				willProceedToInsert = false;
			}
		}
		
		employee.setPassword(request.getParameter("password"));
		if(employee.getPassword() == null || employee.getPassword().length() < 8) {
			AlertMessage invalidPassword = new AlertMessage();
			invalidPassword.setType("warning");
			invalidPassword.setMessage("Invalid password.");
			alertMessages.add(invalidPassword);
			
			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			EmployeeDAO employeeDao = new EmployeeDAO();
			status = employeeDao.insert(employee);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Employee was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Employee.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("EmployeesPanel.jsp");
	}

}
