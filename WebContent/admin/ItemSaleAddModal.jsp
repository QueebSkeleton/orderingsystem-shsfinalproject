<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addItemSaleModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Item Sale</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form method="POST" action="AddItemSale.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
						<c:set var="clientList" value="${clientDao.getAllRecords()}"/>
						<label class="control-label">Client:</label>
						<select name="clientId" class="form-control">
							<c:forEach items="${clientList}" var="client">
								<option value="${client.getId()}"><c:out value="${client.getFirstName()} ${client.getLastName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<jsp:useBean id="employeeDao" class="dao.EmployeeDAO"/>
						<c:set var="employeeList" value="${employeeDao.getAllRecords()}"/>
						<label class="control-label">Employee:</label>
						<select name="employeeId" class="form-control">
							<c:forEach items="${employeeList}" var="employee">
								<option value="${employee.getId()}"><c:out value="${employee.getFirstName()} ${employee.getLastName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Date:</label>
						<input type="datetime-local" name="dateOrdered" class="form-control" required/>
					</div>
					<div class="form-group">
						<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
						<c:set var="foodList" value="${foodDao.getAllRecords()}"/>
						<label class="control-label">Food Item:</label>
						<select name="foodId" class="form-control">
							<c:forEach items="${foodList}" var="food">
								<option value="${food.getId()}"><c:out value="${food.getName()}"/></option>
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