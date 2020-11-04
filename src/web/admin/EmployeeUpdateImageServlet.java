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
import bean.Employee;
import dao.EmployeeDAO;

@WebServlet("/admin/UpdateEmployeeImage.do")
@MultipartConfig
public class EmployeeUpdateImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public EmployeeUpdateImageServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ClientsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Employee employee = new Employee();
		
		try {
			employee.setId(Long.parseLong(request.getParameter("id")));
		
			if(employee.getId() == 0) {
				AlertMessage invalidId = new AlertMessage();
				invalidId.setType("warning");
				invalidId.setMessage("Invalid ID set.");
				alertMessages.add(invalidId);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID set.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
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
			employee.setImage(buffer.toByteArray());
		} else {
			AlertMessage invalidImageFormat = new AlertMessage();
			invalidImageFormat.setType("warning");
			invalidImageFormat.setMessage("Invalid Image Type.");
			alertMessages.add(invalidImageFormat);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			EmployeeDAO employeeDao = new EmployeeDAO();
			status = employeeDao.updateImage(employee);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Employee image was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Employee image.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("EmployeesPanel.jsp");
	}

}
