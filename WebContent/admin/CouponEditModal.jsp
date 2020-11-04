<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="couponDao" class="dao.CouponDAO"/>
<c:set var="coupon" value="${couponDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${coupon.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Valid Until:</label>
		<input type="datetime-local" name="validUntil" class="form-control" value="${coupon.getValidUntil().toString().replace(' ', 'T')}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Code:</label>
		<input type="text" name="code" class="form-control" value="${coupon.getCode()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Discount Amount:</label>
		<input type="number" step=".01" name="discountAmount" class="form-control" value="${coupon.getDiscountAmount()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>