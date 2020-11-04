<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Item Sales Panel</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container-fluid p-4">
		
			<jsp:useBean id="itemOrderDao" class="dao.ItemOrderDAO"/>
			<c:set var="itemOrderList" value="${itemOrderDao.getItemOrderListByEmployee(employee)}"/>
			
			<div class="row">
				<jsp:include page="employee-assets/ProfileMenu.jsp"></jsp:include>
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
					<div class="card">
						<div class="card-header text-white" style="background-color: #36679E;">
							<i class="fas fa-panels"></i>
							Item Sales
						</div>
						<div class="card-body">
							<h3>Item Sales Panel</h3>
							
							<div class="table-responsive">
								<table class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>#.</th>
											<th>Client</th>
											<th>Food</th>
											<th>Quantity</th>
											<th>Total</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${itemOrderList}" var="itemOrder" varStatus="status">
											<tr>
												<td><c:out value="${status.index + 1}"/></td>
												<td><c:out value="${itemOrder.getOrder().getClient().getFirstName()} ${itemOrder.getOrder().getClient().getMiddleName()}"/></td>
												<td><c:out value="${itemOrder.getFood().getName()}"/></td>
												<td><c:out value="${itemOrder.getQuantity()}"/></td>
												<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity()}"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
