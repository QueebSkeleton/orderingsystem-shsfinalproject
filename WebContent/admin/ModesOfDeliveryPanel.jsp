<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Modes of Delivery Panel</title>
		
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Mode of Delivery?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
			
					<jsp:useBean id="modeOfDeliveryDao" class="dao.ModeOfDeliveryDAO"/>
					<c:set scope="request" var="modeOfDeliveryList" value="${modeOfDeliveryDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Modes of Delivery Panel</li>
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
							Modes of Delivery Table
						</div>
						<div class="card-body">
							
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Modes of Delivery Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addModeOfDeliveryModal">Add Mode of Delivery</button>
								</div>
							</div>
							
							<jsp:include page="ModeOfDeliveryAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="modeOfDeliveryEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Mode of Delivery</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="modeOfDeliveryEditForm" method="POST" action="EditModeOfDelivery.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${not empty modeOfDeliveryList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Name</th>
													<th>Description</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${modeOfDeliveryList}" var="modeOfDelivery" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td><c:out value="${modeOfDelivery.getName()}"/></td>
														<td><c:out value="${modeOfDelivery.getDescription()}"/></td>			
														<td><button type="button" class="btn btn-primary" id="btnEdit${modeOfDelivery.getId()}" data-toggle="modal" data-target="#modeOfDeliveryEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteModeOfDelivery.do">
																<input type="hidden" name="id" value="${modeOfDelivery.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${modeOfDelivery.getId()}').click(function() {
															$.ajax({
																url: 'ModeOfDeliveryEditModal.jsp?id=${modeOfDelivery.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#modeOfDeliveryEditForm').html(data);
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
										<font style="font-size: 20px;">No modes of delivery registered. <br/> Please add a new one.</font>
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
