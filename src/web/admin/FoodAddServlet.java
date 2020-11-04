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
import bean.Food;
import dao.CategoryDAO;
import dao.FoodDAO;
import dao.SupplierDAO;
import model.StocksHandler;

@WebServlet("/admin/AddFood.do")
@MultipartConfig
public class FoodAddServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    public FoodAddServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("FoodsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToInsert = true;
		
		Food food = new Food();
		
		food.setName(request.getParameter("name"));
		if(food.getName() == null || food.getName().equals("")) {
			AlertMessage invalidName = new AlertMessage();
			invalidName.setType("warning");
			invalidName.setMessage("Invalid name.");
			alertMessages.add(invalidName);
			
			willProceedToInsert = false;
		}
		
		try {
			SupplierDAO supplierDao = new SupplierDAO();
			
			long supplierId = Long.parseLong(request.getParameter("supplierId"));
			
			if(supplierId != 0)
				food.setSupplier(supplierDao.getByID(supplierId));
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Supplier.");
			alertMessages.add(invalidId);
			
			willProceedToInsert = false;
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
			food.setImage(buffer.toByteArray());
		} else {
			AlertMessage invalidImageFormat = new AlertMessage();
			invalidImageFormat.setType("warning");
			invalidImageFormat.setMessage("Invalid Image Type.");
			alertMessages.add(invalidImageFormat);
			
			willProceedToInsert = false;
		}
		
		try {
			CategoryDAO categoryDao = new CategoryDAO();
			
			long categoryId = Long.parseLong(request.getParameter("categoryId"));
			food.setCategory(categoryDao.getByID(categoryId));
			
			if(categoryId == 0 && food.getCategory() == null) {
				AlertMessage invalidId = new AlertMessage();
				invalidId.setType("warning");
				invalidId.setMessage("Invalid ID set.");
				alertMessages.add(invalidId);
				
				willProceedToInsert = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid Category.");
			alertMessages.add(invalidId);
			
			willProceedToInsert = false;
		}
		
		food.setDescription(request.getParameter("description"));
		
		try {
			food.setUnitsInStock(Integer.parseInt(request.getParameter("unitsInStock")));
		} catch(NumberFormatException e) {
			AlertMessage invalidStocks = new AlertMessage();
			invalidStocks.setType("warning");
			invalidStocks.setMessage("Invalid stocks set.");
			alertMessages.add(invalidStocks);
			
			willProceedToInsert = false;
		}
		
		try {
			food.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));
		} catch(NumberFormatException e) {
			AlertMessage invalidStocks = new AlertMessage();
			invalidStocks.setType("warning");
			invalidStocks.setMessage("Invalid unit price set.");
			alertMessages.add(invalidStocks);
			
			willProceedToInsert = false;
		}
		
		int status = 0;
		
		if(willProceedToInsert) {
			FoodDAO foodDao = new FoodDAO();
			status = foodDao.insert(food);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Food was successfully added.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to add Food.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		StocksHandler.loadStocksList();
		
		String categoryRedirect = request.getParameter("categoryRedirect");
		
		response.sendRedirect("FoodsPanel.jsp" + (categoryRedirect != null ? "?categoryId=" + categoryRedirect : ""));
	}

}
