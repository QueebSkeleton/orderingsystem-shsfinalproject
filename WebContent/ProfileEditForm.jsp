<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
    	<title>Edit your Profile</title>
  	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
			
			<div class="container pt-4 pb-4">
				<div class="card">
					<div class="card-header">
						<i class="fas fa-user-tie"></i>
						My Profile
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-lg-6">
								<form class="form" method="POST" action="EditProfile.do" autocomplete="off">
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
										<label class="control-label">Email Address:</label>
										<input type="text" name="emailAddress" class="form-control" value="${client.getEmailAddress()}" required/>
									</div>
									<div class="form-group">
										<label class="control-label">Contact Number:</label>
										<input type="number" name="contactNumber" class="form-control" value="${client.getContactNumber()}" required/>
									</div>
									<span id="validateUsername"></span>
									<div class="form-group">
										<label class="control-label">Username:</label>
										<input type="text" name="username" id="username" class="form-control" value="${client.getUsername()}" required/>
									</div>
									<button type="submit" id="btnSubmit" class="btn btn-primary">Edit</button>
								</form>
							</div>
							<div class="col-lg-6">
								<form class="form" id="changePassForm" method="POST" action="ChangePassword.do" autocomplete="off">
									<div class="form-group">
										<span id="validateOldPassword"></span>
										<label class="control-label">Old Password:</label>
										<input type="password" name="password" id="password" class="form-control" required/>
									</div>
									<div class="form-group">
										<label class="control-label">New Password:</label>
										<input type="password" name="newPassword" id="newPassword" class="form-control" required/>
									</div>
									<div class="form-group">
										<span id="validateMessage"></span>
										<label class="control-label">Retype Password:</label>
										<input type="password" name="retypedPassword" id="retypedPassword" class="form-control" required/>
									</div>
									<button type="submit" id="btnSubmitPass" class="btn btn-primary" style="display: none;">Edit</button>
								</form>
								<form class="form" method="POST" action="ChangeImage.do" enctype="multipart/form-data">
									<div class="control-label">
										<label class="control-label">Image Set:</label> <br/>
										<img src="GetClientImage?id=${client.getId()}" height="100"/>
										
										<input type="file" name="image" class="form-control mt-2" required/>
										<button type="submit" class="btn btn-primary mt-2">Change</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<script>
				$('#username').on('keyup', function() {
					$.ajax({
						url: 'ValidateUsername.do',
						type: 'GET',
						data: {
							'username': $('#username').val()
						},
						success: function(data) {
							if(data == "Username is already taken.") {
								$('#validateUsername').css('color', 'red');
								$('#validateUsername').html(data);
								$('#btnSubmit').css('display', 'none');
							} else {
								$('#validateUsername').css('color', 'green');
								$('#validateUsername').html(data);
								$('#btnSubmit').css('display', 'block');
							}
						}
					});
				});
				
				$('#password').on('keyup', function() {
					$.ajax({
						url: 'ValidatePassword.do',
						type: 'GET',
						data: {
							'password': $('#password').val()
						},
						success: function(data) {
							if(data == "Current password does not match.") {
								$('#validateOldPassword').css('color', 'red');
								$('#validateOldPassword').html(data + "<br/>");
								$('#btnSubmitPass').css('display', 'none');
							} else {
								$('#validateOldPassword').css('color', 'green');
								$('#validateOldPassword').html(data + "<br/>");
								$('#btnSubmitPass').css('display', 'block');
							}
						}
					});
				});
				
				$('#newPassword').on('keyup', function() {
					if($('#newPassword').val() != $('#retypedPassword').val()) {
						$('#validateMessage').css('color', 'red');
						$('#validateMessage').html('New and retyped passwords do not match. <br/>');
						$('#btnSubmitPass').css('display', 'none');
					} else {
						$('#validateMessage').css('color', 'green');
						$('#validateMessage').html('Passwords match. <br/>');
						$('#btnSubmitPass').css('display', 'block');
					}
				});
			
				$('#retypedPassword').on('keyup', function() {
					if($('#newPassword').val() != $('#retypedPassword').val()) {
						$('#validateMessage').css('color', 'red');
						$('#validateMessage').html('New and retyped passwords do not match. <br/>');
						$('#btnSubmitPass').css('display', 'none');
					} else {
						$('#validateMessage').css('color', 'green');
						$('#validateMessage').html('Passwords match. <br/>');
						$('#btnSubmitPass').css('display', 'block');
					}
				});
			</script>
		</div>
	</body>
</html>
