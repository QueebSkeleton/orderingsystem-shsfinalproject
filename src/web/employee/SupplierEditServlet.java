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

import bean.Employee;
import bean.Notification;
import bean.Supplier;
import dao.NotificationDAO;
import dao.SupplierDAO;
import dao.UserDAO;
import util.NotificationStatus;

@WebServlet("/employee/EditSupplier.do")
public class SupplierEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SupplierEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("SuppliersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Supplier supplier = new Supplier();
		supplier.setId(Long.parseLong(request.getParameter("id")));
		supplier.setName(request.getParameter("name"));
		supplier.setAddress(request.getParameter("address"));
		supplier.setCity(request.getParameter("city"));
		supplier.setRegion(request.getParameter("region"));
		supplier.setPostalCode(Integer.parseInt(request.getParameter("postalCode")));
		supplier.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		supplier.setFaxNumber(Long.parseLong(request.getParameter("faxNumber")));
		
		SupplierDAO supplierDao = new SupplierDAO();
		int status = supplierDao.update(supplier);
		
		HttpSession session = request.getSession();
		if(status > 0) {
			Employee employee = (Employee) session.getAttribute("employee");
			
			Notification notif = new Notification();
			notif.setSender((Employee) session.getAttribute("employee"));
			notif.setRecipient(new UserDAO().getAdministrator());
			notif.setMessage("(Employee) " + employee.getLastName() + ", " + employee.getFirstName() + " just updated a client's details.");
			notif.setDateCreated(Timestamp.from(Instant.now()));
			notif.setStatus(NotificationStatus.UNREAD);
			
			NotificationDAO notificationDao = new NotificationDAO();
			notificationDao.insert(notif);
			
			session.setAttribute("alertType", "success");
			session.setAttribute("alertMsg", "Supplier with ID: " + supplier.getId() + " has been successfully updated.");
		} else {
			session.setAttribute("alertType", "danger");
			session.setAttribute("alertMsg", "There has been an error while updating Supplier with ID: " + supplier.getId());
		}
		
		response.sendRedirect("SuppliersPanel.jsp");
	}

}
