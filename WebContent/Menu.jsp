<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="model.StocksHandler" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
	<c:set var="category" value="${categoryDao.getByID(Long.parseLong(param.categoryId))}"/>

	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
		<title>Menu: <c:out value="${category.getName()}"/></title>
	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
			
			<c:if test="${not empty modalMsg}">
				<jsp:include page="assets/include/NotificationModal.jsp">
					<jsp:param name="modalId" value="notifModal"/>
					<jsp:param name="modalTitle" value="${modalTitle}"/>
					<jsp:param name="modalMessage" value="${modalMsg}"/>
				</jsp:include>
				
				<script>
					$("#notifModal").modal("show");
				</script>
				
				<c:remove var="modalTitle"/>
				<c:remove var="modalMsg"/>
			</c:if>
			
			<div class="container-fluid pt-4 pl-4">	
				<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
				<c:set var="foodList" value="${foodDao.getFoodListByCategory(category)}"/>
				
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-4 col-lg-3 menu">
						<jsp:include page="assets/include/SideMenu.jsp"></jsp:include>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-8 col-lg-9" id="foodMenu">
						<div class="container menuContainer">
							<h1 class="text-center text-white"><c:out value="${category.getName()}"/></h1>
							<c:choose>
								<c:when test="${not empty foodList}">
									<div class="card-columns" id="menu-columns">
										<c:forEach items="${foodList}" var="food" varStatus="status">
											
											<c:forEach items="${StocksHandler.foodStocksList}" var="foodStock">
												<c:if test="${foodStock.getFood().getId() eq food.getId()}">
													<c:set var="unitsInStock" value="${foodStock.getFood().getUnitsInStock()}"/>
												</c:if>
											</c:forEach>
											
											<c:forEach items="${cart}" var="itemOrder">
												<c:if test="${food.getId() eq itemOrder.getFood().getId()}">
													<c:set var="isOrdered" value="${true}"/>
												</c:if>
											</c:forEach>
											
											<c:if test="${not empty countrySet}">
												<jsp:useBean id="foodTranslationDao" class="dao.FoodTranslationDAO"/>
												<c:set var="translation" value="${foodTranslationDao.getByFoodAndLanguageId(food.getId(), countrySet.getLanguage().getId())}"/>
											</c:if>
										
											<div class="card foodCard" data-aos="fade-up">
												<div class="card-body text-center">
													<img src="GetFoodImage?id=${food.getId()}" width="85%" alt="Image of ${food.getName()}" style="border: 1px solid black;"/> <br/>
													<p class="text-left">
														<c:choose>
															<c:when test="${unitsInStock != 0 || isOrdered}">
																<a href="ViewItem.jsp?categoryId=${food.getCategory().getId()}&foodId=${food.getId()}" style="font-size: 22px; text-decoration: none; margin-left: 10px;"><c:out value="${empty translation ? food.getName() : translation.getName()}"/></a> <br/>
															</c:when>
															<c:otherwise>
									 							<p style="font-size: 22px;"><c:out value="${empty translation ? food.getDescription() : translation.getDescription()}"/> (Not Available)</p>
															</c:otherwise>
														</c:choose>
														<b><c:out value="${empty countrySet ? '$' : countrySet.getZone().getCurrency().getCode()}"/><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${empty countrySet ? food.getUnitPrice() : food.getUnitPrice() * countrySet.getZone().getCurrency().getExchangeRate()}"/></b> <br/>
														
														<c:choose>
															<c:when test="${empty food.getDescription() and empty translation.getDescription()}">
																No description has been placed for this item.
															</c:when>
															<c:otherwise>
																<c:out value="${empty countrySet ? food.getDescription() : translation.getDescription()}"/>
															</c:otherwise>
														</c:choose>
													</p>
												</div>
											</div>
											
											<c:remove var="isOrdered"/>
											
										</c:forEach>
									</div>
								</c:when>
								<c:otherwise>
									<p class="text-center">
										<i class="fas fa-ban" style="font-size: 200px;"></i> <br/>
										<font style="font-size: 25px;">
											Sorry, we currently don't have <br/>
											items for this category.
										</font>
									</p>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</body>
</html>