<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>View Food</title>
		
		<script>
			function confirmDelete(id) {
				return confirm("Are you sure you want to delete Translation with ID: " + id + "?");
			}
		</script>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
					
					<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
					<c:set var="food" value="${foodDao.getByID(Long.parseLong(param.id))}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item">
							<a href="FoodsPanel.jsp">Foods Panel</a>
						</li>
						<li class="breadcrumb-item active">View Food</li>
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
							View Food Item
						</div>
						<div class="card-body">
							<h3>View Food Item</h3>
							<hr/>
							
							<b>Name:</b> <c:out value="${food.getName()}"/> <br/>
							<b>Category:</b> <c:out value="${food.getCategory().getName()}"/> <br/>
							<b>Supplier:</b> <c:out value="${food.getSupplier().getName()}"/> <br/>
							<b>Unit Price:</b> <c:out value="${food.getUnitPrice()}"/> <br/> <br/>
							
							<div class="row">
								<div class="col-lg-6">
									<b>Translations:</b>
								</div>
							</div>
							
							<jsp:useBean id="languageDao" class="dao.LanguageDAO"/>
							<c:set var="languageList" value="${languageDao.getAllRecords()}"/>
							
							<jsp:useBean id="foodTranslationDao" class="dao.FoodTranslationDAO"/>
							<c:set var="translationList" value="${foodTranslationDao.getTranslationListByFood(food)}"/>
							
							<div class="table-responsive mt-2">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Language</th>
											<th>Name Translation</th>
											<th>Description Translation</th>
											<th>Set</th>
											<th>Delete</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>Default</td>
											<td><c:out value="${food.getName()}"/></td>
											<td><c:out value="${food.getDescription()}"/></td>
											<td>N/A</td>
											<td>N/A</td>
										</tr>
										
										<c:forEach items="${languageList}" var="language">
											<tr>
												<td><c:out value="${language.getName()}"/></td>
												
												<c:forEach items="${translationList}" var="translation">
													<c:if test="${translation.getLanguage().getId() eq language.getId()}">
														<c:set var="foodTranslation" value="${translation}"/>
													</c:if>
												</c:forEach>
												
												<c:choose>
													<c:when test="${not empty foodTranslation}">
														<td><c:out value="${foodTranslation.getName()}"/></td>
														<td><c:out value="${foodTranslation.getDescription()}"/></td>
														<td><a class="btn btn-primary" href="FoodSetTranslationForm.jsp?foodId=${food.getId()}&languageId=${language.getId()}"><i class="fas fa-edit"></i></a></td>
														<td>
															<form method="POST" action="DeleteTranslation.do">
																<input type="hidden" name="id" value="${foodTranslation.getId()}"/>
																<input type="hidden" name="foodId" value="${food.getId()}"/>
																<button type="submit" class="btn btn-danger" onclick="return confirmDelete(${foodTranslation.getId()})"><i class="fas fa-trash"></i></button>
															</form>
														</td>
																												
														<c:remove var="foodTranslation"/>
													</c:when>
													<c:otherwise>
														<td>N/A</td>
														<td>N/A</td>
														<td><a class="btn btn-primary" href="FoodSetTranslationForm.jsp?foodId=${food.getId()}&languageId=${language.getId()}"><i class="fas fa-edit"></i></a></td>
														<td>(Disabled)</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
				
				
				
				
				
					</div>
					
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
 		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
