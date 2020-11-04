package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ItemOrder;
import dao.FoodDAO;
import model.StocksHandler;

@WebServlet("/AddFoodToCart.do")
public class AddFoodToCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public AddFoodToCartServlet() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ItemOrder itemorder = new ItemOrder();
		
		FoodDAO foodDao = new FoodDAO();
		itemorder.setFood(foodDao.getByID(Long.parseLong(request.getParameter("foodId"))));
		
		int quantityAdded = itemorder.getQuantity() - Integer.parseInt(request.getParameter("quantity"));
		itemorder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<ItemOrder> cart = (List<ItemOrder>) session.getAttribute("cart");
		
		cart.add(itemorder);

		StocksHandler.addOrUpdateFromCart(itemorder.getFood(), quantityAdded);
		
		request.setAttribute("cart", cart);
		response.sendRedirect("Menu.jsp?categoryId=" + itemorder.getFood().getCategory().getId());
	}

}
