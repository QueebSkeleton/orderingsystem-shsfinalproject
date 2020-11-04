<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="modal fade" id="addItemOrderModal">
	<div class="modal-dialog">
		<div class="modal-content">
			
			<div class="modal-header">
				<h1 class="modal-title">Add Item Order</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddItemOrder.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Order ID:</label>
						<input type="number" name="orderId" class="form-control" value="${param.orderId}" readonly required/>
					</div>
					<div class="form-group">
						<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
						<c:set var="foodList" value="${foodDao.getAllRecords()}"/>
						<label class="control-label">Food Item:</label>
						<select name="foodId" class="form-control" required>
							<c:forEach items="${foodList}" var="food">
								<option value="${food.getId()}"><c:out value="${food.getName()} | ${food.getCategory().getName()} | ${food.getUnitPrice()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Quantity:</label>
						<input type="number" name="quantity" class="form-control" required/>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Add</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
		</div>
	</div>
</div>