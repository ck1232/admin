<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="viewChequeForm" method="post" action="<c:url value="/cheque/viewCheque" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="updateChequeForm" method="post" action="<c:url value="/cheque/updateCheque" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="bounceChequeForm" method="post" action="<c:url value="/payment/createPayBounceCheque" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="margin">
	<div class="btn-grp">
		<!--  <button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/cheque/deleteCheque" />"><i class="fa fa-user-times"></i> Delete</button>-->
	</div>
</div>