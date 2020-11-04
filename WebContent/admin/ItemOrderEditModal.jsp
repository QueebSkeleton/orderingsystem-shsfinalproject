<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="itemOrderDao" class="dao.ItemOrderDAO"/>
<c:set var="itemOrder" value="${itemOrderDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
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
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>