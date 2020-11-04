package web.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Setting;
import dao.SettingsDAO;

@WebServlet("/admin/UpdateCompanySettings.do")
public class SettingsUpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SettingsUpdateServlet() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SettingsDAO settingsDao = new SettingsDAO();
		
		Setting setting = settingsDao.getByKey(request.getParameter("keyname"));
		setting.setSetvalue(request.getParameter("setvalue"));
		int status = settingsDao.update(setting);
		
		PrintWriter out = response.getWriter();
		
		if(status > 0) {
			out.println("<div class='alert alert-success alert-dismissable' role='alert'>");
			out.println("Setting has been successfully updated.");
			out.println("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
			out.println("</div>");
		}
			
		else {
			out.println("<div class='alert alert-danger alert-dismissable' role='alert'>");
			out.println("An error has occured while updating setting.");
			out.println("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
			out.println("</div>");
		}
	}

}
