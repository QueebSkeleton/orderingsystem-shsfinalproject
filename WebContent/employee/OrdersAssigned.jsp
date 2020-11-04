<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="util.OrderStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
		<title>Orders Assigned</title>
		
		<script>
			function deliverOrderPrompt(id) {
				return confirm('Order No. ' + id + ' will be set to delivered. Are you sure?');
			}
			
			function denyOrderPrompt(id) {
				return confirm('Order no. ' + id + ' will be denied. Are you sure?');
			}
		</script>
	</head>
	<body>
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
		<c:set var="orderList" value="${orderDao.getOrderListByEmployeeAndStatus(employee, OrderStatus.PROCESSING)}"/>
		
		<div class="container-fluid p-4">
			<div class="row">
				<jsp:include page="employee-assets/ProfileMenu.jsp"></jsp:include>
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
					<c:if test="${not empty alertMsg}">
						<jsp:include page="employee-assets/NotificationAlert.jsp">
							<jsp:param name="alertType" value="${alertType}"/>
							<jsp:param name="alertMsg" value="${alertMsg}"/>
						</jsp:include>
						
						<c:remove scope="session" var="alertType"/>
						<c:remove scope="session" var="alertMsg"/>
					</c:if>
					
					<div class="card">
						<div class="card-header text-white" style="background-color: #36679E;">
							<i class="fas fa-list"></i>
							Orders Assigned
						</div>
						<div class="card-body">
							<c:choose>
								<c:when test="${empty orderList}">
									<div class="text-center">
										<i class="fas fa-exclamation-circle fa-5x"></i> <br/>
										No orders currently assigned to you.
									</div>
								</c:when>
								<c:otherwise>
									<c:forEach items="${orderList}" var="order">
										<div class="row border-bottom border-gray pb-4">
											<div class="col-1 pt-3">
												<i class="fas fa-shopping-cart" style="font-size: 60px;"></i>
											</div>
											<div class="col-11">
												<h4>Order No. ${order.getId()}</h4>
												<b>Client:</b> ${order.getClient().getFirstName()} ${order.getClient().getLastName()} <br/>
												<b>Date:</b> ${order.getDateOrdered()} <br/>
												<b>Payment Type:</b> ${order.getModeOfPayment().getName()} <br/>
												<b>Delivery Type:</b> ${order.getModeOfDelivery().getName()} <br/>
												<b>Status:</b> ${order.getStatus().toString()} <br/>
												
												<div class="btn-group">
													<a class="btn btn-info" href="ViewOrder.jsp?id=${order.getId()}">
														<i class="fas fa-eye"></i>
													</a>
													<a class="btn btn-warning" href="../GetOrderInvoice.do?id=${order.getId()}&redirect=employee" target="_blank">
														<i class="fas fa-file"></i>
													</a>
													<a class="btn btn-success" href="DeliverOrder.do?id=${order.getId()}" onclick="return deliverOrderPrompt(${order.getId()})">
														<i class="fas fa-truck"></i>
													</a>
													<a class="btn btn-danger" href="DenyOrder.do?id=${order.getId()}" onclick="return denyOrderPrompt(${order.getId()})">
														<i class="fas fa-arrow-down"></i>
													</a>
												</div>
											</div>
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>