package web.client;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ItemOrder;

@WebServlet("/DeleteFoodFromCart.do")
public class DeleteFoodFromCartServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public DeleteFoodFromCartServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int foodId = Integer.parseInt(request.getParameter("foodId"));
		
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		ArrayList<ItemOrder> cart = (ArrayList<ItemOrder>) session.getAttribute("cart");
		
		for(int i = 0; i < cart.size(); i++) {
			if(cart.get(i).getFood().getId() == foodId) {
				cart.remove(i);
				break;
			}
		}
		
		response.sendRedirect("Checkout.jsp");
	}

}
