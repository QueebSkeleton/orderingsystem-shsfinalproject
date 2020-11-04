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
import bean.Language;
import dao.LanguageDAO;

@WebServlet("/admin/DeleteLanguage.do")
public class LanguageDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LanguageDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("LanguagesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		Language language = new Language();
		
		try {
			language.setId(Long.parseLong(request.getParameter("id")));
			
			if(language.getId() == 0) {
				AlertMessage invalidID = new AlertMessage();
				invalidID.setType("warning");
				invalidID.setMessage("Invalid ID.");
				alertMessages.add(invalidID);

				willProceedToDelete = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			LanguageDAO languageDao = new LanguageDAO();
			status = languageDao.delete(language);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Language was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete Language.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);

		response.sendRedirect("LanguagesPanel.jsp");
	}

}
