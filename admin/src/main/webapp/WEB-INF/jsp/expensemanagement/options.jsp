<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createExpenseForm" method="get" action="<c:url value="/expense/createExpense" />"></form>
<form id="updateExpenseForm" method="post" action="<c:url value="/expense/updateExpense" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewExpenseForm" method="post" action="<c:url value="/expense/viewExpense" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="payExpenseForm" method="post" action="<c:url value="/expense/payExpense" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/expense/deleteExpense" />"> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createExpenseForm"><i class="fa fa-user-plus"></i> Add</button>
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/payment/createPayExpense" />"><i class="fa fa-user-plus"></i> Add Payment</button>
	</div>
</div>