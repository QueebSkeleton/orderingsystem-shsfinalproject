<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Edit Item Order</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container">
			
			<jsp:useBean id="itemOrderDao" class="dao.ItemOrderDAO"/>
			<c:set scope="request" var="id" value="<%= Long.parseLong(request.getParameter(\"id\"))%>"/>
			<c:set scope="request" var="itemOrder" value="${itemOrderDao.getByID(id)}"/>
			
			<div class="card mt-2 mb-2">
				<div class="card-header">
					<i class="fas fa-shopping-cart"></i>
					Edit Item Order
				</div>
				<div class="card-body">
					<a class="btn btn-sm btn-primary mt-2" href="ViewOrder.jsp?id=${itemOrder.getOrder().getId()}">Back</a>
					
					<form class="form mt-2" method="POST" action="EditItemOrder.do" autocomplete="off">
						<div class="form-group">
							<label class="control-label">ID:</label>
							<input type="number" name="id" class="form-control" value="${itemOrder.getId()}" readonly required/>
						</div>
						<div class="form-group">
							<label class="control-label">Order ID:</label>
							<input type="number" name="orderId" class="form-control" value="${itemOrder.getOrder().getId()}" readonly required/>
						</div>
						<div class="form-group">
							<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
							<c:set scope="request" var="foodList" value="${foodDao.getAllRecords()}"/>
							<label class="control-label">Food Item:</label>
							<select name="foodId" class="form-control" required>
								<c:forEach items="${foodList}" var="food">
									<option value="${food.getId()}" <c:out value="${food.getId() eq itemOrder.getFood().getId() ? \"selected\" : \"\"}"/>><c:out value="${food.getName()}"/></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="control-label">Quantity:</label>
							<input type="number" name="quantity" class="form-control" value="${itemOrder.getQuantity()}" required/>
						</div>
						<button type="submit" class="btn btn-primary">Edit</button>
					</form>
				</div>
			</div>
		
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
