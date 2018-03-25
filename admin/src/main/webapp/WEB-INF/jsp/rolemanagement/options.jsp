<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createRoleForm" method="get" action="<c:url value="/admin/createRole" />"></form>
<form id="updateRoleForm" method="post" action="<c:url value="/admin/updateRole" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/admin/deleteRole" />"><i class="fa fa-user-times"></i> Delete Role</button>
		<button class="btn btn-primary pull-right" type="submit" form="createRoleForm"><i class="fa fa-user-plus"></i> Add Role</button>
	</div>
</div>