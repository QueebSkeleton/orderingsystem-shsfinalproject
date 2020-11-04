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

@WebServlet("/admin/EditLanguage.do")
public class LanguageEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LanguageEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("LanguagesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Language language = new Language();
		
		try {
			language.setId(Long.parseLong(request.getParameter("id")));
			
			if(language.getId() == 0) {
				AlertMessage invalidID = new AlertMessage();
				invalidID.setType("warning");
				invalidID.setMessage("Invalid ID.");
				alertMessages.add(invalidID);

				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToUpdate = false;
		}
		
		language.setCode(request.getParameter("code"));
		if(language.getCode() == null || language.getCode().contentEquals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid code.");
			alertMessages.add(invalidCode);
			
			willProceedToUpdate = false;
		}
		
		language.setName(request.getParameter("name"));
		if(language.getName() == null || language.getName().contentEquals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid name.");
			alertMessages.add(invalidCode);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			LanguageDAO languageDao = new LanguageDAO();
			status = languageDao.update(language);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Language was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Language.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);

		response.sendRedirect("LanguagesPanel.jsp");
	}

}
