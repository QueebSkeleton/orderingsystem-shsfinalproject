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
import bean.Banner;
import dao.BannerDAO;

@WebServlet("/admin/DeleteBanner.do")
public class BannerDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public BannerDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("BannersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		Banner banner = new Banner();
		banner.setId(Long.parseLong(request.getParameter("id")));
		
		BannerDAO bannerDao = new BannerDAO();
		int status = bannerDao.delete(banner);
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Banner was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete banner.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("BannersPanel.jsp");
	}

}