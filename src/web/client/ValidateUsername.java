package web.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Client;
import dao.UserDAO;

@WebServlet("/ValidateUsername.do")
public class ValidateUsername extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ValidateUsername() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
		HttpSession session = request.getSession();
		
		Client client = (Client) session.getAttribute("client");
		
		UserDAO userDao = new UserDAO();
		boolean foundSameUsername = userDao.findSameUsername(client, username);
		
		PrintWriter out = response.getWriter();
		
		if(foundSameUsername) {
			out.print("Username is already taken.");
		} else {
			out.print("Username is ready to use.");
		}
	}

}
