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
import bean.ModeOfDelivery;
import dao.ModeOfDeliveryDAO;

@WebServlet("/admin/DeleteModeOfDelivery.do")
public class ModeOfDeliveryDeleteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ModeOfDeliveryDeleteServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ModesOfDeliveryPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToDelete = true;
		
		ModeOfDelivery modeOfDelivery = new ModeOfDelivery();
		
		try {
			modeOfDelivery.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToDelete = false;
		}
		
		int status = 0;
		
		if(willProceedToDelete) {
			ModeOfDeliveryDAO modeOfDeliveryDao = new ModeOfDeliveryDAO();
			status = modeOfDeliveryDao.delete(modeOfDelivery);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Mode of Delivery was successfully deleted.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to delete Mode of Delivery.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ModesOfDeliveryPanel.jsp");
	}

}
