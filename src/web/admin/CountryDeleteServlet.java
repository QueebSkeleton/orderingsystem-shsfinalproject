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
import bean.Country;
import dao.CountryDAO;

@WebServlet("/admin/DeleteCountry.do")
public class CountryDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CountryDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CountriesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		Country country = new Country();
		
		try {
			country.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID.");
			alertMessages.add(invalidId);
			
			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			CountryDAO countryDao = new CountryDAO();
			status = countryDao.delete(country);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Country was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete Country.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CountriesPanel.jsp");
	}

}
