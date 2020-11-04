<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="bean.*, dao.*, model.*, util.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Product Sales Panel</title>
  	</head>
	<body id="page-top">
		<c:set var="pastFiveMonths" value="${DateHandler.getFiveMonthsFromNow()}"/>
		<script>
			var months = [];
			<c:forEach items="${pastFiveMonths}" var="month">
				months.push("${month}");
			</c:forEach>
		</script>
		
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
					<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
					<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
					<jsp:useBean id="salesDao" class="dao.SalesDAO"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Procuct Sales Panel</li>
					</ol>
					
					<div class="row">
						<c:set var="categoryList" value="${categoryDao.getAllRecords()}"/>
						
						<c:forEach items="${categoryList}" var="category">
							
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
								<div class="card mt-2 mb-2">
									<div class="card-header">
										<i class="fas fa-utensils"></i>
										Sales: <c:out value="${category.getName()}"/>
									</div>
									<div class="card-body" style="max-height: 310px; overflow-y: scroll;">
										<c:forEach items="${foodDao.getFoodListByCategory(category)}" var="food">
											<h3>${food.getName()} Sales</h3>
											
											<table class="table">
												<thead>
													<tr>
														<th>Month</th>
														<th>Sales</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${salesDao.getFoodSalesInFiveMonths(food)}" var="sales">
														<tr>
															<td><fmt:formatDate pattern="MMMM-yyyy" value="${sales.getDate()}"/></td>
															<td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${sales.getSales()}"/></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:forEach>
									</div>
								</div>
							</div>
							
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
								<div class="card mt-2 mb-2">
									<div class="card-header">
										<i class="fas fa-utensils"></i>
										Sales: <c:out value="${category.getName()}"/>
									</div>
									<div class="card-body">
										<canvas id="${category.getName()}SalesChart" width="100%"></canvas> <br/>
										
										<script>
											new Chart(document.getElementById('${category.getName()}SalesChart'), {
												type: 'line',
												data: {
													labels: months,
													datasets: [
														
														<c:forEach items="${foodDao.getFoodListByCategory(category)}" var="food">
															<c:set var="foodSales" value="${salesDao.getFoodSalesInFiveMonths(food)}"/>
															<c:set var="color" value="${ColorHandler.getRandomHexColor()}"/>
															{
																label: '${food.getName()}',
																fill: false,
																backgroundColor: '${color}',
																borderColor: '${color}',
																data: [
																	
																	<c:forEach items="${foodSales}" var="sales" varStatus="status">
																		<c:out value="${sales.getSales()}"/>
																		<c:if test="${!status.last}">,</c:if>
																	</c:forEach>
																]
															},
														</c:forEach>
													]
												}
											});
										</script>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
