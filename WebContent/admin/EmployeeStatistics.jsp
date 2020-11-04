<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="bean.*, dao.*, util.*, model.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Food Order: Employee Statistics</title>
    	
    	<script>
			var chartLabels = [];
			<c:forEach items="${DateHandler.getDateTillNow(TimeFrom.DAILY, 5)}" var="date">
				chartLabels.push('<fmt:formatDate pattern="MMM-dd-yyyy" value="${date}"/>');
			</c:forEach>
    	</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="employeeDao" class="dao.EmployeeDAO"/>
					<c:set var="employeeList" value="${employeeDao.getAllRecords()}"/>
					
					<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
					<jsp:useBean id="salesDao" class="dao.SalesDAO"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item">
							<a href="EmployeesPanel.jsp">Employees Panel</a>
						</li>
						<li class="breadcrumb-item active">Statistics</li>
					</ol>
					
					<c:forEach items="${employeeList}" var="employee">
						
						<div class="card mt-4">
							<div class="card-header">
								<i class="fas fa-bar-chart"></i>
								Overview
							</div>
							<div class="card-body">
								<div class="row">
									<div class="col-lg-3 text-center border-right">
										<img src="../GetEmployeeImage?id=${employee.getId()}" width="80%"/> <br/>
										
										<h4><c:out value="${employee.getFirstName()} ${employee.getLastName()}"/></h4>
									</div>
									<div class="col-lg-4 border-right">
										<h5 class="text-center">Manage Orders</h5> <br/>
										
										<canvas id="employee${employee.getId()}OrdersChart"></canvas>
										<c:set var="totalOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.ALL)}"/>
										<c:set var="processingOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.PROCESSING)}"/>
										<c:set var="deliveredOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.DELIVERED)}"/>
										<c:set var="deniedOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.DENIED)}"/>
										<c:set var="cancelledOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.CANCELLED)}"/>
										
										<script>
											new Chart(document.getElementById('employee${employee.getId()}OrdersChart'), {
												type: 'doughnut',
												data: {
													 labels: ["Processing", "Delivered", "Denied", "Cancelled"],
												      datasets: [
												        {
												          label: "Order Statuses",
												          backgroundColor: ["#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
												          data: ['${processingOrderCount}', '${deliveredOrderCount}', '${deniedOrderCount}', '${cancelledOrderCount}']
												        }
												     ]
												},
											});
										</script>
									</div>
									<div class="col-lg-5">
										<h5 class="text-center">Sales so Far</h5>
										
										<canvas id="employee${employee.getId()}SalesChart"></canvas>
										
										<script>
										new Chart(document.getElementById('employee${employee.getId()}SalesChart'), {
											type: 'line',
												data: {
													labels: chartLabels,
													datasets: [	
														{
															label: 'Sales',
															lineTension: 0.3,
															backgroundColor: 'rgba(2,117,216,0.2)',
															borderColor: 'rgba(2,117,216,1)',
															pointRadius: 5,
															pointBackgroundColor: 'rgba(2,117,216,1)',
															pointBorderColor: 'rgba(255,255,255,0.8)',
															pointHoverRadius: 5,
															pointHoverBackgroundColor: 'rgba(2,117,216,1)',
															pointHitRadius: 50,
															pointBorderWidth: 2,
															
															data: [
															<c:forEach items="${salesDao.getEmployeeSalesTillNow(employee, TimeFrom.DAILY, 5)}" var="sales" varStatus="status">
																${sales.getSales()}
																<c:if test="${!status.last}">,</c:if>
															</c:forEach>
															]
														}
													]
											  }
										});
										</script>
									</div>
								</div>
							</div>
						</div>
						
					</c:forEach>
					
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
