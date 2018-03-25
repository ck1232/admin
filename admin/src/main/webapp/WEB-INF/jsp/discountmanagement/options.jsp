<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createDiscountForm" method="get" action="<c:url value="/product/discount/createDiscount" />"></form>
<form id="updateDiscountForm" method="post" action="<c:url value="/product/discount/updateDiscount" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewDiscountForm" method="post" action="<c:url value="/product/discount/viewDiscount" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/product/discount/deleteDiscount" />"><i class="fa fa-user-times"></i> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createDiscountForm"><i class="fa fa-user-plus"></i> Add</button>
	</div>
</div>