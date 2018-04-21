<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="updatePermissionForm" method="post" action="<c:url value="/admin/updateSubmodulePermission"/>">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
	</div>
</div>