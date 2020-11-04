<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="modal fade" id="cartModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">My Cart</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<div class="modal-body">
				<h3 class="text-center">Cart Contents</h3>
			
				<c:forEach items="${cart}" var="itemOrder">
					<b style="font-size: 25px;">- <c:out value="${itemOrder.getFood().getName()}"/></b> <br/>
					<b>Price Each:</b> <c:out value="${empty countrySet ? '$' : countrySet.getZone().getCurrency().getCode()}"/><c:out value="${empty countrySet ? itemOrder.getFood().getUnitPrice() : countrySet.getZone().getCurrency().getExchangeRate() * itemOrder.getFood().getUnitPrice()}"/> <br/>
					<b>Quantity Placed:</b> <c:out value="${itemOrder.getQuantity()}"/> <br/>
					<b>Total for this Item:</b> <c:out value="${empty countrySet ? '$' : countrySet.getZone().getCurrency().getCode()}"/><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${empty countrySet ? itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() : itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() * countrySet.getZone().getCurrency().getExchangeRate()}"/> <br/>
					<c:set var="grandTotal" value="${empty countrySet ? grandTotal + itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() : grandTotal + itemOrder.getFood().getUnitPrice() * itemOrder.getQuantity() * countrySet.getZone().getCurrency().getExchangeRate()}"/>
				</c:forEach>
				
				<hr/>
				
				<jsp:useBean id="settingsDao" class="dao.SettingsDAO"/>
				
				<c:choose>
					<c:when test="${not empty countrySet}">
						<c:set var="grandTotal" value="${grandTotal + countrySet.getZone().getCurrency().getExchangeRate() * Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}"/>
					</c:when>
					<c:otherwise>
						<c:set var="grandTotal" value="${grandTotal + Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}"/>
					</c:otherwise>
				</c:choose>
				
				
				<h5>Delivery Fee: ${empty countrySet ? Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue()) : countrySet.getZone().getCurrency().getExchangeRate() * Double.parseDouble(settingsDao.getByKey('deliveryFee').getSetvalue())}</h5>	
				<h3>Grand Total: <c:out value="${empty countrySet ? '$' : countrySet.getZone().getCurrency().getCode()}"/><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${not empty grandTotal ? grandTotal : 0}"/></h3>
				<c:if test="${not empty cart}">
					<a class="btn btn-primary" href="Checkout.jsp">Proceed to Checkout</a>
				</c:if>
			</div>
			
		</div>
	</div>
</div>