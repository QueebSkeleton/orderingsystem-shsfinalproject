<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Food Order: Settings</title>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>
				
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Settings Panel</li>
					</ol>
					
					<span id="alertMessage"></span>
					
					<div class="card">
						<div class="card-header">
							<i class="fas fa-cog"></i>
							Settings Panel
						</div>
						<div class="card-body">
							<div class="row">
								<div class="col-lg-4">
								
									<h3 class="text-center">Company</h3>
									
									<div class="form-group">
										<label class="control-label">Company Name:</label>
										<div class="input-group">
											<input type="text" name="companyName" id="companyName" class="form-control" value="${settingsDao.getByKey('companyName').getSetvalue()}"/>
											<button type="button" id="btnCompanyNameSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnCompanyNameSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'companyName',
													'setvalue': $('#companyName').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
									<div class="form-group">
										<label class="control-label">Company Address:</label>
										<div class="input-group">
											<input type="text" name="companyAddress" id="companyAddress" class="form-control" value="${settingsDao.getByKey('companyAddress').getSetvalue()}"/>
											<button type="button" id="btnCompanyAddressSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnCompanyAddressSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'companyAddress',
													'setvalue': $('#companyAddress').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
									<div class="form-group">
										<label class="control-label">Person to Address:</label>
										<div class="input-group">
											<input type="text" name="person" id="person" class="form-control" value="${settingsDao.getByKey('companyPerson').getSetvalue()}"/>
											<button type="button" id="btnCompanyPersonSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnCompanyPersonSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'companyPerson',
													'setvalue': $('#person').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
									<div class="form-group">
										<label class="control-label">Delivery Fee:</label>
										<div class="input-group">
											<input type="text" name="deliveryFee" id="deliveryFee" class="form-control" value="${settingsDao.getByKey('deliveryFee').getSetvalue()}"/>
											<button type="button" id="btnDeliveryFeeSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnDeliveryFeeSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'deliveryFee',
													'setvalue': $('#deliveryFee').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
								</div>
								<div class="col-lg-4">
								
									<h3 class="text-center">Website</h3>
								
									<form class="form" method="POST" action="UpdateLogo.do" enctype="multipart/form-data" autocomplete="off">
										<div class="form-group">
											<label class="control-label">Logo:</label> <br/>
											<img src="../GetLogo.do" width="50" height="50"/> <br/>
											
											<div class="form-group">
												<label class="control-label">Place a file to change:</label>
												<div class="input-group">
													<input type="file" name="image" class="form-control" required/>
													<button type="submit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
												</div>
											</div>
										</div>
									</form>
									
									<div class="form-group">
										<label class="control-label">Website Header:</label>
										<div class="input-group">
											<input type="text" name="headerName" id="headerName" class="form-control" value="${settingsDao.getByKey('headerName').getSetvalue()}"/>
											<button type="button" id="btnHeaderNameSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnHeaderNameSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'headerName',
													'setvalue': $('#headerName').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
									<div class="form-group">
										<label class="control-label">Website Description:</label>
										<div class="input-group">
											<input type="text" name="websiteDescription" id="websiteDescription" class="form-control" value="${settingsDao.getByKey('websiteDescription').getSetvalue()}"/>
											<button type="button" id="btnWebsiteDescriptionSubmit" class="btn btn-primary"><i class="fas fa-arrow-up"></i></button>
										</div>
									</div>
									
									<script>
										$('#btnWebsiteDescriptionSubmit').click(function() {
											$.ajax({
												url: 'UpdateCompanySettings.do',
												type: 'POST',
												data: {
													'keyname': 'websiteDescription',
													'setvalue': $('#websiteDescription').val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
								</div>
								<div class="col-lg-4">
									
									<form class="form" id="adminAccountForm" method="POST" action="UpdateAdministratorAccount.do">
										<h3 class="text-center">Administrator</h3>
										
										<jsp:useBean id="userDao" class="dao.UserDAO"/>
										<c:set var="administrator" value="${userDao.getAdministrator()}"/>
										
										<div class="form-group">
											<label class="control-label">Username:</label>
											<input type="text" name="username" class="form-control" value="${administrator.getUsername()}" required/>
										</div>
										<div class="form-group">
											<label class="control-label">Password:</label>
											<input type="text" name="password" class="form-control" value="${administrator.getPassword()}" required/>
										</div>
										
										<button type="submit" class="btn btn-sm btn-primary">Update Account</button>
									</form>
									
									<script>
										$('#adminAccountForm').submit(function(e) {
											e.preventDefault();
											
											$.ajax({
												url: 'UpdateAdministratorAccount.do',
												type: 'POST',
												data: {
													'username': $("input[name='username']").val(),
													'password': $("input[name='password']").val()
												},
												success: function(data) {
													$('#alertMessage').html(data);
												}
											});
										});
									</script>
									
								</div>
								
							</div>
						</div>
					</div>
				
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
