<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">

</script>


<form action="<c:url value="/logs" />" method="post" id="logsForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="col-md-12" style="overflow-x:auto;">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>File Name</th>
						<th>Last Modified</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${files}" var="file">
					<tr>
						<td>${file.fileName}</td>
						<td>${file.lastModifiedString}</td>
						<td>
							<button name="view" class="btn btn-primary" type="submit" form="logsForm" value="${file.hashCode}" formaction="<c:url value="/viewLogs"/>"><i class="fa fa-eye"></i> View</button>
							<button name="download" class="btn btn-primary" type="submit" form="logsForm" value="${file.hashCode}" formaction="<c:url value="/downloadLogs"/>"><i class="fa fa-pencil"></i> Download</button>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<th>File Name</th>
						<th>Last Modified</th>
						<th>Action</th>
					</tr>
				</tfoot>
			</table>
		</div>
 </form>