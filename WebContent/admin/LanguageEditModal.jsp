<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="languageDao" class="dao.LanguageDAO"/>
<c:set var="language" value="${languageDao.getByID(Long.parseLong(param.id))}"/>

<div class="modal-body">
	<div class="form-group">
		<label class="control-label">ID:</label>
		<input type="number" name="id" class="form-control" value="${language.getId()}" readonly required/>
	</div>
	<div class="form-group">
		<label class="control-label">Code:</label>
		<input type="text" name="code" class="form-control" value="${language.getCode()}" required/>
	</div>
	<div class="form-group">
		<label class="control-label">Name:</label>
		<input type="text" name="name" class="form-control" value="${language.getName()}" required/>
	</div>
</div>
<div class="modal-footer">
	<button type="submit" class="btn btn-primary">Edit</button>
</div>