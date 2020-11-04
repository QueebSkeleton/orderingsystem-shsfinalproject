package web.assets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Banner;
import dao.BannerDAO;

@WebServlet("/GetBannerImage")
public class BannerGetImage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public BannerGetImage() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		BannerDAO bannerDao = new BannerDAO();
		Banner banner = bannerDao.getByID(Long.parseLong(request.getParameter("id")));
		
		if(banner.getImage() != null) {
			OutputStream out = response.getOutputStream();
			out.write(banner.getImage());
			out.flush();
			out.close();
		}
	}

}
