package web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NotificationDAO;

@WebServlet("/admin/UpdateNotificationStatus.do")
public class NotificationUpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public NotificationUpdateServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NotificationDAO notificationDao = new NotificationDAO();
		notificationDao.seeAllNotifications();
	}

}
