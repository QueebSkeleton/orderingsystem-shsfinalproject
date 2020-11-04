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
import bean.Currency;
import dao.CurrencyDAO;

@WebServlet("/admin/DeleteCurrency.do")
public class CurrencyDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CurrencyDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CurrenciesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		Currency currency = new Currency();
		
		try {
			currency.setId(Long.parseLong(request.getParameter("id")));
			
			if(currency.getId() == 0) {
				AlertMessage invalidId = new AlertMessage();
				invalidId.setType("warning");
				invalidId.setMessage("Invalid ID.");
				alertMessages.add(invalidId);

				willProceedToDelete = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID.");
			alertMessages.add(invalidId);

			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			CurrencyDAO currencyDao = new CurrencyDAO();
			status = currencyDao.delete(currency);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Currency was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete Currency.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CurrenciesPanel.jsp");
	}

}
