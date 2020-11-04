<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
<jsp:useBean id="countryDao" class="dao.CountryDAO"/>
<c:set var="country" value="${countryDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${country.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Zone:</label>
		<select name="zoneId" class="form-control" required>
			<jsp:useBean id="zoneDao" class="dao.ZoneDAO"/>
			<c:set var="zoneList" value="${zoneDao.getAllRecords()}"/>
			<c:forEach items="${zoneList}" var="zone">
				<option value="${zone.getId()}" ${country.getZone().getId() eq zone.getId() ? 'selected' : ''}><c:out value="${zone.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Language:</label>
		<select name="languageId" class="form-control" required>
			<jsp:useBean id="languageDao" class="dao.LanguageDAO"/>
			<c:set var="languageList" value="${languageDao.getAllRecords()}"/>
			<c:forEach items="${languageList}" var="language">
				<option value="${language.getId()}" ${country.getLanguage().getId() eq language.getId() ? 'selected' : ''}><c:out value="${language.getName()}"/></option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${country.getName()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Code:</label>
		<input type="text" name="code" class="form-control" value="${country.getCode()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Description:</label>
		<textarea name="description" class="form-control"><c:out value="${country.getDescription()}"/></textarea>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>