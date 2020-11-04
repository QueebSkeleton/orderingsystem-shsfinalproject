package web.assets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Food;
import dao.FoodDAO;

@WebServlet("/GetFoodImage")
public class FoodGetImage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public FoodGetImage() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		FoodDAO foodDao = new FoodDAO();
		Food food = foodDao.getByID(Long.parseLong(request.getParameter("id")));
		
		if(food.getImage() != null) {
			OutputStream out = response.getOutputStream();
			out.write(food.getImage());
			out.flush();
			out.close();
		}
	}

}
