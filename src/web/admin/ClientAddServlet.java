package web.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import bean.Client;
import dao.ClientDAO;
import dao.UserDAO;

@WebServlet("/admin/AddClient.do")
@MultipartConfig
public class ClientAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ClientAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ClientsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Client client = new Client();
		
		Part imagePart = request.getPart("image");
		String fileType = this.getServletContext().getMimeType(imagePart.getSubmittedFileName());
		if(fileType != null) {
			if(fileType.startsWith("image/")) {
				InputStream is = imagePart.getInputStream();
				
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				
				byte[] data = new byte[1024];
				while((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				
				buffer.flush();
				client.setImage(buffer.toByteArray());
			} else {
				AlertMessage invalidImageFormat = new AlertMessage();
				invalidImageFormat.setType("warning");
				invalidImageFormat.setMessage("Invalid Image Type.");
				alertMessages.add(invalidImageFormat);
			}
		} 
		
		client.setFirstName(request.getParameter("firstName"));
		if(client.getFirstName() == null || client.getFirstName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid First Name.");
			alertMessages.add(invalidFirstName);

			willProceedToInsert = false;
		}
			
		client.setMiddleName(request.getParameter("middleName"));
		if(client.getMiddleName() == null || client.getMiddleName().equals("")) {
			AlertMessage invalidMiddleName = new AlertMessage();
			invalidMiddleName.setType("warning");
			invalidMiddleName.setMessage("Invalid Middle Name.");
			alertMessages.add(invalidMiddleName);

			willProceedToInsert = false;
		}
		
		client.setLastName(request.getParameter("lastName"));
		if(client.getLastName() == null || client.getLastName().equals("")) {
			AlertMessage invalidLastName = new AlertMessage();
			invalidLastName.setType("warning");
			invalidLastName.setMessage("Invalid Last Name.");
			alertMessages.add(invalidLastName);

			willProceedToInsert = false;
		}
		
		client.setAddress(request.getParameter("address"));
		if(client.getAddress() == null || client.getAddress().equals("")) {
			AlertMessage invalidAddress = new AlertMessage();
			invalidAddress.setType("warning");
			invalidAddress.setMessage("Invalid Address.");
			alertMessages.add(invalidAddress);

			willProceedToInsert = false;
		}
		
		try {
			client.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidContact = new AlertMessage();
			invalidContact.setType("warning");
			invalidContact.setMessage("Invalid contact number.");
			alertMessages.add(invalidContact);
			
			willProceedToInsert = false;
		}

		client.setEmailAddress(request.getParameter("emailAddress"));
		if(!client.getEmailAddress().contains("@")) {
			AlertMessage invalidEmail = new AlertMessage();
			invalidEmail.setType("warning");
			invalidEmail.setMessage("Invalid email address.");
			alertMessages.add(invalidEmail);
			
			willProceedToInsert = false;
		}

		client.setUsername(request.getParameter("username"));
		if(client.getUsername().contains(" ")) {
			AlertMessage invalidUsername = new AlertMessage();
			invalidUsername.setType("warning");
			invalidUsername.setMessage("Invalid username.");
			alertMessages.add(invalidUsername);
			
			willProceedToInsert = false;
		} else {
			UserDAO userDao = new UserDAO();
			if(userDao.findSameUsername(null, client.getUsername())) {
				AlertMessage sameUsername = new AlertMessage();
				sameUsername.setType("warning");
				sameUsername.setMessage("User with same username has been found. Please use another.");
				alertMessages.add(sameUsername);
				
				willProceedToInsert = false;
			}
		}

		client.setPassword(request.getParameter("password"));
		if(client.getPassword() == null || client.getPassword().length() < 8) {
			AlertMessage invalidPassword = new AlertMessage();
			invalidPassword.setType("warning");
			invalidPassword.setMessage("Invalid password.");
			alertMessages.add(invalidPassword);
			
			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			ClientDAO clientDao = new ClientDAO();
			status = clientDao.insert(client);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Client was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Client.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ClientsPanel.jsp");
	}

}
