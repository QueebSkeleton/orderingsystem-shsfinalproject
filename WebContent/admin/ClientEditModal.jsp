<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					
<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
<c:set var="client" value="${clientDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${client.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">First Name:</label>
		<input type="text" name="firstName" class="form-control" value="${client.getFirstName()}" required/>
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
	<div class="form-group">
		<label class="control-label">Username:</label>
		<input type="text" name="username" class="form-control" value="${client.getUsername()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Password:</label>
		<input type="text" name="password" class="form-control" value="${client.getPassword()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
</div>