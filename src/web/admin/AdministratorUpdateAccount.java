package web.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

@WebServlet("/admin/UpdateAdministratorAccount.do")
public class AdministratorUpdateAccount extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public AdministratorUpdateAccount() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDao = new UserDAO();
		User administrator = userDao.getAdministrator();
		
		administrator.setUsername(request.getParameter("username"));
		administrator.setPassword(request.getParameter("password"));
		
		int status = userDao.update(administrator);
		
		PrintWriter out = response.getWriter();
		
		if(status > 0) {
			out.println("<div class='alert alert-success alert-dismissable' role='alert'>");
			out.println("Administrator account has been updated.");
			out.println("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
			out.println("</div>");
		}
			
		else {
			out.println("<div class='alert alert-danger alert-dismissable' role='alert'>");
			out.println("An error has occured while updating administrator account.");
			out.println("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
			out.println("</div>");
		}
	}

}
