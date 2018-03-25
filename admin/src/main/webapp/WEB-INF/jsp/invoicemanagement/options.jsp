<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<form id="createGrantForm" method="get" action="<c:url value="/invoice/createGrant" />"></form>
<form id="updateGrantForm" method="post" action="<c:url value="/invoice/updateGrant" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewInvoiceForm" method="post" action="<c:url value="/invoice/viewInvoice" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="payInvoiceForm" method="post" action="<c:url value="/invoice/payInvoice" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<security:authorize access="hasAnyAuthority('ROLE_ADMIN')">
			<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/invoice/deleteInvoice" />"> Delete</button>
		</security:authorize>
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/invoice/createBadDebt" />"><i class="fa fa-ban"></i> Bad debt</button>
		<button class="btn btn-primary pull-right" type="submit" form="createGrantForm"><i class="fa fa-user-plus"></i> Add Grant</button>
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/payment/createPayInvoice" />"><i class="fa fa-user-plus"></i> Add Payment</button>
	</div>
</div>