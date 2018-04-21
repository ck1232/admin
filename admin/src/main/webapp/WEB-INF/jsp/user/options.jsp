<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createUserForm" method="get" action="<c:url value="/admin/createUser" />"></form>
<form id="updateUserForm" method="post" action="<c:url value="/admin/updateUser" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewUserForm" method="post" action="<c:url value="/admin/viewUser" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/admin/deleteUser" />"><i class="fa fa-user-times"></i> Delete User</button>
		<button class="btn btn-primary pull-right" type="submit" form="createUserForm"><i class="fa fa-user-plus"></i> Add User</button>
	</div>
</div>