package web.employee;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import bean.Employee;
import bean.Notification;
import dao.ClientDAO;
import dao.NotificationDAO;
import dao.UserDAO;
import util.NotificationStatus;

@WebServlet("/employee/EditClient.do")
public class ClientEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ClientEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ClientsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ClientDAO clientDao = new ClientDAO();
		
		Client client = clientDao.getByID(Long.parseLong(request.getParameter("id")));
		client.setFirstName(request.getParameter("firstName"));
		client.setMiddleName(request.getParameter("middleName"));
		client.setLastName(request.getParameter("lastName"));
		client.setAddress(request.getParameter("address"));
		client.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		client.setEmailAddress(request.getParameter("emailAddress"));
		
		int status = clientDao.update(client);
		
		HttpSession session = request.getSession();
		if(status > 0) {
			session.setAttribute("alertType", "success");
			session.setAttribute("alertMsg", "Client with ID: " + client.getId() + " has been successfully updated.");
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "There has been an error while updating Client with ID: " + client.getId());
		}
		
		Employee employee = (Employee) session.getAttribute("employee");

		Notification notif = new Notification();
		notif.setSender((Employee) session.getAttribute("employee"));
		notif.setRecipient(new UserDAO().getAdministrator());
		notif.setMessage("(Employee) " + employee.getLastName() + ", " + employee.getFirstName() + " just updated a client's details.");
		notif.setDateCreated(Timestamp.from(Instant.now()));
		notif.setStatus(NotificationStatus.UNREAD);
		
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.insert(notif);
		
		response.sendRedirect("ClientsPanel.jsp");
	}

}
