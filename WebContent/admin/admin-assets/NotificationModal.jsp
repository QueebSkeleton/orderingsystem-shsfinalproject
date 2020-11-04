<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="${param.modalId}">
	<div class="modal-dialog">
		<div class="modal-content">
			
			<div class="modal-header">
				<h1 class="modal-title"><c:out value="${param.modalTitle}"/></h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<div class="modal-body">
				<p><c:out value="${param.modalMessage}"/></p>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">Dismiss</button>
			</div>
			
		</div>
	</div>
</div>