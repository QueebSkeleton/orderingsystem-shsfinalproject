<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="bean.*, dao.*, util.*, model.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Administrator Dashboard</title>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<ol class="breadcrumb">
						<li class="breadcrumb-item active">Dashboard</li>
					</ol>
					
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 mb-1">
							<div class="card text-white bg-success h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-shopping-cart"></i>
									</div>
									<div class="mr-5">Orders</div>
								</div>
								<a class="card-footer text-white clearfix small z-1" href="OrdersPanel.jsp">
									<span class="float-left">View Details</span>
									<span class="float-right">
										<i class="fas fa-angle-right"></i>
									</span>
								</a>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 mb-1">
							<div class="card text-white bg-primary h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-users"></i>
									</div>
									<div class="mr-5">Clients</div>
								</div>
								<a class="card-footer text-white clearfix small z-1" href="ClientsPanel.jsp">
									<span class="float-left">View Details</span>
									<span class="float-right">
										<i class="fas fa-angle-right"></i>
									</span>
								</a>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 mb-1">
							<div class="card text-white bg-danger h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-user-tie"></i>
									</div>
									<div class="mr-5">Employees</div>
								</div>
								<a class="card-footer text-white clearfix small z-1" href="EmployeesPanel.jsp">
									<span class="float-left">View Details</span>
									<span class="float-right">
										<i class="fas fa-angle-right"></i>
									</span>
								</a>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-6 col-lg-3 mb-1">
							<div class="card text-white bg-warning h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-chart-line"></i>
									</div>
									<div class="mr-5">Sales</div>
								</div>
								<a class="card-footer text-white clearfix small z-1" href="ProductSalesPanel.jsp">
									<span class="float-left">View Details</span>
									<span class="float-right">
										<i class="fas fa-angle-right"></i>
									</span>
								</a>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="card mt-2 mb-2">
								<div class="card-header">
									<i class="fas fa-dollar-sign"></i>
									Recent Sales
								</div>
								<div class="card-body">
									<canvas id="recentSalesChart" height="100"></canvas>
									
									<jsp:useBean id="salesDao" class="dao.SalesDAO"/>
									
									<script>
										var chartLabels = [];
										<c:forEach items="${DateHandler.getDateTillNow(TimeFrom.DAILY, 11)}" var="date">
											chartLabels.push('<fmt:formatDate pattern="MMM-dd-yyyy" value="${date}"/>');
										</c:forEach>
										
										<%
											out.println("new Chart(document.getElementById('recentSalesChart'), {");
											out.println("type: 'line',");
											out.println("data: {");
											out.println("labels: chartLabels,");
											out.println("datasets: [");
											
											String color = ColorHandler.getRandomHexColor();
											
											out.println("{");
											out.println("label: 'Sales',");
											out.println("lineTension: 0.3,");
											out.println("backgroundColor: 'rgba(2,117,216,0.2)',");
											out.println("borderColor: 'rgba(2,117,216,1)',");
											out.println("pointRadius: 5,");
											out.println("pointBackgroundColor: 'rgba(2,117,216,1)',");
											out.println("pointBorderColor: 'rgba(255,255,255,0.8)',");
											out.println("pointHoverRadius: 5,");
											out.println("pointHoverBackgroundColor: 'rgba(2,117,216,1)',");
											out.println("pointHitRadius: 50,");
											out.println("pointBorderWidth: 2,");
											out.println("data: [");
											
											List<SalesBean> salesList = salesDao.getSalesTillNow(TimeFrom.DAILY, 11);
											
											for(int i = 0; i < salesList.size(); i++) {
												SalesBean sales = salesList.get(i);
												
												out.print(sales.getSales());
												
												if(i != salesList.size() - 1) out.println(",");
											}
											
											out.println("]");
											out.println("}");
											out.println("]");
											out.println("}");
											out.println("});");
										%>
									</script>
								</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<div class="card mt-2 mb-2" id="orderStatuses">
								<div class="card-header">
									<i class="fas fa-shopping-cart"></i>
									Order Monitoring
								</div>
								<div class="card-body">
									<jsp:include page="OrderStatusPanel.jsp"></jsp:include>
								</div>
							</div>
						</div>
						
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<div class="card mt-2 mb-2" id="orderNotifs">
								<div class="card-header">
									<i class="fas fa-exclamation-circle"></i>
									Notifications: Orders
								</div>
								<div class="card-body">
									<jsp:include page="NotificationsPanel.jsp"></jsp:include>
								</div>
							</div>
						</div>
					</div>
				
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
		
		<script>
			function pollNotifs() {
				setTimeout(function() {
					$.ajax({
						url: 'NotificationsPanel.jsp',
						type: 'GET',
						success: function(data) {
							$('#orderNotifs > .card-body').html(data);
							pollNotifs();
						}
					});
				}, 2500);
			}
			
			pollNotifs();
			
			function pollOrderStatus() {
				setTimeout(function() {
					$.ajax({
						url: 'OrderStatusPanel.jsp',
						type: 'GET',
						success: function(data) {
							$('#orderStatuses > .card-body').html(data);
							pollOrderStatus();
						}
					});
				}, 10000);
			}
			
			pollOrderStatus();
			
			function seeAllNotifs() {
				setTimeout(function() {
					if($('#orderNotifs').is(':visible')) {
						$.ajax({
							url: 'UpdateNotificationStatus.do',
							type: 'GET',
							success: function(data) {
								seeAllNotifs();
							}
						});
					}
				}, 500);
			}
			
			seeAllNotifs();
		</script>
		
	</body>
</html>
