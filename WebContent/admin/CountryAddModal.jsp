<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addCountryModal">
	<div class="modal-dialog">
		<div class="modal-content">
		
			<div class="modal-header">
				<h1 class="modal-title">Add Country</h1>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			
			<form class="form" method="POST" action="AddCountry.do" autocomplete="off">
				<div class="modal-body">
					<div class="form-group">
						<label class="control-label">Zone:</label>
						<select name="zoneId" class="form-control" required>
							<jsp:useBean id="zoneDao" class="dao.ZoneDAO"/>
							<c:set var="zoneList" value="${zoneDao.getAllRecords()}"/>
							<c:forEach items="${zoneList}" var="zone">
								<option value="${zone.getId()}"><c:out value="${zone.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Language:</label>
						<select name="languageId" class="form-control" required>
							<jsp:useBean id="languageDao" class="dao.LanguageDAO"/>
							<c:set var="languageList" value="${languageDao.getAllRecords()}"/>
							<c:forEach items="${languageList}" var="language">
								<option value="${language.getId()}"><c:out value="${language.getName()}"/></option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label class="control-label">Name:</label>
						<input type="text" name="name" class="form-control" required/>
					</div>
					<div class="form-group">
						<label class="control-label">Code:</label>
						<input type="text" name="code" class="form-control" maxlength="5" required/>
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