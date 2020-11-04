<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="util.OrderStatus" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="orderDao" class="dao.OrderDAO"/>

<c:set var="allStatus"><%= OrderStatus.ALL %></c:set>
<c:set var="orderCount" value="${orderDao.getOrderCount(allStatus)}"/>

New:
<div class="progress">
	<c:set var="newOrdersCount" value="${orderDao.getOrderCount(OrderStatus.NEW)}"/>
	<div class="progress-bar progress-bar-striped progress-bar-animated bg-info" 
		 style="width: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
		 value="${ orderCount != 0 ? newOrdersCount * 100 / orderCount : 0 }"/>%;"></div>
</div>

Processing:
<div class="progress">
	<c:set var="processingOrdersCount" value="${orderDao.getOrderCount(OrderStatus.PROCESSING)}"/>
	<div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" 
		style="width: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
		value="${ orderCount != 0 ? processingOrdersCount * 100 / orderCount : 0 }" />%;"></div>
</div>

Delivered:
<div class="progress">
	<c:set var="deliveredOrdersCount" value="${orderDao.getOrderCount(OrderStatus.DELIVERED)}"/>
	<div class="progress-bar progress-bar-striped progress-bar-animated bg-success" 
		style="width: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
		value="${ orderCount != 0 ? deliveredOrdersCount * 100 / orderCount : 0 }" />%"></div>
</div>

Denied:
<div class="progress">
	<c:set var="deniedStatus"><%= OrderStatus.DENIED %></c:set>
	<c:set var="deniedOrdersCount" value="${orderDao.getOrderCount(OrderStatus.DENIED)}"/>
	<div class="progress-bar progress-bar-striped progress-bar-animated bg-warning" 
		style="width: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
		value="${ orderCount != 0 ? deniedOrdersCount * 100 / orderCount : 0 }" />%"></div>
</div>

Cancelled:
<div class="progress">
	<c:set var="cancelledOrdersCount" value="${orderDao.getOrderCount(OrderStatus.CANCELLED)}"/>
	<div class="progress-bar progress-bar-striped progress-bar-animated bg-danger" 
		style="width: <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" 
		value="${ orderCount != 0 ? cancelledOrdersCount * 100 / orderCount : 0 }" />%"></div>
</div>