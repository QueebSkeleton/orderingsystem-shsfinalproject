<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
		<title>Edit my Order</title>
	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
			
			<div class="container mt-2 mb-2">
				<div class="card">
					<div class="card-header">
						<i class="fas fa-shopping-cart"></i>
						Checkout: My Cart
					</div>
					<div class="card-body">
						<c:set var="grandTotal" value="${0}"/>
						
						<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
						<c:set var="order" value="${orderDao.getByID(Long.parseLong(param.id))}"/>
						
						<div class="row">
							<div class="col-lg-7">
								<table class="table table-responsive table-striped table-bordered table-hover mt-2">
									<thead>
										<tr>
											<th style="width: 55%;">Food Item</th>
											<th style="width: 15%;">Category</th>
											<th style="width: 10%;">Quantity</th>
											<th style="width: 10%;">Total</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="total" value="${0}"/>
										<c:forEach items="${order.getItemOrderList()}" var="itemOrder">
											<tr>
												<td><c:out value="${itemOrder.getFood().getName()}"/></td>
												<td><c:out value="${itemOrder.getFood().getCategory().getName()}"/></td>
												<td><c:out value="${itemOrder.getQuantity()}"/></td>
												<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${empty countrySet ? itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() : itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() * countrySet.getZone().getCurrency().getExchangeRate()}"/></td>
												<c:set var="grandTotal" value="${empty countrySet ? grandTotal + itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() : grandTotal + itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() * countrySet.getZone().getCurrency().getExchangeRate()}"/>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						
							<div class="col-sm-5">
								<table class="table table-striped table-bordered table-hover mt-2">
									<thead>
										<tr>
											<th style="width: 60%;">Coupon</th>
											<th style="width: 40%;">Discount</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${couponList}" var="orderCoupon">
											<tr>
												<td><c:out value="${orderCoupon.getCoupon().getCode()}"/></td>
												<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${orderCoupon.getCoupon().getDiscountAmount() * grandTotal}"/>
												</tr>
											<c:set var="grandTotal" value="${grandTotal - orderCoupon.getCoupon().getDiscountAmount() * grandTotal}"/>
										</c:forEach>
										
										<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>
										
										<c:choose>
											<c:when test="${not empty countrySet}">
												<c:set var="grandTotal" value="${grandTotal + countrySet.getZone().getCurrency().getExchangeRate() * Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}"/>
											</c:when>
											<c:otherwise>
												<c:set var="grandTotal" value="${grandTotal + Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}"/>
											</c:otherwise>
										</c:choose>
										
									</tbody>
								</table>
							</div>
						</div>
						
						<h6 class="text-right">Delivery Fee: ${empty countrySet ? Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue()) : countrySet.getZone().getCurrency().getExchangeRate() * Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}</h6>
						<h5 class="text-right">Grand Total: <c:out value="${empty countrySet ? '$' : countrySet.getZone().getCurrency().getCode()}"/><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${grandTotal}"/></h5>
						
						<hr/>
						
						<h2 class="text-center">Payment and Delivery Details</h2>
						<form method="POST" action="UpdateOrder.do">
							<div class="row">
								<div class="col-sm-6">
									<input type="hidden" name="id" value="${order.getId()}"/>
									<div class="form-group">
										<label class="control-label">Address:</label>
										<input type="text" name="address" class="form-control" value="${order.getAddress()}" readonly/>
									</div>
									<div class="form-group">
										<label class="control-label">Mode of Payment:</label>
										<jsp:useBean id="modeOfPaymentDao" class="dao.ModeOfPaymentDAO"/>
										<c:set scope="request" var="modeOfPaymentList" value="${modeOfPaymentDao.getAllRecords()}"/>
										<select name="modeOfPaymentId" class="form-control" required>
											<c:forEach items="${modeOfPaymentList}" var="modeOfPayment">
												<option value="${modeOfPayment.getId()}"><c:out value="${modeOfPayment.getName()}"/></option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label class="control-label">Mode of Delivery:</label>
										<jsp:useBean id="modeOfDeliveryDao" class="dao.ModeOfDeliveryDAO"/>
										<c:set scope="request" var="modeOfDeliveryList" value="${modeOfDeliveryDao.getAllRecords()}"/>
										<select name="modeOfDeliveryId" class="form-control" required>
											<c:forEach items="${modeOfDeliveryList}" var="modeOfDelivery">
												<option value="${modeOfDelivery.getId()}"><c:out value="${modeOfDelivery.getName()}"/></option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-6">
									<h2 class="text-center">Notes:</h2>
									<textarea name="notes" rows="6" class="form-control" readonly><c:out value="${order.getNotes()}"/></textarea>
								</div>
							</div>
							<button type="submit" class="btn btn-primary">Update Order</button>
						</form>
					</div>
				</div>
			</div>
			
			<jsp:include page="assets/include/Footer.jsp"></jsp:include>
		</div>
	</body>	
</html>