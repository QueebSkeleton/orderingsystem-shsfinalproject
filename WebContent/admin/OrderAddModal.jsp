<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>				
<div class="modal fade" id="addOrderModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Order</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddOrder.do" autocomplete="off">
				<input type="hidden" name="statusRedirect" value="${param.status}"/>
				<div class="modal-body">
					<div class="form-group">
						<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
						<c:set scope="request" var="clientList" value="${clientDao.getAllRecords()}"/>
						<label class="control-label">Client:</label>
						<select name="clientId" class="form-control" required>
							<c:forEach items="${clientList}" var="client">
								<option value="${client.getId()}">
									<c:out value="${String.format(\"%25s | %4s\", client.getFirstName() += \" \" += client.getLastName(), client.getId())}"/>
								</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<jsp:useBean id="employeeDao" class="dao.EmployeeDAO"/>
						<c:set scope="request" var="employeeList" value="${employeeDao.getAllRecords()}"/>
						<label class="control-label">Assigned Employee:</label>
						<select name="employeeId" class="form-control" required>
							<option value="0">None</option>
							<c:forEach items="${employeeList}" var="employee">
								<option value="${employee.getId()}"><c:out value="${employee.getFirstName()} ${employee.getLastName()} | ${employee.getId()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<jsp:useBean id="currentDate" class="java.util.Date"/>
						<label class="control-label">Date Ordered:</label>
						<input type="datetime-local" name="dateOrdered" class="form-control" max="<fmt:formatDate pattern="yyyy-MM-dd'T'hh:mm" value="${currentDate}"/>" required/>
					</div>
					<div class="form-group">
						<jsp:useBean id="modeOfPaymentDao" class="dao.ModeOfPaymentDAO"/>
						<c:set scope="request" var="modeOfPaymentList" value="${modeOfPaymentDao.getAllRecords()}"/>
						<label class="control-label">Mode of Payment:</label>
						<select name="modeOfPaymentId" class="form-control" required>
							<c:forEach items="${modeOfPaymentList}" var="modeOfPayment">
								<option value="${modeOfPayment.getId()}"><c:out value="${modeOfPayment.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<jsp:useBean id="modeOfDeliveryDao" class="dao.ModeOfDeliveryDAO"/>
						<c:set scope="request" var="modeOfDeliveryList" value="${modeOfDeliveryDao.getAllRecords()}"/>
						<label class="control-label">Mode of Delivery:</label>
						<select name="modeOfDeliveryId" class="form-control" required>
							<c:forEach items="${modeOfDeliveryList}" var="modeOfDelivery">
								<option value="${modeOfDelivery.getId()}"><c:out value="${modeOfDelivery.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Address:</label>
						<input type="text" name="address" class="form-control" required/>
					</div>
					<div class="form-group">
						<jsp:useBean id="countryDao" class="dao.CountryDAO"/>
						<c:set var="countryList" value="${countryDao.getAllRecords()}"/>
						<label class="control-label">Country:</label>
						<select name="countryId" class="form-control">
							<option value="0">USA / Default</option>
							<c:forEach items="${countryList}" var="country">
								<option value="${country.getId()}"><c:out value="${country.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Notes:</label>
						<textarea name="notes" class="form-control"></textarea>
					</div>
					<div class="form-group">
						<label class="control-label">Status:</label>
						<select name="status" class="form-control" required>
							<option value="NEW">New</option>
							<option value="PROCESSING">Processing</option>
							<option value="DELIVERED">Delivered</option>
							<option value="DENIED">Denied</option>
							<option value="CANCELLED">Cancelled</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Add</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
			<script>
				// Initial Value
				$.ajax({
					url: 'GetClientAddress.jsp?id=' + $("select[name='clientId']").val(),
					type: 'GET',
					success: function(data) {
						$("input[name='address']").val(data);
					}
				});
				
				// On Change
				$("select[name='clientId']").change(function() {
					$.ajax({
						url: 'GetClientAddress.jsp?id=' + $("select[name='clientId']").val(),
						type: 'GET',
						success: function(data) {
							$("input[name='address']").val(data);
						}
					});
				});
			</script>
		
		</div>
	</div>
</div>