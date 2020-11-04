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
import bean.ModeOfPayment;
import dao.ModeOfPaymentDAO;

@WebServlet("/admin/EditModeOfPayment.do")
public class ModeOfPaymentEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public ModeOfPaymentEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("ModesOfPaymentPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		ModeOfPayment modeOfPayment = new ModeOfPayment();
		
		try {
			modeOfPayment.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidID = new AlertMessage();
			invalidID.setType("warning");
			invalidID.setMessage("Invalid ID.");
			alertMessages.add(invalidID);

			willProceedToUpdate = false;
		}
		
		modeOfPayment.setName(request.getParameter("name"));
		if(modeOfPayment.getName() == null || modeOfPayment.getName().equals("")) {
			AlertMessage invalidFirstName = new AlertMessage();
			invalidFirstName.setType("warning");
			invalidFirstName.setMessage("Invalid Name.");
			alertMessages.add(invalidFirstName);

			willProceedToUpdate = false;
		}
		
		modeOfPayment.setDescription(request.getParameter("description"));
		
		int status = 0;
		
		if(willProceedToUpdate) {
			ModeOfPaymentDAO modeOfPaymentDao = new ModeOfPaymentDAO();
			status = modeOfPaymentDao.update(modeOfPayment);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Mode of Payment was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Mode of Payment.");
			alertMessages.add(uploadError);
		}

		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("ModesOfPaymentPanel.jsp");
	}

}
