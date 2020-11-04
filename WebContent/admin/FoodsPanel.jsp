<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Foods Panel</title>
		
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Food item?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
			
					<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
					<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
					
					<c:if test="${not empty param.categoryId}">
						<c:set var="category" value="${categoryDao.getByID(Long.parseLong(param.categoryId))}"/>
					</c:if>
					
					<c:choose>
						<c:when test="${empty category}">
							<c:set var="foodList" value="${foodDao.getAllRecords()}"/>
						</c:when>
						<c:otherwise>
							<c:set var="foodList" value="${foodDao.getFoodListByCategory(category)}"/>
						</c:otherwise>
					</c:choose>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Foods Panel</li>
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
							Food Items Table
						</div>
						<div class="card-body">
						
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Foods Panel<c:if test="${not empty category}">: <c:out value="${category.getName()}"/></c:if></h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary mb-2" data-toggle="modal" data-target="#addFoodModal">Add Food</button>
								</div>
							</div>
							
							<jsp:include page="FoodAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="foodEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Food</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="foodEditForm" method="POST" action="EditFood.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${not empty foodList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Image</th>
													<th>Name</th>
													<th>Category</th>
													<th>Stock</th>
													<th>Unit Price</th>
													<th>View</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${foodList}" var="food" varStatus="status">
													<tr class="${food.getUnitsInStock() eq 0 ? 'table-warning' : ''}">
														<td><c:out value="${status.index + 1}"/></td>
														<td>
															<img src="../GetFoodImage?id=${food.getId()}" height="40"/>
															<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#imageForm${food.getId()}"><i class="fas fa-exchange-alt"></i></button>
															
															<div class="modal fade" id="imageForm${food.getId()}">
																<div class="modal-dialog">
																	<div class="modal-content">
																	
																		<div class="modal-header">
																			<h1 class="modal-title">Change Image</h1>
																			<button type="button" class="close" data-dismiss="modal">&times;</button>
																		</div>
																		
																		<form class="form" method="POST" action="UpdateFoodImage.do" enctype="multipart/form-data" autocomplete="off">
																			<div class="modal-body">
																				<div class="form-group">
																					<label class="control-label">ID:</label>
																					<input type="number" name="id" class="form-control" value="${food.getId()}" readonly required/>
																				</div>
																				<div class="form-group">
																					<label class="control-label">Image Set:</label> <br/>
																					<img src="../GetFoodImage?id=${food.getId()}" height="100"/>
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
														<td><c:out value="${food.getName()}"/></td>
														<td><c:out value="${food.getCategory().getName()}"/></td>
														<td><c:out value="${food.getUnitsInStock()}"/></td>
														<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${food.getUnitPrice()}"/></td>
														<td><a class="btn btn-info" href="FoodView.jsp?id=${food.getId()}"><i class="fas fa-eye"></i></a></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${food.getId()}" data-toggle="modal" data-target="#foodEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteFood.do">
																<input type="hidden" name="id" value="${food.getId()}"/>
																<input type="hidden" name="categoryRedirect" value="${param.categoryId}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${food.getId()}').click(function() {
															$.ajax({
																url: "FoodEditModal.jsp?${not empty param.categoryId ? 'categoryId=' += param.categoryId += '&' : ''}id=${food.getId()}",
																type: 'GET',
																success: function(data) {
																	$('#foodEditForm').html(data);
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
										<font style="font-size: 20px;">No food items registered. <br/> Please add a new one.</font>
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
