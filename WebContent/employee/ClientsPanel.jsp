<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="employee-assets/head.jsp"></jsp:include>
    	<title>Clients Panel</title>
  	</head>
	<body id="page-top">
		<jsp:include page="employee-assets/HeadNavigator.jsp"></jsp:include>
		
		<div class="container-fluid p-4">
			<div class="row">
				<jsp:include page="employee-assets/ProfileMenu.jsp"></jsp:include>
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
			
					<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
					<c:set scope="request" var="clientList" value="${clientDao.getAllRecords()}"/>
					
					<c:if test="${not empty alertMsg}">
						<jsp:include page="employee-assets/NotificationAlert.jsp">
							<jsp:param name="alertType" value="${alertType}"/>
							<jsp:param name="alertMsg" value="${alertMsg}"/>
						</jsp:include>
						
						<c:remove scope="session" var="alertType"/>
						<c:remove scope="session" var="alertMsg"/>
					</c:if>
					
					<div class="card">
						<div class="card-header text-white" style="background-color: #36679E;">
							<i class="fas fa-table"></i>
							List of Clients
						</div>
						<div class="card-body">
							<c:choose>
								<c:when test="${not empty clientList}">
									<div class="table-responsive">
										<table class="table table-bordered text-center mt-2">
											<thead>
												<tr>
													<th>Name</th>
													<th>Address</th>
													<th>Email Address</th>
													<th>Contact Number</th>
													<th>Edit</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${clientList}" var="client">
													<tr>
														<td><c:out value="${client.getFirstName()} ${client.getLastName()}"/></td>
														<td><c:out value="${client.getAddress()}"/>
														<td><c:out value="${client.getContactNumber()}"/></td>
														<td><c:out value="${client.getEmailAddress()}"/></td>
														<td><a class="btn btn-info" href="ClientEditForm.jsp?id=${client.getId()}"><i class="fas fa-edit"></i></a></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</c:when>
								<c:otherwise>
									<div class="text-center">
										<i class="fas fa-exclamation-circle" style="font-size: 200px;"></i> <br/>
										<font style="font-size: 20px;">No clients registered. <br/> Please add a new one.</font>
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
