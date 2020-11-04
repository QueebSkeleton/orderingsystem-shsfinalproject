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

@WebServlet("/admin/AddCurrency.do")
public class CurrencyAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CurrencyAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CurrenciesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Currency currency = new Currency();
		
		currency.setCode(request.getParameter("code"));
		if(currency.getCode() == null || currency.getCode().equals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid Code.");
			alertMessages.add(invalidCode);

			willProceedToInsert = false;
		}
		
		currency.setDescription(request.getParameter("description"));
		
		try {
			currency.setExchangeRate(Double.parseDouble(request.getParameter("exchangeRate")));
		} catch(NumberFormatException e) {
			AlertMessage invalidExchange = new AlertMessage();
			invalidExchange.setType("warning");
			invalidExchange.setMessage("Invalid Exchange Rate.");
			alertMessages.add(invalidExchange);

			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			CurrencyDAO currencyDao = new CurrencyDAO();
			status = currencyDao.insert(currency);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Currency was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Currency.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CurrenciesPanel.jsp");
	}

}
