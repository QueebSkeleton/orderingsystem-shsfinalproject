package web.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.ImageSetting;
import dao.ImageSettingDAO;

@WebServlet("/admin/UpdateLogo.do")
@MultipartConfig
public class LogoUpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public LogoUpdateServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ImageSettingDAO settingDao = new ImageSettingDAO();
		
		ImageSetting logo = settingDao.getByKey("websiteImage");
		
		Part imagePart = request.getPart("image");
		InputStream is = imagePart.getInputStream();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		
		byte[] data = new byte[1024];
		while((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		
		buffer.flush();
		logo.setImageval(buffer.toByteArray());
		
		settingDao.update(logo);
		
		response.sendRedirect("SettingsPanel.jsp");
	}

}
