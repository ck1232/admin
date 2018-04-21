<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createModuleForm" method="get" action="<c:url value="/admin/createModule" />"></form>
<form id="updateModuleForm" method="post" action="<c:url value="/admin/updateModule" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="updatePermissionKeyForm" method="post" action="<c:url value="/admin/updatePermissionType" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="updatePermissionForm" method="post" action="<c:url value= "/admin/updateSubmodulePermission" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction='<c:url value="/admin/deleteModule" />'><i class="fa fa-user-times"></i> Delete Module</button>
		<button class="btn btn-primary pull-right" type="submit" form="createModuleForm"><i class="fa fa-user-plus"></i> Add Module</button>
	</div>
</div>