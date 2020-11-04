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

@WebServlet("/admin/AddSupplier.do")
public class SupplierAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public SupplierAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("SuppliersPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Supplier supplier = new Supplier();
		
		supplier.setName(request.getParameter("name"));
		if(supplier.getName() == null || supplier.getName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid Name.");
			alertMessages.add(invalidFirstName);

			willProceedToInsert = false;
		}
		
		supplier.setAddress(request.getParameter("address"));
		if(supplier.getAddress() == null || supplier.getAddress().equals("")) {
			AlertMessage invalidAddress = new AlertMessage();
			invalidAddress.setType("warning");
			invalidAddress.setMessage("Invalid Address.");
			alertMessages.add(invalidAddress);

			willProceedToInsert = false;
		}
		
		supplier.setCity(request.getParameter("city"));
		if(supplier.getCity() == null || supplier.getCity().equals("")) {
			AlertMessage invalidCity = new AlertMessage();
			invalidCity.setType("warning");
			invalidCity.setMessage("Invalid City.");
			alertMessages.add(invalidCity);

			willProceedToInsert = false;
		}
		
		supplier.setRegion(request.getParameter("region"));
		if(supplier.getRegion() == null || supplier.getRegion().equals("")) {
			AlertMessage invalidRegion = new AlertMessage();
			invalidRegion.setType("warning");
			invalidRegion.setMessage("Invalid Region.");
			alertMessages.add(invalidRegion);

			willProceedToInsert = false;
		}
		
		try {
			supplier.setPostalCode(Integer.parseInt(request.getParameter("postalCode")));
		} catch(NumberFormatException e) {
			AlertMessage invalidPostalCode = new AlertMessage();
			invalidPostalCode.setType("warning");
			invalidPostalCode.setMessage("Invalid Postal Code.");
			alertMessages.add(invalidPostalCode);

			willProceedToInsert = false;
		}
		
		try {
			supplier.setContactNumber(Long.parseLong(request.getParameter("contactNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidContactNumber = new AlertMessage();
			invalidContactNumber.setType("warning");
			invalidContactNumber.setMessage("Invalid Contact Number.");
			alertMessages.add(invalidContactNumber);

			willProceedToInsert = false;
		}
		
		try {
			supplier.setFaxNumber(Long.parseLong(request.getParameter("faxNumber")));
		} catch(NumberFormatException e) {
			AlertMessage invalidFaxNumber = new AlertMessage();
			invalidFaxNumber.setType("warning");
			invalidFaxNumber.setMessage("Invalid Fax Number.");
			alertMessages.add(invalidFaxNumber);

			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			SupplierDAO supplierDao = new SupplierDAO();
			status = supplierDao.insert(supplier);
		}

		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Supplier was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Supplier.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("SuppliersPanel.jsp");
	}

}
