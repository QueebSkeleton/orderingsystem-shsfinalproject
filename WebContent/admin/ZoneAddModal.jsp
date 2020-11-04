<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addZoneModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Zone</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddZone.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Currency:</label>
						<select name="currencyId" class="form-control" required>
							<jsp:useBean id="currencyDao" class="dao.CurrencyDAO"/>
							<c:set var="currencyList" value="${currencyDao.getAllRecords()}"/>
							<c:forEach items="${currencyList}" var="currency">
								<option value="${currency.getId()}"><c:out value="${currency.getCode()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Name:</label>
						<input type="text" name="name" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Description:</label>
						<textarea name="description" class="form-control"></textarea>
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