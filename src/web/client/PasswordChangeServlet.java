package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import dao.ClientDAO;

@WebServlet("/ChangePassword.do")
public class PasswordChangeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public PasswordChangeServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Client client = (Client) session.getAttribute("client");
		client.setPassword(request.getParameter("newPassword"));
		
		ClientDAO clientDao = new ClientDAO();
		clientDao.update(client);
		
		session.invalidate();
		
		session = request.getSession(true);
		session.setAttribute("modalTitle", "Message from the server.");
		session.setAttribute("modalMsg", "Password has been changed. Please re-login.");
		
		response.sendRedirect("index.jsp");
	}

}
