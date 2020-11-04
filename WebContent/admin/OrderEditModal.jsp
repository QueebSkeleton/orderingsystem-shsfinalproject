<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="util.OrderStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
<c:set var="order" value="${orderDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<input type="hidden" name="statusRedirect" value="${param.statusRedirect}"/>
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${order.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
		<c:set scope="request" var="clientList" value="${clientDao.getAllRecords()}"/>
		<label class="control-label">Client:</label>
		<select name="clientId" class="form-control" required>
			<c:forEach items="${clientList}" var="client">
				<option value="${client.getId()}" <c:out value="${client.getId() eq order.getClient().getId() ? \"selected\" : \"\"}"/>><c:out value="${client.getFirstName()} ${client.getLastName()}"/></option>
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
				<option value="${employee.getId()}" <c:out value="${employee.getId() eq order.getEmployee().getId() ? \"selected\" : \"\"}"/>><c:out value="${employee.getFirstName()} ${employee.getLastName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Date Ordered:</label>
		<input type="datetime-local" name="dateOrdered" class="form-control" value="<fmt:formatDate pattern="yyyy-MM-dd'T'HH:mm:ss" value="${order.getDateOrdered()}"/>" required/>
	</div>
	<div class="form-group">
		<jsp:useBean id="modeOfPaymentDao" class="dao.ModeOfPaymentDAO"/>
		<c:set scope="request" var="modeOfPaymentList" value="${modeOfPaymentDao.getAllRecords()}"/>
		<label class="control-label">Mode of Payment:</label>
		<select name="modeOfPaymentId" class="form-control" required>
			<c:forEach items="${modeOfPaymentList}" var="modeOfPayment">
				<option value="${modeOfPayment.getId()}" <c:out value="${modeOfPayment.getId() eq order.getModeOfPayment().getId() ? \"selected\" : \"\"}"/>><c:out value="${modeOfPayment.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<jsp:useBean id="modeOfDeliveryDao" class="dao.ModeOfDeliveryDAO"/>
		<c:set scope="request" var="modeOfDeliveryList" value="${modeOfDeliveryDao.getAllRecords()}"/>
		<label class="control-label">Mode of Delivery:</label>
		<select name="modeOfDeliveryId" class="form-control" required>
			<c:forEach items="${modeOfDeliveryList}" var="modeOfDelivery">
				<option value="${modeOfDelivery.getId()}" <c:out value="${modeOfDelivery.getId() eq order.getModeOfDelivery().getId() ? \"selected\" : \"\"}"/>><c:out value="${modeOfDelivery.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Address:</label>
		<input type="text" name="address" class="form-control" value="${order.getAddress()}" required/>
	</div>
	<div class="form-group">
		<jsp:useBean id="countryDao" class="dao.CountryDAO"/>
		<c:set var="countryList" value="${countryDao.getAllRecords()}"/>
		<label class="control-label">Country:</label>
		<select name="countryId" class="form-control">
			<option value="0">USA / Default</option>
			<c:forEach items="${countryList}" var="country">
				<option value="${country.getId()}" ${order.getCountry().getId() eq country.getId() ? 'selected' : ''}><c:out value="${country.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Notes:</label>
		<textarea name="notes" class="form-control"><c:out value="${order.getNotes()}"/></textarea>
	</div>
	<div class="form-group">
		<label class="control-label">Status:</label>
		<select name="status" class="form-control" required>
			<c:set var="newStatus"><%= OrderStatus.NEW %></c:set>
			<option value="NEW" <c:out value="${order.getStatus() eq newStatus ? \"selected\" : \"\"}"/>>New</option>
			<c:set var="processingStatus" ><%= OrderStatus.PROCESSING %></c:set>
			<option value="PROCESSING" <c:out value="${order.getStatus() eq processingStatus ? \"selected\" : \"\"}"/>>Processing</option>
			<c:set var="deliveredStatus"><%= OrderStatus.DELIVERED %></c:set>
			<option value="DELIVERED" <c:out value="${order.getStatus() eq deliveredStatus ? \"selected\" : \"\"}"/>>Delivered</option>
			<c:set var="deniedStatus"><%= OrderStatus.DENIED %></c:set>
			<option value="DENIED" <c:out value="${order.getStatus() eq deniedStatus ? \"selected\" : \"\"}"/>>Denied</option>
			<c:set var="cancelledStatus"><%= OrderStatus.CANCELLED %></c:set>
			<option value="CANCELLED" <c:out value="${order.getStatus() eq cancelledStatus ? \"selected\" : \"\"}"/>>Cancelled</option>
		</select>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>


<script>
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