package web.listeners;

import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import bean.ItemOrder;
import model.StocksHandler;

@WebListener
public class ClientSessionListener implements HttpSessionListener {
	
    public ClientSessionListener() {
    	
    }
    
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    	
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		
		@SuppressWarnings("unchecked")
		List<ItemOrder> cart = (List<ItemOrder>) session.getAttribute("cart");
		
		if(cart != null)
			StocksHandler.removeItemOrdersFromCart(cart);
    }
	
}
