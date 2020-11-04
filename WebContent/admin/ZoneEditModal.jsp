<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
					
<jsp:useBean id="zoneDao" class="dao.ZoneDAO"/>
<c:set var="zone" value="${zoneDao.getByID(Long.parseLong(param.id))}"/>
			
<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${zone.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Currency:</label>
		<select name="currencyId" class="form-control" required>
			<jsp:useBean id="currencyDao" class="dao.CurrencyDAO"/>
			<c:set var="currencyList" value="${currencyDao.getAllRecords()}"/>
			<c:forEach items="${currencyList}" var="currency">
				<option value="${currency.getId()}" ${zone.getCurrency().getId() eq currency.getId() ? 'selected' : ''}><c:out value="${currency.getCode()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${zone.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control"><c:out value="${zone.getDescription()}"/></textarea>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>