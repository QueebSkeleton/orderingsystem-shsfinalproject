<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	
	<c:if test="${empty client}">
		<c:set scope="session" var="redirectTo" value="<%= request.getRequestURI() %>"/>
		<c:set scope="session" var="message" value="You must login before accessing pending orders."/>
		<c:redirect url="LoginForm.jsp"/>
	</c:if>
	
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
    	<title>My Pending Orders</title>

		<script>
			function cancelOrderPrompt(id) {
				return confirm('Order No. ' + id + ' will be cancelled. Are you sure?');
			}
		</script>
  	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
			
			<div class="container">
				<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
				<c:set var="pendingOrderList" value="${orderDao.getPendingOrderListByClient(client)}"/>
				
				<c:choose>
					<c:when test="${not empty pendingOrderList}">
						<div class="card-columns mt-2 mb-2">
							<c:forEach items="${pendingOrderList}" var="order" varStatus="status">
								<div class="card" data-aos="flip-left">
									<div class="card-header">
										<i class="fas fa-shopping-cart"></i>
										Order # <c:out value="${status.index + 1}"/>
									</div>
									<div class="card-body">
										<b>Date Ordered:</b> <c:out value="${order.getDateOrdered()}"/> <br/>
										<b>Mode of Payment:</b> <c:out value="${order.getModeOfPayment().getName()}"/> <br/>
										<b>Mode of Delivery:</b> <c:out value="${order.getModeOfDelivery().getName()}"/> <br/>
										
										<div class="btn-group">
											<a class="btn btn-info" href="EditOrder.jsp?id=${order.getId()}">
												<i class="fas fa-eye"></i>
											</a>
											<a class="btn btn-danger" href="CancelOrder.do?id=${order.getId()}" onclick="return cancelOrderPrompt(${status.index + 1})">
												<i class="fas fa-arrow-down"></i>
											</a>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div class="text-center text-white mt-4">
							<i class="fas fa-exclamation-circle fa-8x"></i> <br/>
							<font size="10">You have no pending orders.</font>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		
		</div>
	</body>
</html>
