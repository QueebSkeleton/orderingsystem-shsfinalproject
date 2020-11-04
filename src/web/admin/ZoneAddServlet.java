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
import bean.Zone;
import dao.CurrencyDAO;
import dao.ZoneDAO;

@WebServlet("/admin/AddZone.do")
public class ZoneAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ZoneAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ZonesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Zone zone = new Zone();
		
		try {
			CurrencyDAO currencyDao = new CurrencyDAO();
			
			long currencyId = Long.parseLong(request.getParameter("currencyId"));
			zone.setCurrency(currencyDao.getByID(currencyId));
			
			if(currencyId == 0 || zone.getCurrency() == null) {
				AlertMessage invalidCurrency = new AlertMessage();
				invalidCurrency.setType("warning");
				invalidCurrency.setMessage("Invalid currency.");
				alertMessages.add(invalidCurrency);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidCurrency = new AlertMessage();
			invalidCurrency.setType("warning");
			invalidCurrency.setMessage("Invalid currency.");
			alertMessages.add(invalidCurrency);
			
			willProceedToInsert = false;
		}
		
		zone.setName(request.getParameter("name"));
		if(zone.getName() == null || zone.getName().contentEquals("")) {
			AlertMessage invalidName = new AlertMessage();
			invalidName.setType("warning");
			invalidName.setMessage("Invalid name set.");
			alertMessages.add(invalidName);
			
			willProceedToInsert = false;
		}
		
		zone.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToInsert) {
			ZoneDAO zoneDao = new ZoneDAO();
			status = zoneDao.insert(zone);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Zone was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Zone.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ZonesPanel.jsp");
	}

}
