<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="sidebar navbar-nav">
	<li class="nav-item">
		<a class="nav-link" href="Dashboard.jsp"> 
			<i class="fas fa-fw fa-tachometer-alt"></i> 
			<span>Dashboard</span>
		</a>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="salesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-chart-line"></i>
			<span>Sales</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="salesDropdown">
			<a class="dropdown-item" href="ProductSalesPanel.jsp">
				<i class="fas fa-chart-line"></i>
				Charts
			</a>
			<a class="dropdown-item" href="ItemSalesPanel.jsp">
				<i class="fas fa-box"></i>
				Item Sales Panel
			</a>
		</div>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="ordersDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-shopping-cart"></i>
			<span>Orders</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="ordersDropdown">
			<a class="dropdown-item" href="OrdersPanel.jsp">
				All Orders
			</a>
			<a class="dropdown-item" href="OrdersPanel.jsp?status=NEW">
				New Orders
			</a>
			<a class="dropdown-item" href="OrdersPanel.jsp?status=PROCESSING">
				Processing Orders
			</a>
			<a class="dropdown-item" href="OrdersPanel.jsp?status=DELIVERED">
				Delivered Orders
			</a>
			<a class="dropdown-item" href="OrdersPanel.jsp?status=DENIED">
				Denied Orders
			</a>
			<a class="dropdown-item" href="OrdersPanel.jsp?status=CANCELLED">
				Cancelled Orders
			</a>
		</div>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="peopleDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-users"></i> 
			<span>People</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="peopleDropdown">
			<a class="dropdown-item" href="ClientsPanel.jsp">
				<i class="fas fa-users"></i>
				Clients
			</a>
			<a class="dropdown-item" href="EmployeesPanel.jsp">
				<i class="fas fa-user-tie"></i>
				Employees
			</a>
			<a class="dropdown-item" href="SuppliersPanel.jsp">
				<i class="fas fa-box"></i>
				Suppliers
			</a>
		</div>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="foodCategoryDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-drumstick-bite"></i>
			<span>Products</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="foodCategoryDropdown">
			<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
			<c:set var="categoryList" value="${categoryDao.getAllRecords()}"/>
			
			<a class="dropdown-item" href="CategoriesPanel.jsp">
				Categories
			</a>
			
			<a class="dropdown-item" href="FoodsPanel.jsp">
				All Items
			</a>
			
			<c:forEach items="${categoryList}" var="category">
				<a class="dropdown-item" href="FoodsPanel.jsp?categoryId=${category.getId()}"><c:out value="${category.getName()}"/></a>
			</c:forEach>
		</div>
	</li>
	<li class="nav-item">
		<a class="nav-link" href="CouponsPanel.jsp">
			<i class="fas fa-tags"></i>
			<span>Coupons</span>
		</a>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="countriesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-flag"></i>
			<span>Countries</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="countriesDropdown">
			<a class="dropdown-item" href="ZonesPanel.jsp">
				Zones
			</a>
			<a class="dropdown-item" href="CountriesPanel.jsp">
				Countries
			</a>
			<a class="dropdown-item" href="CurrenciesPanel.jsp">
				Currencies
			</a>
			<a class="dropdown-item" href="LanguagesPanel.jsp">
				Languages
			</a>
		</div>
	</li>
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			<i class="fas fa-columns"></i> 
			<span>Others</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="ModesOfPaymentPanel.jsp">
				<i class="fas fa-money-bill"></i>
				Modes of Payment
			</a>
			<a class="dropdown-item" href="ModesOfDeliveryPanel.jsp">
				<i class="fas fa-truck"></i>
				Modes of Delivery
			</a>
			<a class="dropdown-item" href="BannersPanel.jsp">
				<i class="fas fa-flag"></i>
				Banners
			</a>
		</div>
	</li>
	<li class="nav-item">
		<a class="nav-link" href="SettingsPanel.jsp">
			<i class="fas fa-cog"></i>
			<span>Settings</span>
		</a>
	</li>
</ul>