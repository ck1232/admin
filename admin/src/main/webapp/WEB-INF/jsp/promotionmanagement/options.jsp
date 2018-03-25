<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createPromotionForm" method="get" action="<c:url value="/product/promotion/createPromotion" />"></form>
<form id="updatePromotionForm" method="post" action="<c:url value="/product/promotion/updatePromotion" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="viewPromotionForm" method="post" action="<c:url value="/product/promotion/viewPromotion" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form id="manageDiscountForm" method="post" action="<c:url value="/product/discount/manageDiscount" />">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/product/promotion/deletePromotion" />"><i class="fa fa-user-times"></i> Delete</button>
		<button class="btn btn-primary pull-right" type="submit" form="createPromotionForm"><i class="fa fa-user-plus"></i> Add</button>
	</div>
</div>