<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createSalaryBonusForm" method="get" action="<c:url value="/salarybonus/createSalaryBonus" />"></form>
<form id="viewSalaryBonusForm" method="post" action="<c:url value="/salarybonus/viewSalaryBonus" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="updateSalaryBonusForm" method="post" action="<c:url value="/salarybonus/updateSalaryBonus" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="paySalaryBonusForm" method="post" action="<c:url value="/salarybonus/paySalaryBonus" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/salarybonus/deleteSalaryBonus" />"><i class="fa fa-user-times"></i> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createSalaryBonusForm"><i class="fa fa-user-plus"></i> Add</button>
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/payment/createPaySalaryBonus" />"><i class="fa fa-user-plus"></i> Add Payment</button>
	</div>
</div>