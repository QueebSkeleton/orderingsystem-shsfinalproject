package web.assets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Employee;
import dao.EmployeeDAO;

@WebServlet("/GetEmployeeImage")
public class EmployeeGetImage extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public EmployeeGetImage() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		EmployeeDAO employeeDao = new EmployeeDAO();
		Employee employee = employeeDao.getByID(Long.parseLong(request.getParameter("id")));
		
		if(employee.getImage() != null) {
			OutputStream out = response.getOutputStream();
			out.write(employee.getImage());
			out.flush();
			out.close();
		}
	}

}
