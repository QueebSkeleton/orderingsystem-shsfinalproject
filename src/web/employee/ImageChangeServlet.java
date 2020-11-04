package web.employee;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.Employee;
import dao.EmployeeDAO;

@WebServlet("/employee/ChangeImage.do")
@MultipartConfig
public class ImageChangeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ImageChangeServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		EmployeeDAO employeeDao = new EmployeeDAO();
		Employee employee = employeeDao.getByID(((Employee) session.getAttribute("employee")).getId());
		
		Part imagePart = request.getPart("image");
		InputStream is = imagePart.getInputStream();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		
		byte[] data = new byte[1024];
		while((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		
		buffer.flush();
		employee.setImage(buffer.toByteArray());
		
		employeeDao.updateImage(employee);
		session.setAttribute("employee", employee);
		
		response.sendRedirect("EditProfile.jsp");
	}

}
