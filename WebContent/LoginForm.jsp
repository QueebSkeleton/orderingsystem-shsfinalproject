<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
    	<title>Login or Sign Up</title>
  	</head>
	<body>
		<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
		
		<div class="container p-4">
			<div class="row">
				
				<!-- Login Form -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 border-right">
					<h2 class="text-center">Login</h2>
					
					<form class="form" method="POST" action="Login.do">
						<div class="form-group">
							<label class="control-label">Username</label>
							<input type="text" name="username" class="form-control form-control-sm" required />
						</div>
						<div class="form-group">
							<label class="control-label">Password</label>
							<input type="password" name="password" class="form-control form-control-sm" required />
						</div>
						<button type="submit" class="btn btn-sm btn-primary">Login</button>
					</form>
				</div>
				<!-- Login Form End -->
				
				<!-- Sign Up Form -->
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<h2 class="text-center">Sign Up</h2>
					
					<form class="form" method="POST" action="SignUp.do">
						<div class="form-group">
							<label class="control-label">First Name:</label>
							<input type="text" name="firstName" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Middle Name:</label>
							<input type="text" name="middleName" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Last Name:</label>
							<input type="text" name="lastName" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Address:</label>
							<input type="text" name="address" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Contact Number:</label>
							<input type="number" name="contactNumber" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Email Address:</label>
							<input type="email" name="emailAddress" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Username:</label>
							<input type="text" name="username" class="form-control form-control-sm" required/>
						</div>
						<div class="form-group">
							<label class="control-label">Password:</label>
							<input type="text" name="password" class="form-control form-control-sm" required/>
						</div>
						<button type="submit" class="btn btn-sm btn-primary">Sign Up</button>
					</form>
				</div>
				<!-- Sign Up Form End -->
				
			</div>
		</div>
		
		<jsp:include page="assets/include/Footer.jsp"></jsp:include>
		
	</body>
</html>
