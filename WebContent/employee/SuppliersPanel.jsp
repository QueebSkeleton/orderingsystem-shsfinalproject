<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Suppliers Panel</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container-fluid p-4">
			<div class="row">
				<jsp:include page="employee-assets/ProfileMenu.jsp"></jsp:include>
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
					<div class="card">
						<div class="card-header text-white" style="background-color: #36679E;">
							<i class="fas fa-table"></i>
							Suppliers Table
						</div>
						<div class="card-body">
							<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
							<c:set scope="request" var="supplierList" value="${supplierDao.getAllRecords()}"/>

							<c:if test="${not empty alertMsg}">
								<jsp:include page="employee-assets/NotificationAlert.jsp">
									<jsp:param name="alertType" value="${alertType}"/>
									<jsp:param name="alertMsg" value="${alertMsg}"/>
								</jsp:include>
								
								<c:remove scope="session" var="alertType"/>
								<c:remove scope="session" var="alertMsg"/>
							</c:if>
							
							<c:choose>
								<c:when test="${not empty supplierList}">
									<div class="table-responsive">
										<table class="table table-bordered text-center">
											<thead class="">
												<tr>
													<th>Name</th>
													<th>Address</th>
													<th>City</th>
													<th>Region</th>
													<th>Postal Code</th>
													<th>Contact Number</th>
													<th>Fax Number</th>
													<th>Edit</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${supplierList}" var="supplier">
													<tr>
														<td><c:out value="${supplier.getName()}"/></td>
														<td><c:out value="${supplier.getAddress()}"/></td>
														<td><c:out value="${supplier.getCity()}"/></td>
														<td><c:out value="${supplier.getRegion()}"/></td>
														<td><c:out value="${supplier.getPostalCode()}"/></td>
														<td><c:out value="${supplier.getContactNumber()}"/></td>
														<td><c:out value="${supplier.getFaxNumber()}"/></td>
														<td><a class="btn btn-info" href="SupplierEditForm.jsp?id=${supplier.getId()}"><i class="fas fa-edit"></i></a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="text-center">
										<i class="fas fa-exclamation-circle" style="font-size: 200px;"></i> <br/>
										<font style="font-size: 20px;">No suppliers registered. <br/> Please add a new one.</font>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		
		<jsp:include page="employee-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
