<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="card" id="side-menu" data-aos="zoom-in-right">
	<div class="card-header text-center" style="background-color: #7C281C;">
		<b style="font-size: 25px; color: white;">Categories</b>
	</div>
	<div class="card-body">
		<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
		<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
		<c:set var="categoryList" value="${categoryDao.getAllRecords()}"/>
		
		<c:forEach items="${categoryList}" var="category">
			<a style="font-size: 20px; text-decoration: none;" href="Menu.jsp?categoryId=${category.getId()}"><c:out value="${category.getName()} (${foodDao.getFoodCountByCategory(category)})"/></a> <br/>
		</c:forEach>
	</div>
</div>