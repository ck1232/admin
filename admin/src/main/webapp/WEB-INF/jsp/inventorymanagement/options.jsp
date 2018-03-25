<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createInventoryProductForm" method="get" action="<c:url value="/inventory/createInventoryProduct" />"></form>
<form id="updateStorageLocationForm" method="post" action="<c:url value="/storagelocation/updateStorageLocation" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewInventoryProductForm" method="post" action="<c:url value="/inventory/viewInventoryProduct" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="createInventoryProductForm"><i class="fa fa-user-plus"></i> Add</button>
	</div>
</div>