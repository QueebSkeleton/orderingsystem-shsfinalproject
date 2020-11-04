<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="util.OrderStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Orders Panel</title>
    	
    	<script>
			function confirmDelete(id) {
				return confirm("Are you sure you want to delete Order with ID: " + id + "?");
			}
    	</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
					<c:set scope="request" var="status"><%= request.getParameter("status") != null ?  OrderStatus.valueOf(request.getParameter("status")) : OrderStatus.ALL %></c:set>
					<c:set scope="request" var="orderList" value="${orderDao.getOrderListByStatus(status)}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Orders Panel</li>
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
							Orders Table
						</div>
						<div class="card-body">
							
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Orders Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addOrderModal">Add Order</button>
								</div>
							</div>
							
							<jsp:include page="OrderAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="orderEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Order</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="orderEditForm" method="POST" action="EditOrder.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
							
							<c:choose>
								<c:when test="${not empty orderList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead>
												<tr>
													<th>ID</th>
													<th>Client</th>
													<th>Employee</th>
													<th>Date</th>
													<th>Status</th>
													<th>Invoice</th>
													<th>View</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${orderList}" var="order">
													<tr>
														<td><c:out value="${order.getId()}"/></td>
														<td><c:out value="${order.getClient().getFirstName()} ${order.getClient().getLastName()}"/></td>
														<td><c:out value="${order.getEmployee().getFirstName()} ${order.getEmployee().getLastName()}"/></td>
														<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${order.getDateOrdered()}"/></td>
														<td><c:out value="${order.getStatus()}"/></td>
														<td>
															<c:if test="${order.getStatus() eq OrderStatus.DELIVERED}">
																<a class="btn btn-warning" href="../GetOrderInvoice.do?id=${order.getId()}&redirect=admin" target="_blank"><i class="fas fa-file"></i></a>
															</c:if>
														</td>
														<td><a class="btn btn-primary" href="ViewOrder.jsp?id=${order.getId()}"><i class="fas fa-eye"></i></a></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${order.getId()}" data-toggle="modal" data-target="#orderEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteOrder.do">
																<input type="hidden" name="id" value="${order.getId()}"/>
																<input type="hidden" name="statusRedirect" value="${param.status}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete(${order.getId()})"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${order.getId()}').click(function() {
															$.ajax({
																url: 'OrderEditModal.jsp?id=${order.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#orderEditForm').html(data);
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
										<font style="font-size: 20px;">No orders registered. <br/> Please add a new one.</font>
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
