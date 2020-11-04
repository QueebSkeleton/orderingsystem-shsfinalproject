package web.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AlertMessage;
import bean.Client;
import dao.ClientDAO;
import dao.UserDAO;

@WebServlet("/admin/EditClient.do")
public class ClientEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ClientEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ClientsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Client client = new Client();
		
		try {
			client.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToUpdate = false;
		}
		
		client.setFirstName(request.getParameter("firstName"));
		if(client.getFirstName() == null || client.getFirstName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid First Name.");
			alertMessages.add(invalidFirstName);

			willProceedToUpdate = false;
		}
			
		client.setMiddleName(request.getParameter("middleName"));
		if(client.getMiddleName() == null || client.getMiddleName().equals("")) {
			AlertMessage invalidMiddleName = new AlertMessage();
			invalidMiddleName.setType("warning");
			invalidMiddleName.setMessage("Invalid Middle Name.");
			alertMessages.add(invalidMiddleName);

			willProceedToUpdate = false;
		}
		
		client.setLastName(request.getParameter("lastName"));
		if(client.getLastName() == null || client.getLastName().equals("")) {
			AlertMessage invalidLastName = new AlertMessage();
			invalidLastName.setType("warning");
			invalidLastName.setMessage("Invalid Last Name.");
			alertMessages.add(invalidLastName);

			willProceedToUpdate = false;
		}
		
		client.setAddress(request.getParameter("address"));
		if(client.getAddress() == null || client.getAddress().equals("")) {
			AlertMessage invalidAddress = new AlertMessage();
			invalidAddress.setType("warning");
			invalidAddress.setMessage("Invalid Address.");
			alertMessages.add(invalidAddress);

			willProceedToUpdate = false;
		}
		
		try {
			client.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidContact = new AlertMessage();
			invalidContact.setType("warning");
			invalidContact.setMessage("Invalid contact number.");
			alertMessages.add(invalidContact);
			
			willProceedToUpdate = false;
		}

		client.setEmailAddress(request.getParameter("emailAddress"));
		if(!client.getEmailAddress().contains("@")) {
			AlertMessage invalidEmail = new AlertMessage();
			invalidEmail.setType("warning");
			invalidEmail.setMessage("Invalid email address.");
			alertMessages.add(invalidEmail);
			
			willProceedToUpdate = false;
		}

		client.setUsername(request.getParameter("username"));
		if(client.getUsername().contains(" ")) {
			AlertMessage invalidUsername = new AlertMessage();
			invalidUsername.setType("warning");
			invalidUsername.setMessage("Invalid username.");
			alertMessages.add(invalidUsername);
			
			willProceedToUpdate = false;
		} else {
			UserDAO userDao = new UserDAO();
			if(userDao.findSameUsername(client, client.getUsername())) {
				AlertMessage sameUsername = new AlertMessage();
				sameUsername.setType("warning");
				sameUsername.setMessage("User with same username has been found. Please use another.");
				alertMessages.add(sameUsername);
				
				willProceedToUpdate = false;
			}
		}

		client.setPassword(request.getParameter("password"));
		if(client.getPassword() == null || client.getPassword().length() < 8) {
			AlertMessage invalidPassword = new AlertMessage();
			invalidPassword.setType("warning");
			invalidPassword.setMessage("Invalid password.");
			alertMessages.add(invalidPassword);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			ClientDAO clientDao = new ClientDAO();
			status = clientDao.update(client);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Client was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Client.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ClientsPanel.jsp");
	}

}
