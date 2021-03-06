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
import dao.LanguageDAO;
import dao.ZoneDAO;


@WebServlet("/admin/EditCountry.do")
public class CountryEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CountryEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CountriesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Country country = new Country();
		
		try {
			country.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			ZoneDAO zoneDao = new ZoneDAO();
			
			country.setZone(zoneDao.getByID(Long.parseLong(request.getParameter("zoneId"))));
			
			if(country.getZone() == null) {
				AlertMessage invalidZone = new AlertMessage();
				invalidZone.setType("warning");
				invalidZone.setMessage("Invalid Zone set.");
				alertMessages.add(invalidZone);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Zone ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			LanguageDAO languageDao = new LanguageDAO();
			
			country.setLanguage(languageDao.getByID(Long.parseLong(request.getParameter("languageId"))));
			
			if(country.getLanguage() == null) {
				AlertMessage invalidLanguage = new AlertMessage();
				invalidLanguage.setType("warning");
				invalidLanguage.setMessage("Invalid Language set.");
				alertMessages.add(invalidLanguage);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Language ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		country.setName(request.getParameter("name"));
		if(country.getName() == null || country.getName().equals("")) {
			AlertMessage invalidCountry = new AlertMessage();
			invalidCountry.setType("warning");
			invalidCountry.setMessage("Invalid Name.");
			alertMessages.add(invalidCountry);

			willProceedToUpdate = false;
		}
		
		country.setCode(request.getParameter("code"));
		if(country.getCode() == null || country.getCode().equals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid Code.");
			alertMessages.add(invalidCode);

			willProceedToUpdate = false;
		}
		
		country.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToUpdate) {
			CountryDAO countryDao = new CountryDAO();
			status = countryDao.update(country);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Country was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Country.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CountriesPanel.jsp");
	}

}
