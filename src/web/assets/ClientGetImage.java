package web.assets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Client;
import dao.ClientDAO;

@WebServlet("/GetClientImage")
public class ClientGetImage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public ClientGetImage() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		ClientDAO clientDao = new ClientDAO();
		Client client = clientDao.getByID(Long.parseLong(request.getParameter("id")));
		
		if(client.getImage() != null) {
			OutputStream out = response.getOutputStream();
			out.write(client.getImage());
			out.flush();
			out.close();
		}
	}

}
