<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addFoodModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Food</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddFood.do" enctype="multipart/form-data" autocomplete="off">
				<div class="modal-body">
					<input type="hidden" name="categoryRedirect" value="${param.categoryId}"/>
					<div class="form-group">
						<label class="control-label">Name:</label>
						<input type="text" name="name" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Supplier:</label>
						<jsp:useBean id="supplierDao" class="dao.SupplierDAO"/>
						<c:set scope="request" var="supplierList" value="${supplierDao.getAllRecords()}"/>
						<select name="supplierId" class="form-control">
							<option value="0">None</option>
							<c:forEach items="${supplierList}" var="supplier">
								<option value="${supplier.getId()}"><c:out value="${supplier.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Image:</label>
						<input type="file" name="image" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Category:</label>
						<select name="categoryId" class="form-control" required>
							<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
							<c:set var="categoryList" value="${categoryDao.getAllRecords()}"/>
							<c:forEach items="${categoryList}" var="category">
								<option value="${category.getId()}" ${param.categoryId eq category.getId() ? 'selected' : ''}><c:out value="${category.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Description:</label>
						<textarea name="description" class="form-control"></textarea>
					</div>
					<div class="form-group">
						<label class="control-label">Initial Units in Stock:</label>
						<input type="number" name="unitsInStock" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Unit Price:</label>
						<input type="number" step=".01" name="unitPrice" class="form-control" required/>
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