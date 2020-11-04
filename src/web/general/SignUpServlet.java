package web.general;

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

import bean.Client;
import dao.ClientDAO;

@WebServlet("/SignUp.do")
@MultipartConfig
public class SignUpServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SignUpServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Client client = new Client();
		
		Part imagePart = request.getPart("image");
		InputStream is = imagePart.getInputStream();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		
		byte[] data = new byte[1024];
		while((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		
		buffer.flush();
		client.setImage(buffer.toByteArray());
		
		client.setFirstName(request.getParameter("firstName"));
		client.setMiddleName(request.getParameter("middleName"));
		client.setLastName(request.getParameter("lastName"));
		client.setAddress(request.getParameter("address"));
		client.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		client.setEmailAddress(request.getParameter("emailAddress"));
		client.setUsername(request.getParameter("username"));
		client.setPassword(request.getParameter("password"));
		
		ClientDAO clientDao = new ClientDAO();
		int status = clientDao.insert(client);
		
		if(status > 0) {
			response.sendRedirect("index.jsp");	
		}
		
	}

}
