<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
	<div class="card">
		<div class="card-header text-white" style="background-color: #36679E;">
			<i class="fas fa-user-tie"></i>
			My Profile
		</div>
		<div class="card-body text-center">
			<img src="../GetEmployeeImage?id=${employee.getId()}" class="rounded-circle" width="100" height="100"/>
			<h4 class="mt-2"><c:out value="${employee.getFirstName()} ${employee.getLastName()}"/></h4>
			<h6>Employee</h6>
			
			<a class="btn btn-block btn-primary" href="EditProfile.jsp">Edit Profile</a>
		</div>
	</div>
</div>