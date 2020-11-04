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

@WebServlet("/admin/EditTranslation.do")
public class FoodEditTranslationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public FoodEditTranslationServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("FoodsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		FoodTranslation translation = new FoodTranslation();
		
		try {
			translation.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid translation.");
			alertMessages.add(invalidID);

			willProceedToUpdate = false;
		}
		
		try {
			FoodDAO foodDao = new FoodDAO();
			
			long foodId = Long.parseLong(request.getParameter("foodId"));
			translation.setFood(foodDao.getByID(foodId));
			
			if(foodId == 0 || translation.getFood() == null) {
				AlertMessage invalidFood = new AlertMessage();
				invalidFood.setType("warning");
				invalidFood.setMessage("Invalid food.");
				alertMessages.add(invalidFood);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidFoodID = new AlertMessage();
			invalidFoodID.setType("warning");
			invalidFoodID.setMessage("Invalid food id.");
			alertMessages.add(invalidFoodID);
			
			willProceedToUpdate = false;
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
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidLanguageID = new AlertMessage();
			invalidLanguageID.setType("warning");
			invalidLanguageID.setMessage("Invalid language id.");
			alertMessages.add(invalidLanguageID);
			
			willProceedToUpdate = false;
		}
		
		translation.setName(request.getParameter("name"));
		if(translation.getName() == null || translation.getName().equals("")) {
			AlertMessage invalidNameTranslation = new AlertMessage();
			invalidNameTranslation.setType("warning");
			invalidNameTranslation.setMessage("Invalid name translation.");
			alertMessages.add(invalidNameTranslation);
			
			willProceedToUpdate = false;
		}
		
		translation.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToUpdate) {
			FoodTranslationDAO foodTranslationDao = new FoodTranslationDAO();
			status = foodTranslationDao.update(translation);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Food translation was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update translation.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("FoodView.jsp?id=" + translation.getFood().getId());
	}

}
