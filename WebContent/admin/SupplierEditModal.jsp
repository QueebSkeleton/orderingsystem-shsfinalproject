<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					
<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
<c:set var="supplier" value="${supplierDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${supplier.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${supplier.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Address:</label>
		<input type="text" name="address" class="form-control" value="${supplier.getAddress()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">City:</label>
		<input type="text" name="city" class="form-control" value="${supplier.getCity()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Region:</label>
		<input type="text" name="region" class="form-control" value="${supplier.getRegion()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Postal Code:</label>
		<input type="number" name="postalCode" class="form-control" value="${supplier.getPostalCode()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Contact Number:</label>
		<input type="number" name="contactNumber" class="form-control" value="${supplier.getContactNumber()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Fax Number:</label>
		<input type="number" name="faxNumber" class="form-control" value="${supplier.getFaxNumber()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>