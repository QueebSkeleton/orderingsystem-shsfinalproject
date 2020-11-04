package web.assets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import bean.AlertMessage;
import bean.ItemOrder;
import bean.Order;
import bean.OrderCoupon;
import dao.OrderDAO;
import dao.SettingsDAO;
import util.OrderStatus;

@WebServlet("/GetOrderInvoice.do")
public class OrderGetInvoice extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    public OrderGetInvoice() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");
		
		ServletOutputStream os = response.getOutputStream();
		
		SettingsDAO settingsDao = new SettingsDAO();
		OrderDAO orderDao = new OrderDAO();
		Order order = orderDao.getByID(Long.parseLong(request.getParameter("id")));
		
		if(order.getEmployee() == null || !(order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.PROCESSING)) {
			List<AlertMessage> alertMessages = new ArrayList<AlertMessage>();
			
			AlertMessage invalidInvoice = new AlertMessage();
			invalidInvoice.setType("danger");
			invalidInvoice.setMessage("Invalid invoice. Either no employee was set, or the order was not yet processed or delivered.");
			alertMessages.add(invalidInvoice);
			
			HttpSession session = request.getSession();
			session.setAttribute("alertMessages", alertMessages);
			
			String redirect = request.getParameter("redirect");
			
			if(redirect.contentEquals("admin"))
				response.sendRedirect(request.getContextPath() + "/admin/OrdersPanel.jsp");
			
			else if(redirect.contentEquals("employee"))
				response.sendRedirect(request.getContextPath() + "/employee/OrdersAssigned.jsp");
		}
		
		try {
			Document doc = new Document();
			
			PdfWriter.getInstance(doc, os);
			
			doc.open();
			
			PdfPTable headTable = new PdfPTable(2);
			headTable.setWidthPercentage(100);
			
			Paragraph company = new Paragraph(settingsDao.getByKey("companyName").getSetvalue(), new Font(FontFamily.COURIER, 18f, Font.BOLD));
			company.setAlignment(Paragraph.ALIGN_CENTER);
			doc.add(company);
			
			Paragraph address = new Paragraph(settingsDao.getByKey("companyAddress").getSetvalue(), new Font(FontFamily.COURIER, 13f, Font.BOLD));
			address.setAlignment(Paragraph.ALIGN_CENTER);
			doc.add(address);
			
			doc.add(new Paragraph("\n"));
			
			PdfPCell fromhead = new PdfPCell();
			fromhead.addElement(new Paragraph("Handled by:", new Font(FontFamily.UNDEFINED, 12f, Font.BOLD)));
			fromhead.addElement(new Paragraph(order.getEmployee().getLastName() + ", " + order.getEmployee().getFirstName()));
			fromhead.addElement(new Paragraph("Employee, " + settingsDao.getByKey("companyName").getSetvalue()));
			fromhead.addElement(new Paragraph(order.getEmployee().getContactNumber()));
			fromhead.setBorderColor(BaseColor.WHITE);
			headTable.addCell(fromhead);
			
			PdfPCell tohead = new PdfPCell();
			tohead.addElement(new Paragraph("To:", new Font(FontFamily.UNDEFINED, 12f, Font.BOLD)));
			tohead.addElement(new Paragraph(order.getClient().getLastName() + ", " + order.getClient().getFirstName() + " " + order.getClient().getMiddleName()));
			tohead.addElement(new Paragraph(order.getAddress()));
			tohead.addElement(new Paragraph(order.getCountry() != null ? order.getCountry().getName() : "United States of America"));
			tohead.addElement(new Paragraph(order.getClient().getContactNumber()));
			tohead.addElement(new Paragraph(order.getClient().getEmailAddress()));
			tohead.setBorderColor(BaseColor.WHITE);
			headTable.addCell(tohead);
			
			doc.add(headTable);
			
			doc.add(new Paragraph("\n"));
			
			PdfPTable orderTable = new PdfPTable(5);
			orderTable.setWidthPercentage(100);
			orderTable.setWidths(new float[] {1f, 3f, 2f, 1f, 2f});
			
			Font tableHeadFont = new Font(FontFamily.UNDEFINED, 12f, Font.NORMAL);
			
			PdfPCell orderNoHead = new PdfPCell(new Paragraph("#.", tableHeadFont));
			orderNoHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			orderNoHead.setPadding(5f);
			orderTable.addCell(orderNoHead);
			
			PdfPCell orderFoodHead = new PdfPCell(new Paragraph("Food", tableHeadFont));
			orderFoodHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			orderFoodHead.setPadding(5f);
			orderTable.addCell(orderFoodHead);
			
			PdfPCell priceEachHead = new PdfPCell(new Paragraph("Price Each"));
			priceEachHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			priceEachHead.setPadding(5f);
			orderTable.addCell(priceEachHead);
			
			PdfPCell orderQtyHead = new PdfPCell(new Paragraph("Qty.", tableHeadFont));
			orderQtyHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			orderQtyHead.setPadding(5f);
			orderTable.addCell(orderQtyHead);

			PdfPCell orderTotalHead = new PdfPCell(new Paragraph("Subtotal", tableHeadFont));
			orderTotalHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			orderTotalHead.setPadding(5f);
			orderTable.addCell(orderTotalHead);
			
			List<ItemOrder> cart = order.getItemOrderList();
			
			double total = 0.0;
			
			for(int i = 0; i < cart.size(); i++) {
				ItemOrder orderLine = cart.get(i);
				
				PdfPCell itemNoCell = new PdfPCell(new Paragraph(Integer.toString(i + 1)));
				itemNoCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				itemNoCell.setPadding(5f);
				
				PdfPCell foodCell = new PdfPCell(new Paragraph(orderLine.getFood().getName() + " | " + orderLine.getFood().getCategory().getName()));
				foodCell.setPadding(5f);
				
				PdfPCell priceEachCell = new PdfPCell(new Paragraph(String.format((order.getCountry() != null ? order.getCountry().getZone().getCurrency().getCode() : "USD") + "%.2f", (order.getCountry() != null ? orderLine.getFood().getUnitPrice() * order.getCountry().getZone().getCurrency().getExchangeRate() : orderLine.getFood().getUnitPrice()))));
				priceEachCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				priceEachCell.setPadding(5f);
				
				PdfPCell qtyCell = new PdfPCell(new Paragraph(Integer.toString(orderLine.getQuantity())));
				qtyCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				qtyCell.setPadding(5f);
				
				PdfPCell totalCell = new PdfPCell(new Paragraph(String.format((order.getCountry() != null ? order.getCountry().getZone().getCurrency().getCode() : "USD") + "%.2f", order.getCountry() != null ? orderLine.getFood().getUnitPrice() * orderLine.getQuantity() * order.getCountry().getZone().getCurrency().getExchangeRate() : orderLine.getFood().getUnitPrice() * orderLine.getQuantity())));
				totalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				totalCell.setPadding(5f);
				
				orderTable.addCell(itemNoCell);
				orderTable.addCell(foodCell);
				orderTable.addCell(priceEachCell);
				orderTable.addCell(qtyCell);
				orderTable.addCell(totalCell);
				
				total += (order.getCountry() != null ? orderLine.getFood().getUnitPrice() * order.getCountry().getZone().getCurrency().getExchangeRate() : orderLine.getFood().getUnitPrice()) * orderLine.getQuantity();
			}
			
			doc.add(orderTable);
			
			doc.add(new Paragraph("\n"));
			
			if(order.getOrderCouponList() != null && order.getOrderCouponList().size() != 0) {
				PdfPTable couponTable = new PdfPTable(2);
				couponTable.setWidths(new float[] {3f, 2f});
				couponTable.setWidthPercentage(100);
				
				PdfPCell couponCodeHead = new PdfPCell(new Paragraph("Code", tableHeadFont));
				couponCodeHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				couponCodeHead.setPadding(5f);
				couponTable.addCell(couponCodeHead);
				
				PdfPCell couponDiscountHead = new PdfPCell(new Paragraph("Discount Amount", tableHeadFont));
				couponDiscountHead.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				couponDiscountHead.setPadding(5f);
				couponTable.addCell(couponDiscountHead);
				
				List<OrderCoupon> orderCouponList = order.getOrderCouponList();
				
				for(int i = 0; i < orderCouponList.size(); i++) {
					OrderCoupon orderCoupon = orderCouponList.get(i);
					
					PdfPCell couponCodeCell = new PdfPCell(new Paragraph(orderCoupon.getCoupon().getCode()));
					couponCodeCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					couponCodeCell.setPadding(5f);
					
					PdfPCell discountCell = new PdfPCell(new Paragraph(String.format("%.2f", orderCoupon.getCoupon().getDiscountAmount() * total)));
					discountCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					discountCell.setPadding(5f);
					
					couponTable.addCell(couponCodeCell);
					couponTable.addCell(discountCell);
					
					total -= (orderCoupon.getCoupon().getDiscountAmount() * total);
				}
				
				doc.add(couponTable);
			}
			
			// Delivery Fee
			doc.add(new Paragraph("Delivery Fee: " + (order.getCountry() != null ? Double.parseDouble(settingsDao.getByKey("deliveryFee").getSetvalue()) * order.getCountry().getZone().getCurrency().getExchangeRate() : Double.parseDouble(settingsDao.getByKey("deliveryFee").getSetvalue()))));
			
			doc.add(new Paragraph("\n"));
			Paragraph grandTotal = new Paragraph("Grand Total: " + (order.getCountry() != null ? order.getCountry().getZone().getCurrency().getCode() : "USD") + String.format("%.2f", total + (order.getCountry() != null ? Double.parseDouble(settingsDao.getByKey("deliveryFee").getSetvalue()) * order.getCountry().getZone().getCurrency().getExchangeRate() : Double.parseDouble(settingsDao.getByKey("deliveryFee").getSetvalue()))), new Font(FontFamily.UNDEFINED, 15f, Font.BOLD));
			grandTotal.setAlignment(Paragraph.ALIGN_RIGHT);
			doc.add(grandTotal);
			// END OF CONTENT
			
			doc.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
