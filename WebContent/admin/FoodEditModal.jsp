<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
<c:set var="food" value="${foodDao.getByID(Long.parseLong(param.id))}"/>
					
<div class="modal-body">	
	<input type="hidden" name="categoryRedirect" value="${param.categoryId}"/>
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${food.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${food.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Supplier:</label>
		<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
		<c:set scope="request" var="supplierList" value="${supplierDao.getAllRecords()}"/>
		<select name="supplierId" class="form-control">
			<option value="0">None</option>
			<c:forEach items="${supplierList}" var="supplier">
				<option value="${supplier.getId()}" ${food.getSupplier().getId() eq supplier.getId() ? 'selected' : ''}><c:out value="${supplier.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Category:</label>
		<select name="categoryId" class="form-control" required>
			<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
			<c:set var="categoryList" value="${categoryDao.getAllRecords()}"/>
			<c:forEach items="${categoryList}" var="category">
				<option value="${category.getId()}" ${food.getCategory().getId() eq category.getId() ? 'selected' : ''}><c:out value="${category.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control"><c:out value="${food.getDescription()}"/></textarea>
	</div>
	<div class="form-group">
		<label class="control-label">Initial Units in Stock:</label>
		<input type="number" name="unitsInStock" class="form-control" value="${food.getUnitsInStock()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Unit Price:</label>
		<input type="number" step=".01" name="unitPrice" class="form-control" value="${food.getUnitPrice()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>
