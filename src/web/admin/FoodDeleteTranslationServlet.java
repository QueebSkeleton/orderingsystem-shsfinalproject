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

@WebServlet("/admin/DeleteTranslation.do")
public class FoodDeleteTranslationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public FoodDeleteTranslationServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("FoodsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		FoodTranslation translation = new FoodTranslation();
		
		try {
			translation.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid translation.");
			alertMessages.add(invalidID);

			willProceedToDelete = false;
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
				
				willProceedToDelete = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidFoodID = new AlertMessage();
			invalidFoodID.setType("warning");
			invalidFoodID.setMessage("Invalid food id.");
			alertMessages.add(invalidFoodID);
			
			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			FoodTranslationDAO foodTranslationDao = new FoodTranslationDAO();
			status = foodTranslationDao.delete(translation);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Food translation was successfully removed.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to remove translation.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("FoodView.jsp?id=" + translation.getFood().getId());
	}

}
