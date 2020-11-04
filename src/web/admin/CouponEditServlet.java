package web.admin;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AlertMessage;
import bean.Coupon;
import dao.CouponDAO;

@WebServlet("/admin/EditCoupon.do")
public class CouponEditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public CouponEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("CouponsPanel.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
		
		boolean willProceedToUpdate = true;
		
		Coupon coupon = new Coupon();
		
		try {
			coupon.setId(Long.parseLong(request.getParameter("id")));
		} catch(NumberFormatException e) {
			AlertMessage invalidId = new AlertMessage();
			invalidId.setType("warning");
			invalidId.setMessage("Invalid ID.");
			alertMessages.add(invalidId);
			
			willProceedToUpdate = false;
		}
		
		try {
			coupon.setDateCreated(Timestamp.from(Instant.now()));
		} catch(Exception e) {
			AlertMessage invalidDateNow = new AlertMessage();
			invalidDateNow.setType("warning");
			invalidDateNow.setMessage("Invalid Time current set.");
			alertMessages.add(invalidDateNow);
			
			willProceedToUpdate = false;
		}
		
		try {
			coupon.setValidUntil(new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").parse(request.getParameter("validUntil")).getTime()));
		} catch (ParseException e) {
			AlertMessage invalidDate = new AlertMessage();
			invalidDate.setType("warning");
			invalidDate.setMessage("Invalid Date set for 'Valid Until' Field.");
			alertMessages.add(invalidDate);
			
			willProceedToUpdate = false;
		}
		
		coupon.setCode(request.getParameter("code"));
		if(coupon.getCode() == null || coupon.getCode().equals("")) {
			AlertMessage invalidCode = new AlertMessage();
			invalidCode.setType("warning");
			invalidCode.setMessage("Invalid Code.");
			alertMessages.add(invalidCode);
			
			willProceedToUpdate = false;
		}
		
		try {
			coupon.setDiscountAmount(Double.parseDouble(request.getParameter("discountAmount")));
			
			if(coupon.getDiscountAmount() <= 0) {
				AlertMessage invalidDiscount = new AlertMessage();
				invalidDiscount.setType("warning");
				invalidDiscount.setMessage("Invalid Discount Amount.");
				alertMessages.add(invalidDiscount);
				
				willProceedToUpdate = false;
			}
		} catch(NumberFormatException e) {
			AlertMessage invalidDiscount = new AlertMessage();
			invalidDiscount.setType("warning");
			invalidDiscount.setMessage("Invalid Discount Amount.");
			alertMessages.add(invalidDiscount);
			
			willProceedToUpdate = false;
		}
		
		int status = 0;
		
		if(willProceedToUpdate) {
			CouponDAO couponDao = new CouponDAO();
			status = couponDao.update(coupon);
		}
		
		if(status > 0) {
			AlertMessage uploadSuccess = new AlertMessage();
			uploadSuccess.setType("success");
			uploadSuccess.setMessage("Coupon was successfully updated.");
			alertMessages.add(uploadSuccess);
		} else {
			AlertMessage uploadError = new AlertMessage();
			uploadError.setType("danger");
			uploadError.setMessage("An error has occured while trying to update Coupon.");
			alertMessages.add(uploadError);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("alertMessages", alertMessages);
		
		response.sendRedirect("CouponsPanel.jsp");
	}

}
