<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createStorageLocationForm" method="get" action="<c:url value="/storagelocation/createStorageLocation" />"></form>
<form id="updateStorageLocationForm" method="post" action="<c:url value="/storagelocation/updateStorageLocation" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewStorageLocationForm" method="post" action="<c:url value="/storagelocation/viewStorageLocation" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/storagelocation/deleteStorageLocation" />"><i class="fa fa-user-times"></i> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createStorageLocationForm"><i class="fa fa-user-plus"></i> Add</button>
	</div>
</div>