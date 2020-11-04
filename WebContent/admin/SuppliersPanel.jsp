<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Suppliers Panel</title>
		
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Supplier?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
					<c:set scope="request" var="supplierList" value="${supplierDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Suppliers Panel</li>
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
							Suppliers Table
						</div>
						<div class="card-body">
							
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Suppliers Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addSupplierModal">Add Supplier</button>
								</div>
							</div>
							
							<jsp:include page="SupplierAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="supplierEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Supplier</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="supplierEditForm" method="POST" action="EditSupplier.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
						
							<c:choose>
								<c:when test="${not empty supplierList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Name</th>
													<th>Address</th>
													<th>City</th>
													<th>Region</th>
													<th>Zip Code</th>
													<th>Contact</th>
													<th>Fax</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${supplierList}" var="supplier" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td><c:out value="${supplier.getName()}"/></td>
														<td><c:out value="${supplier.getAddress()}"/></td>
														<td><c:out value="${supplier.getCity()}"/></td>
														<td><c:out value="${supplier.getRegion()}"/></td>
														<td><c:out value="${supplier.getPostalCode()}"/></td>
														<td><c:out value="${supplier.getContactNumber()}"/></td>
														<td><c:out value="${supplier.getFaxNumber()}"/></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${supplier.getId()}" data-toggle="modal" data-target="#supplierEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteSupplier.do">
																<input type="hidden" name="id" value="${supplier.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${supplier.getId()}').click(function() {
															$.ajax({
																url: 'SupplierEditModal.jsp?id=${supplier.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#supplierEditForm').html(data);
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
										<font style="font-size: 20px;">No suppliers registered. <br/> Please add a new one.</font>
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
