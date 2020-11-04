<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Edit Supplier</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container">
			
			<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
			<c:set scope="request" var="id" value="<%= Long.parseLong(request.getParameter(\"id\")) %>"/>
			<c:set scope="request" var="supplier" value="${supplierDao.getByID(id)}"/>
			
			<div class="card mt-2 mb-2">
				<div class="card-header">
					<i class="fas fa-edit"></i>
					Edit Supplier
				</div>
				<div class="card-body">
					<a class="btn btn-sm btn-primary" href="SuppliersPanel.jsp">Back</a>
					
					<form class="form mt-2" method="POST" action="EditSupplier.do" autocomplete="off">
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
						<button type="submit" class="btn btn-primary">Edit</button>
					</form>
				</div>
			</div>
		
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
