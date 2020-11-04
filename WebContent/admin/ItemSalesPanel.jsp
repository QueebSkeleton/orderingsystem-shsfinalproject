<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Item Sales Panel</title>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<jsp:useBean id="itemOrderDao" class="dao.ItemOrderDAO"/>
					<c:set var="itemOrderList" value="${itemOrderDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Languages Panel</li>
					</ol>
					
					<div class="card mt-2 mb-2">
						<div class="card-header">
							<i class="fas fa-table"></i>
							Item Sales Table
						</div>
						<div class="card-body">
							
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Item Sales Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addItemSaleModal">Add Sale</button>
								</div>
							</div>
							
							<jsp:include page="ItemSaleAddModal.jsp"></jsp:include>
							
							<c:choose>
								<c:when test="${not empty itemOrderList}">
									<div class="table-responsive">
										<table class="table table-bordered table-hover" id="dataTable">
											<thead>
												<tr>
													<th>#.</th>
													<th>Client</th>
													<th>Employee</th>
													<th>Date</th>
													<th>Item</th>
													<th>Quantity</th>
													<th>Total</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${itemOrderList}" var="itemOrder" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td><c:out value="${itemOrder.getOrder().getClient().getFirstName()} ${itemOrder.getOrder().getClient().getLastName()}"/></td>
														<td><c:out value="${itemOrder.getOrder().getEmployee().getFirstName()} ${itemOrder.getOrder().getEmployee().getLastName()}"/></td>
														<td><fmt:formatDate pattern="MMMM-dd-yyyy" value="${itemOrder.getOrder().getDateOrdered()}"/>
														<td><c:out value="${itemOrder.getFood().getName()}"/></td>
														<td><c:out value="${itemOrder.getQuantity()}"/></td>
														<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity()}"/></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="text-center">
										<i class="fas fa-exclamation-circle" style="font-size: 200px;"></i> <br/>
										<font style="font-size: 20px;">No languages registered. <br/> Please add a new one.</font>
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
