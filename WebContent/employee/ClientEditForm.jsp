<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Edit Client</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container">
			
			<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
			<c:set scope="request" var="id" value="<%= Long.parseLong(request.getParameter(\"id\"))%>"/>
			<c:set scope="request" var="client" value="${clientDao.getByID(id)}"/>
			
			<div class="card mt-2 mb-2">
				<div class="card-header">
					<i class="fas fa-edit"></i>
					Edit Client
				</div>
				<div class="card-body">
					<a class="btn btn-primary" href="ClientsPanel.jsp">Back</a>
					<form class="form mt-2" method="POST" action="EditClient.do" autocomplete="off">
						<input type="hidden" name="id" value="${client.getId()}"/>
						<div class="form-group">
							<label class="control-label">First Name:</label>
							<input type="text" name="firstName" class="form-control" value="${client.getFirstName()}"  required/>
						</div>
						<div class="form-group">
							<label class="control-label">Middle Name:</label>
							<input type="text" name="middleName" class="form-control" value="${client.getMiddleName()}" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Last Name:</label>
							<input type="text" name="lastName" class="form-control" value="${client.getLastName()}" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Address:</label>
							<input type="text" name="address" class="form-control" value="${client.getAddress()}" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Contact Number:</label>
							<input type="number" name="contactNumber" class="form-control" value="${client.getContactNumber()}" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Email Address:</label>
							<input type="email" name="emailAddress" class="form-control" value="${client.getEmailAddress()}" required/>
						</div>
						<button type="submit" class="btn btn-primary">Edit</button>
					</form>
				</div>
			</div>
		
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
