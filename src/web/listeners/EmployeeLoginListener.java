package web.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import bean.Employee;
import model.OrderManager;

@WebListener
public class EmployeeLoginListener implements HttpSessionListener, HttpSessionAttributeListener {
	
    public EmployeeLoginListener() {
    	
    }
    
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
    	if(event.getValue() instanceof Employee) {
    		Employee employee = (Employee) event.getValue();
    		OrderManager.addOnlineEmployee(employee);
    	}
    }
    
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    	
    }
    
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    	
    }

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		
		Employee employee = (Employee) session.getAttribute("employee");
		
		if(employee != null) {
			OrderManager.removeOnlineEmployee(employee);
		}
	}
	
}
