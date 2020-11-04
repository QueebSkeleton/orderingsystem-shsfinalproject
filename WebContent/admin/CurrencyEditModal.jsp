<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="currencyDao" class="dao.CurrencyDAO"/>
<c:set var="currency" value="${currencyDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${currency.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Code::</label>
		<input type="text" name="code" class="form-control" value="${currency.getCode()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control"><c:out value="${currency.getDescription()}"/></textarea>
	</div>
	<div class="form-group">
		<label class="control-label">Exchange Rate (1 USD = ?):</label>
		<input type="number" step=".0001" name="exchangeRate" class="form-control" value="${currency.getExchangeRate()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>