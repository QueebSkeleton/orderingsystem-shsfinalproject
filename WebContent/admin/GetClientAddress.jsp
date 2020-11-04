<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="clientDao" class="dao.ClientDAO"/>
<c:set var="client" value="${clientDao.getByID(Long.parseLong(param.id))}"/>

<c:out value="${client.getAddress()}"/>