<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="employeeDao" class="dao.EmployeeDAO"/>
<c:set var="employee" value="${employeeDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${employee.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">First Name:</label>
		<input type="text" name="firstName" class="form-control" value="${employee.getFirstName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Middle Name:</label>
		<input type="text" name="middleName" class="form-control" value="${employee.getMiddleName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Last Name:</label>
		<input type="text" name="lastName" class="form-control" value="${employee.getLastName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Birthdate:</label>
		<input type="date" name="birthdate" class="form-control" value="${employee.getBirthdate()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Address:</label>
		<input type="text" name="address" class="form-control" value="${employee.getAddress()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Contact Number:</label>
		<input type="number" name="contactNumber" class="form-control" value="${employee.getContactNumber()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Email Address:</label>
		<input type="email" name="emailAddress" class="form-control" value="${employee.getEmailAddress()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Username:</label>
		<input type="text" name="username" class="form-control" value="${employee.getUsername()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Password:</label>
		<input type="text" name="password" class="form-control" value="${employee.getPassword()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>