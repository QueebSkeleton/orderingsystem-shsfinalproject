<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="alert alert-${param.alertType} alert-dismissable mt-2" role="alert">
	<c:out value="${param.alertMsg}"/>
	<button type="button" class="close" data-dismiss="alert">&times;</button>
</div>