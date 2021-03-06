<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Clients Panel</title>
		
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Client?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
					<c:set scope="request" var="clientList" value="${clientDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Clients Panel</li>
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
							Clients Table
						</div>
						<div class="card-body">
						
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Clients Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addClientModal">Add Client</button>
								</div>
							</div>
						
							<jsp:include page="ClientAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="clientEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Client</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="clientEditForm" method="POST" action="EditClient.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${not empty clientList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Image</th>
													<th>Name</th>
													<th>Address</th>
													<th>Contact</th>
													<th>Email</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${clientList}" var="client" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td>
															<img src="../GetClientImage?id=${client.getId()}" height="40"/>
															<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#imageForm${client.getId()}"><i class="fas fa-exchange-alt"></i></button>
															
															<div class="modal fade" id="imageForm${client.getId()}">
																<div class="modal-dialog">
																	<div class="modal-content">
																	
																		<div class="modal-header">
																			<h1 class="modal-title">Change Image</h1>
																			<button type="button" class="close" data-dismiss="modal">&times;</button>
																		</div>
																		
																		<form class="form" method="POST" action="UpdateClientImage.do" enctype="multipart/form-data" autocomplete="off">
																			<div class="modal-body">
																				<div class="form-group">
																					<label class="control-label">ID:</label>
																					<input type="number" name="id" class="form-control" value="${client.getId()}" readonly required/>
																				</div>
																				<div class="form-group">
																					<label class="control-label">Image Set:</label> <br/>
																					<img src="../GetClientImage?id=${client.getId()}" height="100"/>
																				</div>
																				<div class="form-group">
																					<label class="control-label">Image to Replace:</label>
																					<input type="file" name="image" class="form-control" required/> 
																				</div>
																			</div>
																			<div class="modal-footer">
																				<button type="submit" class="btn btn-primary">Update</button>
																				<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
																			</div>
																		</form>
																	
																	</div>
																</div>
															</div>
														</td>
														<td><c:out value="${client.getFirstName()} ${client.getLastName()}"/></td>
														<td><c:out value="${client.getAddress()}"/></td>
														<td><c:out value="${client.getContactNumber()}"/></td>
														<td><c:out value="${client.getEmailAddress()}"/></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${client.getId()}" data-toggle="modal" data-target="#clientEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteClient.do">
																<input type="hidden" name="id" value="${client.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${client.getId()}').click(function() {
															$.ajax({
																url: 'ClientEditModal.jsp?id=${client.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#clientEditForm').html(data);
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
										<font style="font-size: 20px;">No clients registered. <br/> Please add a new one.</font>
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
