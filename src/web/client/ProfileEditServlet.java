package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import dao.ClientDAO;

@WebServlet("/EditProfile.do")
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
		Client client = (Client) session.getAttribute("client");
		
		client.setFirstName(request.getParameter("firstName"));
		client.setMiddleName(request.getParameter("middleName"));
		client.setLastName(request.getParameter("lastName"));
		client.setAddress(request.getParameter("address"));
		client.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		client.setEmailAddress(request.getParameter("emailAddress"));
		client.setUsername(request.getParameter("username"));
		
		ClientDAO clientDao = new ClientDAO();
		int status = clientDao.update(client);
		
		if(status == 1) {
			session.setAttribute("client", client);
			session.setAttribute("modalTitle", "Success...");
			session.setAttribute("modalMsg", "Your profile has been successfully updated.");
		} else {
			session.setAttribute("modalTitle", "Error...");
			session.setAttribute("modalMsg", "There has been an error while updating your profile.");
		}
		
		response.sendRedirect("index.jsp");
	}

}
