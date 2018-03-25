<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <c:if test="${not empty msg}">
	<div class="alert alert-${css} alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert"
	                              aria-label="Close">
			<span aria-hidden="true">×</span>
		</button>
		<c:if test="${css==\"success\"}">
			<i class="icon fa fa-check"></i>
		</c:if>
		<c:if test="${css==\"warning\"}">
			<i class="icon fa fa-warning"></i>
		</c:if>
		<c:if test="${css==\"info\"}">
			<i class="icon fa fa-check"></i>
		</c:if>
		<c:if test="${css==\"danger\"}">
			<i class="icon fa fa-ban"></i>
		</c:if>
		<strong>${msg}</strong>
	</div>
</c:if>