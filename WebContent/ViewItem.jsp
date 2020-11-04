<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="model.StocksHandler" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
	<c:set var="foodId" value="<%= Long.parseLong(request.getParameter(\"foodId\"))%>"/>
	<c:set var="food" value="${foodDao.getByID(foodId)}"/>
	
	<c:forEach items="${cart}" var="cartItem">
		<c:if test="${cartItem.getFood().getId() == food.getId()}">
			<c:set var="itemOrder" value="${cartItem}"/>
			<c:set var="toEdit" value="${true}"/>
		</c:if>
	</c:forEach>
	
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
		<title>Add to Cart: <c:out value="${food.getName()}"/></title>
		
		<c:forEach items="${StocksHandler.foodStocksList}" var="foodStock">
			<c:if test="${foodStock.getFood().getId() eq food.getId()}">
				<c:set var="unitsInStock" value="${foodStock.getFood().getUnitsInStock()}"/>
			</c:if>
		</c:forEach>
			
		<script>
			var foodLimit = ${itemOrder.getQuantity() + unitsInStock};
		
			function addQuantity() {
				$('#quantityInput').val(function(i, oldVal) {
					if(oldVal < foodLimit)
						return ++oldVal;
					
					else
						return oldVal;
				});
			}
			
			function subtractQuantity() {
				$('#quantityInput').val(function(i, oldVal) { 
					if(oldVal > 1) 
						return --oldVal;
					
					else
						return oldVal;
				});
			}
		</script>
	</head>
	<body>
		<div class="wrapper">
			<jsp:include page="assets/include/HeadNavigator.jsp"></jsp:include>
			
			<div class="container rounded mt-4">
				<div class="p-4 border border-danger" id="itemPanel" style="min-width: 350px; max-width: 700px; background-color: white; margin: 0 auto; top: 0; left: 0; bottom: 0; right: 0;" data-aos="flip-left">
					<c:if test="${not empty countrySet}">
						<jsp:useBean id="foodTranslationDao" class="dao.FoodTranslationDAO"/>
						<c:set var="translation" value="${foodTranslationDao.getByFoodAndLanguageId(food.getId(), countrySet.getLanguage().getId())}"/>
					</c:if>
					
					<form method="POST" action="${empty itemOrder ? 'AddFoodToCart.do' : 'EditFoodFromCart.do' }">
						<div class="row">
							<div class="col-sm-6 text-right">
								<img src="GetFoodImage?id=${food.getId()}" height="175" alt="Image of <c:out value="${food.getName()}"/>" class="border border-danger" data-aos="zoom-in"/>
							</div>
							<div class="col-sm-6" data-aos="fade-up" data-aos-delay="300">
								<h3 class="text-danger"><c:out value="${empty translation ? food.getName() : translation.getName()}"/></h3> <br/>
								<i class="fas fa-utensils"></i> &emsp;
								
								<c:choose>
									<c:when test="${empty food.getDescription() and empty translation.getDescription()}">
										No description has been placed for this item.
									</c:when>
									<c:otherwise>
										<c:out value="${empty countrySet ? food.getDescription() : translation.getDescription()}"/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="row mt-4">
							<input type="hidden" name="foodId" value="${food.getId()}"/>
							
							<div class="col-sm-6">
								<h4 data-aos="fade-up" data-aos-delay="400">Price Each:</h4>
								<input type="text" name="foodPrice" class="form-control" value="<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${empty countrySet ? food.getUnitPrice() : food.getUnitPrice() * countrySet.getZone().getCurrency().getExchangeRate()}"/>" data-aos="fade-up" data-aos-delay="500" readonly/>
							</div>
							
							<div class="col-sm-6">
								<h4 data-aos="fade-up" data-aos-delay="400">Quantity:</h4>
								<div class="input-group" data-aos="fade-up" data-aos-delay="500">
									<button type="button" class="btn btn-danger" onclick="subtractQuantity()">-</button>
									<input type="text" name="quantity" id="quantityInput" class="form-control" value="<c:out value="${itemOrder != null ? itemOrder.getQuantity() : '1' }"/>" readonly/>
									<button type="button" class="btn btn-danger" onclick="addQuantity()">+</button>
								</div>
							</div>
						</div>
						<div class="row mt-4" data-aos="fade-up" data-aos-delay="600">
							<button type="submit" class="btn btn-danger ml-3"><c:out value="${empty itemOrder ? 'Add to Cart' : 'Update Quantity' }"/></button>
							<a class="btn btn-primary ml-1" href="Menu.jsp?categoryId=${param.categoryId}">Back to Menu</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>