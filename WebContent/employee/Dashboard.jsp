<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="util.OrderStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Employee Panel</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container-fluid p-4">
			<div class="row">
				<jsp:include page="employee-assets/ProfileMenu.jsp"></jsp:include>
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<div class="card" style="height: 305px;">
								<div class="card-header text-white" style="background-color: #36679E;">
									<i class="fas fa-user-tie"></i>
									Personal Information
								</div>
								<div class="card-body">
									<b>First Name:</b> <c:out value="${employee.getFirstName()}"/> <br/>
									<b>Middle Name:</b> <c:out value="${employee.getMiddleName()}"/> <br/>
									<b>Last Name:</b> <c:out value="${employee.getLastName()}"/> <br/>
									<b>Email Address:</b> <c:out value="${employee.getEmailAddress()}"/> <br/>
									<b>Contact Details:</b> <c:out value="${employee.getContactNumber()}"/> <br/>
									<b>Birthdate: </b> <c:out value="${employee.getBirthdate()}"/> <br/>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
							<div class="card">
								<div class="card-header text-white" style="background-color: #36679E;">
									<i class="fas fa-chart-line"></i>
									Brief Status
								</div>
								<div class="card-body">
									<jsp:useBean id="orderDao" class="dao.OrderDAO"/>
									<c:set var="totalOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.ALL)}"/>
									<c:set var="newOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.NEW)}"/>
									<c:set var="processingOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.PROCESSING)}"/>
									<c:set var="deliveredOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.DELIVERED)}"/>
									<c:set var="deniedOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.DENIED)}"/>
									<c:set var="cancelledOrderCount" value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.CANCELLED)}"/>
									
									<canvas id="statusChart" width="100%"></canvas>
									
									<script>
										new Chart(document.getElementById("statusChart"), {
										    type: 'doughnut',
										    data: {
										      labels: ["New", "Processing", "Delivered", "Denied", "Cancelled"],
										      datasets: [
										        {
										          label: "Order Statuses",
										          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
										          data: [${newOrderCount}, ${processingOrderCount}, ${deliveredOrderCount}, ${deniedOrderCount}, ${cancelledOrderCount}]
										        }
										      ]
										    },
										    options: {
										      title: {
										        display: true,
										        text: 'Brief Orders Status'
										      }
										    }
										});
									</script>
								</div>
							</div>
						</div>
					</div>
					<div class="row pt-4">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="card">
								<div class="card-header text-white" style="background-color: #36679E;">
									<i class="fas fa-box"></i>
									Quick Links
								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
											<div class="card border border-primary">
												<div class="card-body bg-primary align-middle text-center text-white">
													<i class="fas fa-shopping-cart" style="font-size: 100px;"></i>
												</div>
												<a class="card-footer text-primary clearfix small z-1" href="OrdersAssigned.jsp">
													Orders Assigned
													<span class="badge badge-pill badge-success">
														<c:out value="${orderDao.getOrderCountByEmployee(employee, OrderStatus.PROCESSING)}"/>
													</span>
													<span class="float-right">
														<i class="fas fa-angle-right"></i>
													</span>
												</a>
											</div>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
											<div class="card border border-danger">
												<div class="card-body bg-danger align-middle text-center text-white">
													<i class="fas fa-user" style="font-size: 100px;"></i>
												</div>
												<a class="card-footer text-danger clearfix small z-1" href="ClientsPanel.jsp">
													List of Clients
													<span class="float-right">
														<i class="fas fa-angle-right"></i>
													</span>
												</a>
											</div>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
											<div class="card border border-warning">
												<div class="card-body bg-warning align-middle text-center text-white">
													<i class="fas fa-box" style="font-size: 100px;"></i>
												</div>
												<a class="card-footer text-warning clearfix small z-1" href="SuppliersPanel.jsp">
													List of Suppliers
													<span class="float-right">
														<i class="fas fa-angle-right"></i>
													</span>
												</a>
											</div>
										</div>
										<div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
											<div class="card border border-success">
												<div class="card-body bg-success align-middle text-center text-white">
													<i class="fas fa-chart-line" style="font-size: 100px;"></i>
												</div>
												<a class="card-footer text-success clearfix small z-1" href="ItemSalesPanel.jsp">
													Item Sales
													<span class="float-right">
														<i class="fas fa-angle-right"></i>
													</span>
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
