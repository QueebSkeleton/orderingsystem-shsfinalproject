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
import bean.Supplier;
import dao.SupplierDAO;

@WebServlet("/admin/DeleteSupplier.do")
public class SupplierDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SupplierDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("SuppliersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		Supplier supplier = new Supplier();
		
		try {
			supplier.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			SupplierDAO supplierDao = new SupplierDAO();
			status = supplierDao.delete(supplier);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Supplier was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete Supplier.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("SuppliersPanel.jsp");
	}

}
