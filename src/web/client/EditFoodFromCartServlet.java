package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Food;
import bean.ItemOrder;
import dao.FoodDAO;
import model.StocksHandler;

@WebServlet("/EditFoodFromCart.do")
public class EditFoodFromCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public EditFoodFromCartServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FoodDAO foodDao = new FoodDAO();
		Food food = foodDao.getByID(Long.parseLong(request.getParameter("foodId")));
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<ItemOrder> cart = (List<ItemOrder>) session.getAttribute("cart");
		
		ItemOrder toUpdate = null;
		
		for(ItemOrder itemOrder : cart) {
			if(itemOrder.getFood().getId() == food.getId()) {
				toUpdate = itemOrder;
				break;
			}
		}
		
		int quantityUpdated = toUpdate.getQuantity() - Integer.parseInt(request.getParameter("quantity"));
		toUpdate.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		
		StocksHandler.addOrUpdateFromCart(toUpdate.getFood(), quantityUpdated);
		
		request.setAttribute("cart", cart);
		response.sendRedirect("Menu.jsp?categoryId=" + toUpdate.getFood().getCategory().getId());
	}

}
