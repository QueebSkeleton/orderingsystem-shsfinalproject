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
import bean.Food;
import bean.ItemOrder;
import bean.Order;
import dao.ItemOrderDAO;

@WebServlet("/admin/EditItemOrder.do")
public class ItemOrderEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemOrderEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersPanel.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		ItemOrder itemOrder = new ItemOrder();
		
		try {
			itemOrder.setId(Long.parseLong(request.getParameter("id")));
			
			if(itemOrder.getId() == 0) {
				AlertMessage invalidId = new AlertMessage();
				invalidId.setType("warning");
				invalidId.setMessage("Invalid ID set.");
				alertMessages.add(invalidId);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID set.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			itemOrder.setOrder(new Order());
			itemOrder.getOrder().setId(Long.parseLong(request.getParameter("orderId")));
			
			if(itemOrder.getOrder().getId() == 0) {
				AlertMessage invalidOrder = new AlertMessage();
				invalidOrder.setType("warning");
				invalidOrder.setMessage("Invalid order.");
				alertMessages.add(invalidOrder);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidOrder = new AlertMessage();
			invalidOrder.setType("warning");
			invalidOrder.setMessage("Invalid order.");
			alertMessages.add(invalidOrder);
			
			willProceedToUpdate = false;
		}
		
		try {
			itemOrder.setFood(new Food());
			itemOrder.getFood().setId(Long.parseLong(request.getParameter("foodId")));
			
			if(itemOrder.getFood().getId() == 0) {
				AlertMessage invalidFood = new AlertMessage();
				invalidFood.setType("warning");
				invalidFood.setMessage("Invalid food.");
				alertMessages.add(invalidFood);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidFood = new AlertMessage();
			invalidFood.setType("warning");
			invalidFood.setMessage("Invalid food.");
			alertMessages.add(invalidFood);
			
			willProceedToUpdate = false;
		}
		
		try {
			itemOrder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			
			if(itemOrder.getQuantity() <= 0) {
				AlertMessage invalidQty = new AlertMessage();
				invalidQty.setType("warning");
				invalidQty.setMessage("Invalid quantity.");
				alertMessages.add(invalidQty);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidQty = new AlertMessage();
			invalidQty.setType("warning");
			invalidQty.setMessage("Invalid quantity.");
			alertMessages.add(invalidQty);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			ItemOrderDAO itemOrderDao = new ItemOrderDAO();
			status = itemOrderDao.update(itemOrder);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Item was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update item.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ViewOrder.jsp?id=" + itemOrder.getOrder().getId());
	}

}
