<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createEmployeeForm" method="get" action="<c:url value="/employee/createEmployee" />"></form>
<form id="updateEmployeeForm" method="post" action="<c:url value="/employee/updateEmployee" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewEmployeeForm" method="post" action="<c:url value="/employee/viewEmployee" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/employee/deleteEmployee" />"><i class="fa fa-user-times"></i> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createEmployeeForm"><i class="fa fa-user-plus"></i> Add</button>
	</div>
</div>