<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">

</script>


<form action="<c:url value="/logs" />" method="post" id="logsForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	<div class="col-md-12" style="overflow-x:auto;overflow-y:auto;">
		${content}
	</div>
	
 </form>