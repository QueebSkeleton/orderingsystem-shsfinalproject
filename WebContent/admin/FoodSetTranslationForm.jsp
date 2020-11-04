<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="admin-assets/head.jsp"></jsp:include>
    	<title>Set Food Translation</title>
  	</head>
	<body id="page-top">
		<jsp:include page="admin-assets/HeadNavigator.jsp"></jsp:include>
		
		<div id="wrapper">
			<jsp:include page="admin-assets/Sidebar.jsp"></jsp:include>
			
			<div id="content-wrapper">
				<div class="container-fluid">
				
					<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
					<c:set var="food" value="${foodDao.getByID(Long.parseLong(param.foodId))}"/>
					
					<jsp:useBean id="languageDao" class="dao.LanguageDAO"/>
					<c:set var="language" value="${languageDao.getByID(Long.parseLong(param.languageId))}"/>
					
					<jsp:useBean id="foodTranslationDao" class="dao.FoodTranslationDAO"/>
					<c:set var="foodTranslation" value="${foodTranslationDao.getByFoodAndLanguageId(Long.parseLong(param.foodId), Long.parseLong(param.languageId))}"/>
					
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="Dashboard.jsp">Dashboard</a>
						</li>
						<li class="breadcrumb-item">
							<a href="FoodsPanel.jsp">Foods Panel</a>
						</li>
						<li class="breadcrumb-item">
							<a href="FoodView.jsp?id=${food.getId()}">View Food</a>
						</li>
						<li class="breadcrumb-item active">Set Translation</li>
					</ol>
					
					<div class="card mt-2 mb-2">
						<div class="card-header">
							<i class="fas fa-edit"></i>
							Set Food Translation
						</div>
						<div class="card-body">
							<form class="form" method="POST" action="${empty foodTranslation ? 'AddTranslation.do' : 'EditTranslation.do'}" autocomplete="off">
								<input type="hidden" name="id" value="${foodTranslation.getId()}"/>
								<div class="form-group">
									<label class="control-label">Food:</label>
									<input type="hidden" name="foodId" value="${food.getId()}"/>
									<input type="text" class="form-control" value="${food.getName()}" readonly/>
								</div>
								<div class="form-group">
									<label class="control-label">Language:</label>
									<input type="hidden" name="languageId" value="${language.getId()}"/>
									<input type="text" class="form-control" value="${language.getName()}" readonly/>
								</div>
								<div class="form-group">
									<label class="control-label">Name Translation:</label>
									<input type="text" name="name" class="form-control" value="${foodTranslation.getName()}" required/>
								</div>
								<div class="form-group">
									<label class="control-label">Description Translation:</label>
									<textarea name="description" class="form-control"><c:out value="${foodTranslation.getDescription()}"/></textarea>
								</div>
								<button type="submit" class="btn btn-primary">Set</button>
							</form>
						</div>
					</div>
										
				</div>
				<jsp:include page="admin-assets/Footer.jsp"></jsp:include>
			</div>
		</div>
		
		<jsp:include page="admin-assets/bottom-scripts.jsp"></jsp:include>
	</body>
</html>
