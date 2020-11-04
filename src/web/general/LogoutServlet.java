package web.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout.do")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LogoutServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		session = request.getSession(true);
		session.setAttribute("modalTitle", "Message.");
		session.setAttribute("modalMsg", "Successfully logged out.");
		
		response.sendRedirect("index.jsp");
	}

}
