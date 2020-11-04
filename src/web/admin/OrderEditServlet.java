package web.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AlertMessage;
import bean.Order;
import dao.ClientDAO;
import dao.CountryDAO;
import dao.EmployeeDAO;
import dao.ModeOfDeliveryDAO;
import dao.ModeOfPaymentDAO;
import dao.OrderDAO;
import util.OrderStatus;

@WebServlet("/admin/EditOrder.do")
public class OrderEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Order order = new Order();
		
		try {
			order.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			ClientDAO clientDao = new ClientDAO();
			
			long id = Long.parseLong(request.getParameter("clientId"));
			order.setClient(clientDao.getByID(id));
			
			if(order.getClient() == null) {
				AlertMessage invalidClient = new AlertMessage();
				invalidClient.setType("warning");
				invalidClient.setMessage("Invalid Client set.");
				alertMessages.add(invalidClient);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Client ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			EmployeeDAO employeeDao = new EmployeeDAO();
			
			long id = Long.parseLong(request.getParameter("employeeId"));
			order.setEmployee(employeeDao.getByID(id));
			
			if(id != 0 && order.getEmployee() == null) {
				AlertMessage invalidEmployee = new AlertMessage();
				invalidEmployee.setType("warning");
				invalidEmployee.setMessage("Invalid Employee set.");
				alertMessages.add(invalidEmployee);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Employee ID");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			order.setDateOrdered(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(request.getParameter("dateOrdered")).getTime()));
		} catch(IllegalArgumentException e) {
			AlertMessage invalidDate = new AlertMessage();
			invalidDate.setType("warning");
			invalidDate.setMessage("Invalid Date Ordered set.");
			alertMessages.add(invalidDate);
			
			willProceedToUpdate = false;
		} catch (ParseException e) {
			AlertMessage invalidDate = new AlertMessage();
			invalidDate.setType("warning");
			invalidDate.setMessage("Invalid Date Ordered set.");
			alertMessages.add(invalidDate);
			
			willProceedToUpdate = false;
		}
		
		try {
			ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
			
			long id = Long.parseLong(request.getParameter("modeOfPaymentId"));
			order.setModeOfPayment(modeOfPaymentDao.getByID(id));
			
			if(order.getModeOfPayment() == null) {
				AlertMessage invalidMop = new AlertMessage();
				invalidMop.setType("warning");
				invalidMop.setMessage("Invalid Mode of Payment set.");
				alertMessages.add(invalidMop);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Mode of Payment ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}

		try {
			ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
		
			long id = Long.parseLong(request.getParameter("modeOfDeliveryId"));
			order.setModeOfDelivery(modeOfDeliveryDao.getByID(id));
			
			if(order.getModeOfDelivery() == null) {
				AlertMessage invalidMop = new AlertMessage();
				invalidMop.setType("warning");
				invalidMop.setMessage("Invalid Mode of Delivery set.");
				alertMessages.add(invalidMop);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Mode of Delivery ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			order.setStatus(OrderStatus.valueOf(request.getParameter("status")));
		} catch(IllegalArgumentException e) {
			AlertMessage invalidStatus = new AlertMessage();
			invalidStatus.setType("warning");
			invalidStatus.setMessage("Invalid status set.");
			alertMessages.add(invalidStatus);
			
			willProceedToUpdate = false;
		}
		
		order.setNotes(request.getParameter("notes"));
		order.setAddress(request.getParameter("address"));
		
		CountryDAO countryDao = new CountryDAO();
		order.setCountry(countryDao.getByID(Long.parseLong(request.getParameter("countryId"))));
		
		int status = 0;
		
		if(willProceedToUpdate) {
			OrderDAO orderDao = new OrderDAO();
			status = orderDao.update(order);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Order was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Order.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);

		String statusRedirect = request.getParameter("statusRedirect");
		
		if(statusRedirect == null || statusRedirect.equals(""))
			response.sendRedirect("OrdersPanel.jsp");
		
		else
			response.sendRedirect("OrdersPanel.jsp?status=" + statusRedirect);
	}

}
