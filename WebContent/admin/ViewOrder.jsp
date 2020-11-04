<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>View Order</title>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
					<c:set var="order" value="${orderDao.getByID(Long.parseLong(param.id))}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item">
							<a href="OrdersPanel.jsp">Orders Panel</a>
						</li>
						<li class="breadcrumb-item active">View Order</li>
					</ol>
					
					<c:if test="${not empty alertMessages}">
						<c:forEach items="${alertMessages}" var="alert">
							<jsp:include page="admin-assets/NotificationAlert.jsp">
								<jsp:param name="alertType" value="${alert.getType()}"/>
								<jsp:param name="alertMsg" value="${alert.getMessage()}"/>
							</jsp:include>
						</c:forEach>
						
						<c:remove scope="session" var="alertMessages"/>
					</c:if>
					
					<div class="card mt-2 mb-2">
						<div class="card-header">
							<i class="fas fa-shopping-cart"></i>
							Order: Items Ordered
						</div>
						<div class="card-body">
							<a class="btn btn-sm btn-primary" href="OrdersPanel.jsp">Back</a> <br/>
							
							<b>Client:</b> ${order.getClient().getId()} | ${order.getClient().getFirstName()} ${order.getClient().getLastName()} <br/>
							<b>Date Ordered:</b> <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.getDateOrdered()}"/> <br/>
							<b>Status:</b> ${order.getStatus()} <br/> <br/>
							
							<jsp:include page="ItemOrderAddModal.jsp">
								<jsp:param name="orderId" value="${order.getId()}"/>
							</jsp:include>
							
							<jsp:include page="OrderCouponAddModal.jsp">
								<jsp:param name="orderId" value="${order.getId()}"/>
							</jsp:include>
							
							<div class="modal fade" id="itemOrderEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Item Order</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="itemOrderEditForm" method="POST" action="EditItemOrder.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-lg-7">
									<div class="row">
										<div class="col-lg-6">
											<h3>Food Items:</h3>
										</div>
										<div class="col-lg-6 text-right">
											<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addItemOrderModal">Add Item</button>
										</div>
									</div>
									
									<table class="table table-striped table-bordered table-hover mt-2">
										<thead>
											<tr>
												<th style="width: 50%;">Food Item</th>
												<th style="width: 10%l">Price Each</th>
												<th style="width: 10%;">Quantity</th>
												<th style="width: 10%;">Total</th>
												<th colspan="2">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="total" value="${0}"/>
											<c:forEach items="${order.getItemOrderList()}" var="itemOrder">
												<tr>
													<td><c:out value="${itemOrder.getFood().getName()}"/></td>
													<td><c:out value="${itemOrder.getFood().getUnitPrice()}"/></td>
													<td><c:out value="${itemOrder.getQuantity()}"/></td>
													<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemOrder.getQuantity() * itemOrder.getFood().getUnitPrice()}"/></td>
													<td><button type="button" class="btn btn-primary" id="btnEdit${itemOrder.getId()}" data-toggle="modal" data-target="#itemOrderEditModal"><i class="fas fa-edit"></i></button></td>
													<td>
														<form method="POST" action="DeleteItemOrder.do">
															<input type="hidden" name="id" value="${itemOrder.getId()}"/>
															<input type="hidden" name="orderId" value="${itemOrder.getOrder().getId()}"/>
															<button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i></button>
														</form>
													</td>
													<c:set var="total" value="${total + itemOrder.getQuantity() * itemOrder.getFood().getUnitPrice()}"/>
												</tr>
													
												<script>
													$('#btnEdit${itemOrder.getId()}').click(function() {
														$.ajax({
															url: 'ItemOrderEditModal.jsp?id=${itemOrder.getId()}',
															type: 'GET',
															success: function(data) {
																$('#itemOrderEditForm').html(data);
															}
														});
													});
												</script>
											</c:forEach>
											
											<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>
											
											<c:set var="total" value="${total + Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}"/>
											
										</tbody>
									</table>
								</div>
								<div class="col-lg-5">
									<div class="row">
										<div class="col-lg-6">
											<h3>Coupons:</h3>
										</div>
										<div class="col-lg-6 text-right">
											<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addOrderCouponModal">Add a Coupon</button>
										</div>
									</div>
									
									<c:choose>
									
										<c:when test="${not empty order.getOrderCouponList()}">
											<table class="table table-striped table-bordered table-hover mt-2">
												<thead>
													<tr>
														<th>Coupon Code</th>
														<th>Discount Amount</th>
														<th>Delete</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${order.getOrderCouponList()}" var="orderCoupon">
														<tr>
															<td><c:out value="${orderCoupon.getCoupon().getCode()}"/></td>
															<td><c:out value="${orderCoupon.getCoupon().getDiscountAmount()}"/></td>
															<td>
																<form method="POST" action="DeleteOrderCoupon.do">
																	<input type="hidden" name="orderId" value="${orderCoupon.getOrder().getId()}"/>
																	<input type="hidden" name="couponId" value="${orderCoupon.getCoupon().getId()}"/>
																	<button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i></button>
																</form>
															</td>
														</tr>
														
														<c:set var="total" value="${total - orderCoupon.getCoupon().getDiscountAmount() * total}"/>
													</c:forEach>
												</tbody>
											</table>
										</c:when>
										
										<c:otherwise>
											<div class="text-center p-4">
												<i class="fas fa-exclamation-circle fa-3x"></i> <br/>
												<font style="font-size: 20px;">No coupons used for this order.</font>
											</div>
										</c:otherwise>
										
									</c:choose>
									
								</div>
							</div>
							
							
							<h5 class="text-right">Total: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${total}"/></h5>
						</div>
					</div>
				
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
