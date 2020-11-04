<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Countries Panel</title>
    	
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Country?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="countryDao" class="dao.CountryDAO"/>
					<c:set scope="request" var="countryList" value="${countryDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Countries Panel</li>
					</ol>
					
					<c:if test="${not empty alertMessages}">
						<c:forEach items="${alertMessages}" var="alert">
							<jsp:include page="admin-assets/NotificationAlert.jsp">
								<jsp:param name="alertType" value="${alert.getType()}"/>
								<jsp:param name="alertMsg" value="${alert.getMessage()}"/>
							</jsp:include>
						</c:forEach>
						
						<c:remove scope="session" var="alertMessages"/>
					</c:if>
					
					<div class="card mt-2 mb-2">
						<div class="card-header">
							<i class="fas fa-table"></i>
							Countries Table
						</div>
						<div class="card-body">
						
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Countries Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addCountryModal">Add Country</button>
								</div>
							</div>
							
							<jsp:include page="CountryAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="countryEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Country</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="countryEditForm" method="POST" action="EditCountry.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${not empty countryList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Zone</th>
													<th>Language</th>
													<th>Name</th>
													<th>Code</th>
													<th>Description</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${countryList}" var="country" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td><c:out value="${country.getZone().getName()}"/></td>
														<td><c:out value="${country.getLanguage().getName()} | ${country.getLanguage().getCode()}"/></td>
														<td><c:out value="${country.getName()}"/></td>
														<td><c:out value="${country.getCode()}"/></td>
														<td><c:out value="${country.getDescription()}"/></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${country.getId()}" data-toggle="modal" data-target="#countryEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteCountry.do">
																<input type="hidden" name="id" value="${country.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${country.getId()}').click(function() {
															$.ajax({
																url: 'CountryEditModal.jsp?id=${country.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#countryEditForm').html(data);
																}
															});
														});
													</script>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="text-center">
										<i class="fas fa-exclamation-circle" style="font-size: 200px;"></i> <br/>
										<font style="font-size: 20px;">No countries registered. <br/> Please add a new one.</font>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					
					
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
