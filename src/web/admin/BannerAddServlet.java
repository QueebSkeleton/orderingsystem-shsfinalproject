package web.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.AlertMessage;
import bean.Banner;
import dao.BannerDAO;

@WebServlet("/admin/AddBanner.do")
@MultipartConfig
public class BannerAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public BannerAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("BannersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		Banner banner = new Banner();
		
		Part imagePart = request.getPart("image");
		String fileType = this.getServletContext().getMimeType(imagePart.getSubmittedFileName());
		if(fileType.startsWith("image/")) {
			InputStream is = imagePart.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			int nRead; 
			byte[] data = new byte[1024];
			
			while((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			
			buffer.flush();
			banner.setImage(buffer.toByteArray());
			
			banner.setDescription(request.getParameter("description"));
			
			BannerDAO bannerDao = new BannerDAO();
			int status = bannerDao.insert(banner);
			
			if(status > 0) {
				AlertMessage uploadSuccess = new AlertMessage();
				uploadSuccess.setType("success");
				uploadSuccess.setMessage("Banner was successfully added.");
				alertMessages.add(uploadSuccess);
			} else {
				AlertMessage uploadError = new AlertMessage();
				uploadError.setType("danger");
				uploadError.setMessage("An error has occured while trying to add Banner.");
				alertMessages.add(uploadError);
			}
		} else {
			AlertMessage invalidImage = new AlertMessage();
			invalidImage.setType("warning");
			invalidImage.setMessage("Invalid image type set for upload. System did not proceed with upload.");
			alertMessages.add(invalidImage);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("BannersPanel.jsp");
	}

}
