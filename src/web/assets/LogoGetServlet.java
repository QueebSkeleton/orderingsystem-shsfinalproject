package web.assets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ImageSetting;
import dao.ImageSettingDAO;

@WebServlet("/GetLogo.do")
public class LogoGetServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LogoGetServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		ImageSettingDAO settingDao = new ImageSettingDAO();
		ImageSetting logo = settingDao.getByKey("websiteImage");
		
		if(logo.getImageval() != null) {
			OutputStream out = response.getOutputStream();
			out.write(logo.getImageval());
			out.flush();
			out.close();
		}
	}

}
