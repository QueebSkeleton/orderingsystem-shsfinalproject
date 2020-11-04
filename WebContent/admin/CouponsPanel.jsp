<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Coupons Panel</title>
		
		<script>
			function confirmDelete() {
				return confirm("Are you sure you want to delete this Coupon?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="couponDao" class="dao.CouponDAO"/>
					<c:set scope="request" var="couponList" value="${couponDao.getAllRecords()}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Coupons Panel</li>
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
							Coupons Table
						</div>
						<div class="card-body">
							
							<div class="row">
								<div class="col-sm-12 col-md-6">
									<h2>Coupons Panel</h2>
								</div>
								<div class="col-sm-12 col-md-6 text-right">
									<button type="button" class="btn btn-sm btn-primary" data-toggle="modal" data-target="#addCouponModal">Add Coupon</button>
								</div>
							</div>
					
							<jsp:include page="CouponAddModal.jsp"></jsp:include>
							
							<div class="modal fade" id="couponEditModal">
								<div class="modal-dialog">
									<div class="modal-content">
									
										<div class="modal-header">
											<h1 class="modal-title">Edit Coupon</h1>
											<button type="button" class="close" data-dismiss="modal">&times;</button>
										</div>
										
										<form class="form" id="couponEditForm" method="POST" action="EditCoupon.do" autocomplete="off">
											
										</form>
										
									</div>
								</div>
							</div>
						
							<c:choose>
								<c:when test="${not empty couponList}">
									<div class="table-responsive">
										<table class="table" id="dataTable">
											<thead class="">
												<tr>
													<th>#.</th>
													<th>Created On</th>
													<th>Valid To</th>
													<th>Code</th>
													<th>Amount</th>
													<th>Edit</th>
													<th>Delete</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${couponList}" var="coupon" varStatus="status">
													<tr>
														<td><c:out value="${status.index + 1}"/></td>
														<td><c:out value="${coupon.getDateCreated()}"/></td>
														<td><c:out value="${coupon.getValidUntil()}"/></td>
														<td><c:out value="${coupon.getCode()}"/></td>
														<td><fmt:formatNumber type="percent" minIntegerDigits="1" maxIntegerDigits="3" value="${coupon.getDiscountAmount()}"/></td>
														<td><button type="button" class="btn btn-primary" id="btnEdit${coupon.getId()}" data-toggle="modal" data-target="#couponEditModal"><i class="fas fa-edit"></i></button></td>
														<td>
															<form method="POST" action="DeleteCoupon.do">
																<input type="hidden" name="id" value="${coupon.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete()"><i class="fas fa-trash"></i></button>
															</form>
														</td>
													</tr>
													
													<script>
														$('#btnEdit${coupon.getId()}').click(function() {
															$.ajax({
																url: 'CouponEditModal.jsp?id=${coupon.getId()}',
																type: 'GET',
																success: function(data) {
																	$('#couponEditForm').html(data);
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
										<font style="font-size: 20px;">No coupons registered. <br/> Please add a new one.</font>
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
