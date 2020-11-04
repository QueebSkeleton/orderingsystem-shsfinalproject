<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="categoryDao" class="dao.CategoryDAO"/>
<c:set var="category" value="${categoryDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${category.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${category.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control">${category.getDescription()}</textarea>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Update</button>
	<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
</div>