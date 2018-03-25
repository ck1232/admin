<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
$(function(){
	var sql = '${sqlStatement}';
	console.log(sql);
	$("#sqlStatement").val(sql);
});
</script>


<form action="<c:url value="/query" />" method="post">

	
<div class="col-md-12">
	<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <b>SQL statement:</b><br>
    <textarea id = "sqlStatement" style="overflow-y:auto; width:100%;" name="sqlStatement" rows=8></textarea>
   </div>
   <div class="col-md-12">
   	<input type="submit" class="col-md-3" value="Execute" />
   	<input type="submit" class="col-md-3" value="Export" formaction="<c:url value="/query/export" />" formmethod="POST"/>
</div>

</form>

<div class="row">
	<p class="col-md-2"><b>SQL result:</b></p>
	<div class="col-md-12" style="overflow-x:auto;">
		<table class="table table-striped">${message}</table>
	</div>
</div>
 