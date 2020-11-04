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
import bean.Category;
import dao.CategoryDAO;

@WebServlet("/admin/AddCategory.do")
public class CategoryAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CategoryAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CategoriesPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Category category = new Category();
		
		category.setName(request.getParameter("name"));
		if(category.getName() == null || category.getName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid Name.");
			alertMessages.add(invalidFirstName);

			willProceedToInsert = false;
		}
		
		category.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToInsert) {
			CategoryDAO categoryDao = new CategoryDAO();
			status = categoryDao.insert(category);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Category was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Category.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CategoriesPanel.jsp");
	}

}
