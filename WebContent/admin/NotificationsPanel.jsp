<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="model.DateHandler" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="notificationDao" class="dao.NotificationDAO"/>
<c:set var="notificationList" value="${notificationDao.getNotificationListByRecipient(admin)}"/>

<c:choose>
	<c:when test="${not empty notificationList}">
		
		<c:forEach items="${notificationList}" var="notif">
			<b><c:out value="${DateHandler.getTimeFromNow(notif.getDateCreated())}"/></b> <br/>
			<c:out value="${notif.getMessage()}"/>
			<hr/>
		</c:forEach>
				
	</c:when>
</c:choose>