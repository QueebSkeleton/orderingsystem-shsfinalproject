<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Login Modal -->
<div class="modal fade" id="loginModal">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<h1 class="modal-title">Login</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>

			<form class="form" method="POST" action="Login.do"
				autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Username</label>
						<input type="text" name="username" id="loginUsername" class="form-control" required />
					</div>
					<div class="form-group">
						<label class="control-label">Password</label>
						<input type="password" name="password" id="loginPassword" class="form-control" required />
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Login</button>
				</div>
			</form>
			
		</div>
	</div>
</div>

<script>
	$('#loginModal').on('hidden.bs.modal', function() {
		$('#loginUsername').val('');
		$('#loginPassword').val('');
	});
</script>
<!-- Login Modal End -->

<!-- Sign up Modal -->
<div class="modal fade" id="signUpModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Sign Up</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="SignUp.do" enctype="multipart/form-data" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Image:</label>
						<input type="file" name="image" class="form-control"/>
					</div>
					<div class="form-group">
						<label class="control-label">First Name:</label>
						<input type="text" name="firstName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Middle Name:</label>
						<input type="text" name="middleName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Last Name:</label>
						<input type="text" name="lastName" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Address:</label>
						<input type="text" name="address" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Contact Number:</label>
						<input type="number" name="contactNumber" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Email Address:</label>
						<input type="email" name="emailAddress" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Username:</label>
						<input type="text" name="username" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Password:</label>
						<input type="password" name="password" class="form-control" required/>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Sign Up</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
		</div>
	</div>
</div>
<!-- SignUp Modal End -->

<!-- Cart Modal -->
<jsp:include page="CartModal.jsp"></jsp:include>
<!-- Cart Modal End -->

<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #7C281C;">
	<a class="navbar-brand text-white" href="index.jsp">
		<img src="GetLogo.do" width="30" height="30"/>
		<c:out value="${settingsDao.getByKey('headerName').getSetvalue()}"/>
	</a>
	
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#headnav">
        <span class="navbar-toggler-icon"></span>
    </button>
	
	<div class="navbar-collapse collapse" id="headnav">
		<ul class="navbar-nav ml-auto">
			<li class="nav-item dropdown">
				<a href="#" class="nav-link dropdown-toggle" id="countryDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Country -  ${empty countrySet ? 'USA/Default' : countrySet.getCode()}
				</a>
				<div class="dropdown-menu" aria-labelledby="countryDropdown">
					<jsp:useBean id="countryDao" class="dao.CountryDAO"/>
					<c:set var="countryList" value="${countryDao.getAllRecords()}"/>
					<a class="dropdown-item" href="SetCountry.do?id=0">USA - United States/Default</a>
					<c:forEach items="${countryList}" var="country">
						<a class="dropdown-item" href="SetCountry.do?id=${country.getId()}"><c:out value="${country.getCode()} - ${country.getName()}"/></a>
					</c:forEach>
				</div>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#" data-toggle="modal" data-target="#cartModal">
					<i class="fas fa-shopping-cart"></i>
					My Cart
				</a>
			</li>
			<c:choose>
				<c:when test="${not empty client}">
					<li class="nav-item dropdown">
						<a href="#" class="nav-link dropdown-toggle" id="profileDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			                Profile
			            </a>
	                    <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
	                    	<li>
	                    		<div style="width: 300px;">
	                    			<div class="row p-2 pl-4 pr-4">
	                    				<div class="col-lg-4">
	                    					<c:choose>
		                    					<c:when test="${empty client.getImage()}">
		                    						<i class="fas fa-user fa-4x"></i>
		                    					</c:when>
		                    					<c:otherwise>
		                    						<img src="GetClientImage?id=${client.getId()}" width="100%" style="border: 1px solid black;"/>
		                    					</c:otherwise>
	                    					</c:choose>
	                    				</div>
	                    				<div class="col-lg-8">
	                    					<h5>Hello, ${client.getFirstName()}!</h5>
	                    					<a class="btn btn-block btn-primary" href="ProfileEditForm.jsp">Edit my Profile</a>
	                    				</div>
	                    			</div>
	                    		</div>
	                    	</li>
	                    	<li class="dropdown-divider"></li>
	                    	<li>
	                    		<a class="dropdown-item" href="PendingOrders.jsp">
		                    		<i class="fas fa-truck"></i>
		                    		Pending Orders
	                    		</a>
	                    	</li>
	                    	<li>
		                        <a class="dropdown-item" href="Logout.do">
		                        	<i class="fas fa-sign-out-alt"></i>
		                        	Logout
		                        </a>
	                        </li>
	                    </ul>
					</li>
				</c:when>
				<c:otherwise>
					<li class="nav-item"><a class="nav-link" href="#" data-toggle="modal" data-target="#loginModal">Log In</a></li>
					<li class="nav-item"><a class="nav-link" href="#" data-toggle="modal" data-target="#signUpModal">Sign Up</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</nav>