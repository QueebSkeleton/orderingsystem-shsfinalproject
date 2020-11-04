package web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.StocksHandler;

@WebListener
public class StocksInitializerListener implements ServletContextListener {
	
    public StocksInitializerListener() {
    	
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    	
    }
    
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
    	StocksHandler.loadStocksList();
    }
	
}
