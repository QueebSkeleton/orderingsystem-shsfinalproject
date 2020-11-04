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

@WebServlet("/admin/AddItemOrder.do")
public class ItemOrderAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ItemOrderAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("OrdersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		ItemOrder itemOrder = new ItemOrder();
		
		try {
			itemOrder.setOrder(new Order());
			itemOrder.getOrder().setId(Long.parseLong(request.getParameter("orderId")));
			
			if(itemOrder.getOrder().getId() == 0) {
				AlertMessage invalidOrder = new AlertMessage();
				invalidOrder.setType("warning");
				invalidOrder.setMessage("Invalid order.");
				alertMessages.add(invalidOrder);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidOrder = new AlertMessage();
			invalidOrder.setType("warning");
			invalidOrder.setMessage("Invalid order.");
			alertMessages.add(invalidOrder);
			
			willProceedToInsert = false;
		}
		
		try {
			itemOrder.setFood(new Food());
			itemOrder.getFood().setId(Long.parseLong(request.getParameter("foodId")));
			
			if(itemOrder.getFood().getId() == 0) {
				AlertMessage invalidFood = new AlertMessage();
				invalidFood.setType("warning");
				invalidFood.setMessage("Invalid food.");
				alertMessages.add(invalidFood);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidFood = new AlertMessage();
			invalidFood.setType("warning");
			invalidFood.setMessage("Invalid food.");
			alertMessages.add(invalidFood);
			
			willProceedToInsert = false;
		}
		
		try {
			itemOrder.setQuantity(Integer.parseInt(request.getParameter("quantity")));
			
			if(itemOrder.getQuantity() <= 0) {
				AlertMessage invalidQty = new AlertMessage();
				invalidQty.setType("warning");
				invalidQty.setMessage("Invalid quantity.");
				alertMessages.add(invalidQty);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidQty = new AlertMessage();
			invalidQty.setType("warning");
			invalidQty.setMessage("Invalid quantity.");
			alertMessages.add(invalidQty);
			
			willProceedToInsert = false;
		}
		
		if(willProceedToInsert) {
			ItemOrderDAO itemOrderDao = new ItemOrderDAO();
			
			List<ItemOrder> itemOrderList = itemOrderDao.getItemOrderListByOrder(itemOrder.getOrder());
			
			boolean foundSame = false;
			
			for(ItemOrder item : itemOrderList) {
				if(item.getFood().getId() == itemOrder.getFood().getId())
					foundSame = true;
			}
			
			if(foundSame) {
				AlertMessage foundSameMsg = new AlertMessage();
				foundSameMsg.setType("danger");
				foundSameMsg.setMessage("Found same item in this order. Please update that item order instead.");
				alertMessages.add(foundSameMsg);
			} else {
				int status = itemOrderDao.insert(itemOrder);
				
				if(status > 0) {
					AlertMessage uploadSuccess = new AlertMessage();
					uploadSuccess.setType("success");
					uploadSuccess.setMessage("Item was successfully added.");
					alertMessages.add(uploadSuccess);
				} else {
					AlertMessage uploadError = new AlertMessage();
					uploadError.setType("danger");
					uploadError.setMessage("An error has occured while trying to add item.");
					alertMessages.add(uploadError);
				}
			}
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ViewOrder.jsp?id=" + itemOrder.getOrder().getId());
	}

}
