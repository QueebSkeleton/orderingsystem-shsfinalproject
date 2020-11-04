<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="modeOfDeliveryDao" class="dao.ModeOfDeliveryDAO"/>
<c:set var="modeOfDelivery" value="${modeOfDeliveryDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${modeOfDelivery.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${modeOfDelivery.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control"><c:out value="${modeOfDelivery.getDescription()}"/></textarea>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>