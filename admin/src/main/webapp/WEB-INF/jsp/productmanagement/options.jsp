<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="createProductForm" method="get" action="<c:url value="/product/product/createProduct" />" accept-charset="UTF-8"></form>
<form id="editProductForm" method="post" action="<c:url value="/product/product/editProduct" />" accept-charset="UTF-8">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="submit" form="datatableForm" formaction="<c:url value="/product/product/deleteProduct" />"><i class="fa fa-user-times"></i> Delete Product</button>
		<button class="btn btn-primary pull-right" type="submit" form="createProductForm"><i class="fa fa-user-plus"></i> Add Product</button>
	</div>
</div>