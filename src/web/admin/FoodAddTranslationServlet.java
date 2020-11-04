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
import bean.FoodTranslation;
import dao.FoodDAO;
import dao.FoodTranslationDAO;
import dao.LanguageDAO;

@WebServlet("/admin/AddTranslation.do")
public class FoodAddTranslationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public FoodAddTranslationServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("FoodsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		FoodTranslation translation = new FoodTranslation();
		
		try {
			FoodDAO foodDao = new FoodDAO();
			
			long foodId = Long.parseLong(request.getParameter("foodId"));
			translation.setFood(foodDao.getByID(foodId));
			
			if(foodId == 0 || translation.getFood() == null) {
				AlertMessage invalidFood = new AlertMessage();
				invalidFood.setType("warning");
				invalidFood.setMessage("Invalid food.");
				alertMessages.add(invalidFood);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidFoodID = new AlertMessage();
			invalidFoodID.setType("warning");
			invalidFoodID.setMessage("Invalid food id.");
			alertMessages.add(invalidFoodID);
			
			willProceedToInsert = false;
		}
		
		try {
			LanguageDAO languageDao = new LanguageDAO();
			
			long languageId = Long.parseLong(request.getParameter("languageId"));
			translation.setLanguage(languageDao.getByID(languageId));
			
			if(languageId == 0 || translation.getLanguage() == null) {
				AlertMessage invalidLanguage = new AlertMessage();
				invalidLanguage.setType("warning");
				invalidLanguage.setMessage("Invalid language.");
				alertMessages.add(invalidLanguage);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidLanguageID = new AlertMessage();
			invalidLanguageID.setType("warning");
			invalidLanguageID.setMessage("Invalid language id.");
			alertMessages.add(invalidLanguageID);
			
			willProceedToInsert = false;
		}
		
		translation.setName(request.getParameter("name"));
		if(translation.getName() == null || translation.getName().equals("")) {
			AlertMessage invalidNameTranslation = new AlertMessage();
			invalidNameTranslation.setType("warning");
			invalidNameTranslation.setMessage("Invalid name translation.");
			alertMessages.add(invalidNameTranslation);
			
			willProceedToInsert = false;
		}
		
		translation.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToInsert) {
			FoodTranslationDAO foodTranslationDao = new FoodTranslationDAO();
			status = foodTranslationDao.insert(translation);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Food translation was successfully set.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add translation.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("FoodView.jsp?id=" + translation.getFood().getId());
	}

}
