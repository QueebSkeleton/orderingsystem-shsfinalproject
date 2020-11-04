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

@WebServlet("/admin/AddLanguage.do")
public class LanguageAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LanguageAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("LanguagesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Language language = new Language();
		
		language.setCode(request.getParameter("code"));
		if(language.getCode() == null || language.getCode().contentEquals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid code.");
			alertMessages.add(invalidCode);
			
			willProceedToInsert = false;
		}
		
		language.setName(request.getParameter("name"));
		if(language.getName() == null || language.getName().contentEquals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid name.");
			alertMessages.add(invalidCode);
			
			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			LanguageDAO languageDao = new LanguageDAO();
			status = languageDao.insert(language);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Language was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Language.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);

		response.sendRedirect("LanguagesPanel.jsp");
	}

}
