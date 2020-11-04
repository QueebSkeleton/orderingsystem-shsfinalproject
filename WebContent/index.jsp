<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="assets/include/head.jsp"></jsp:include>
		
		<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>
		
		<title>Welcome to <c:out value="${settingsDao.getByKey('companyName').getSetvalue()}"/></title>
	</head>
	<body style="background-color: #F8C172;">
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
		
		<div class="container-fluid">
			<div class="row pt-4 pb-4">
				<div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
					<jsp:useBean id="bannerDao" class="dao.BannerDAO"/>
					<c:set var="bannerList" value="${bannerDao.getAllRecords()}"/>
					
					<c:choose>
						<c:when test="${not empty bannerList}">
							<div id="bannerCarousel" class="carousel slide" data-ride="carousel" data-aos="zoom-in">
								<ul class="carousel-indicators">
									<c:forEach items="${bannerList}" var="banner" varStatus="status">
										<li data-target="#bannerCarousel" data-slide-to="${status.index}" ${status.index eq 0 ? 'class=\"active\"' : ''}></li>
									</c:forEach>
								</ul>
								
								<div class="carousel-inner">
									<c:forEach items="${bannerList}" var="banner" varStatus="status">
										<div class="carousel-item text-center ${status.index eq 0 ? 'active' : ''}">
											<img src="GetBannerImage?id=${banner.getId()}" height="300"/>
										</div>
									</c:forEach>
							  	</div>
							  	
							  	<a class="carousel-control-prev text-body" href="#bannerCarousel" data-slide="prev">
							    	<i class="fas fa-angle-left fa-2x"></i>
							  	</a>
							  	<a class="carousel-control-next text-body" href="#bannerCarousel" data-slide="next">
							    	<i class="fas fa-angle-right fa-2x"></i>
							  	</a>                                                    
							</div>
						</c:when>
						<c:otherwise>
							<div class="align-middle">
								<h3 class="text-center">No Banners set.</h3>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 text-center">
					<h1 data-aos="zoom-in-left">Welcome to <b><c:out value="${settingsDao.getByKey('companyName').getSetvalue()}"/>!</b></h1> <br/>
					<p style="font-size: 20px;"><c:out value="${settingsDao.getByKey('websiteDescription').getSetvalue()}"/></p>
				</div>
			</div>
		</div>
		
		<div class="container-fluid text-center pt-4 pb-4" style="background-color: #FFECDC;">
			<h3>Click a category to see the menu.</h3>
			
			<div class="card-deck mt-4">
				<jsp:useBean id="foodDao" class="dao.FoodDAO"/>
				<c:set var="foodEachCat" value="${foodDao.getOneFoodEachCategory()}"/>
				
				<c:forEach items="${foodEachCat}" var="food">
					<a class="col-xs-12 col-sm-12 col-md-4 col-lg-3 card text-dark" href="Menu.jsp?categoryId=${food.getCategory().getId()}" style="background-color: #F8C172; text-decoration: none;" data-aos="fade-up">
						<span class="card-body">
							<img src="GetFoodImage?id=${food.getId()}" class="border border-secondary" width="85%" height="125" alt="${food.getCategory()}"/><br/>
							<c:out value="${food.getCategory().getName()}"/>
						</span>
					</a>
				</c:forEach>
			</div>
		</div>
		
		<div class="container-fluid pt-4 pb-4" style="background-color: white;">
			<h1 class="text-center">About Us</h1>
			
			<div class="card-deck mt-4">
				
				<div class="card" style="background-color: #C1BAEA;" data-aos="fade-up">
					<div class="card-body text-center text-white">
						<i class="fas fa-drumstick-bite fa-3x"></i>
						<h2 class="text-center mt-2">Tasty</h2>
						<p>We provide only the best food. No doubt about that.</p>
					</div>
				</div>
				
				<div class="card" style="background-color: #EDE577" data-aos="fade-up">
					<div class="card-body text-center text-white">
						<i class="fas fa-truck fa-3x"></i>
						<h2 class="text-center mt-2">Fast Delivery</h2>
						<p>Using only the fastest FOTON trucks available, any order is expected to be delivered within 5-10 minutes.</p>
					</div>
				</div>
				
				<div class="card" style="background-color: #73C9CF;" data-aos="fade-up">
					<div class="card-body text-center text-white">
						<i class="fas fa-user fa-3x"></i>
						<h2 class="text-center mt-2">User Friendly</h2>
						<p>No need to learn about computers that much. Ordering in our website is just one click away.</p>
					</div>
				</div>
				
				<div class="card bg-success" data-aos="fade-up">
					<div class="card-body text-center text-white">
						<i class="fas fa-user-tie fa-3x"></i>
						<h2 class="text-center mt-2">Efficient</h2>
						<p>We only hire the most experienced employees. Without a doubt, your orders will be processed faster than you think.</p>
					</div>
				</div>
				
			</div>
		</div>
		
		<jsp:include page="assets/include/Footer.jsp"></jsp:include>
		
	</body>
</html>