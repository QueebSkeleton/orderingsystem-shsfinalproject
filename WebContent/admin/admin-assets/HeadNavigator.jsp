<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand navbar-dark bg-dark static-top">

	<a class="navbar-brand mr-1" href="Dashboard.jsp">
		<img src="../GetLogo.do" height="30" width="30"/>
		Administrator
	</a>
	
	<button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle">
		<i class="fas fa-bars"></i>
	</button>
	
	<ul class="navbar-nav ml-auto">
		<li class="nav-item dropdown no-arrow mx-1">
			<a class="nav-link" href="Dashboard.jsp#orderNotifs">
				<i class="fas fa-bell fa-fw">
					<jsp:useBean id="notificationDao" class="dao.NotificationDAO"/>
					<c:set var="notificationCount" value="${notificationDao.getUnreadNotificationCount()}"/>
            		<span class="badge badge-left badge-danger" id="notifCountBadge"><c:out value="${notificationCount}"/></span>
				</i>
			</a>
		</li>
		<li class="nav-item dropdown no-arrow">
			<a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> 
				<i class="fas fa-user-circle fa-fw">
				</i>
			</a>
			<div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
				<a class="dropdown-item" href="SettingsPanel.jsp">Settings</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="../Logout.do">Logout</a>
			</div>
		</li>
	</ul>
	
	<script>
		function pollNotifCount() {
			setTimeout(function() {
				$.ajax({
					url: 'GetNotificationUnreadCount.do',
					type: 'GET',
					success: function(data) {
						$('#notifCountBadge').html(data);
						pollNotifCount();
					}
				});
			}, 2500);
		}
		
		pollNotifCount();
	</script>
	
</nav>