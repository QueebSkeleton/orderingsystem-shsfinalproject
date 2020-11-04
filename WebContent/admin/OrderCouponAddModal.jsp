<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="addOrderCouponModal">
	<div class="modal-dialog">
		<div class="modal-content">
			
			<div class="modal-header">
				<h1 class="modal-title">Add Item Order</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddOrderCoupon.do" autocomplete="off">
				<div class="modal-body">
					<input type="hidden" name="orderId" value="${param.orderId}"/>
					<div class="form-group">
						<jsp:useBean id="couponDao" class="dao.CouponDAO"/>
						<c:set var="couponList" value="${couponDao.getAllRecords()}"/>
						<label class="control-label">Coupon:</label>
						<select name="couponId" class="form-control">
							<c:forEach items="${couponList}" var="coupon">
								<option value="${coupon.getId()}"><c:out value="${coupon.getCode()} | ${coupon.getDiscountAmount()}"/></option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">Add</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
				</div>
			</form>
			
		</div>
	</div>
</div>