<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

	<div class="box">
		<!--BOX HEADER-->
	    <div class="box-header with-border">
	    	<h3 class="box-title">Module Information</h3>
	    </div>
	    <!--FORM-->
	    
		<div class="box-body">
			<form id="backToListButton" method="get" action="<c:url value="/admin/listModule" />"></form>
			<c:url var="post_url" value="/admin/updateModuleToDb" />
			<form:form id="updateModuleToDbForm" method="post" modelAttribute="moduleForm" action="${post_url }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<form:input path="moduleId" type="hidden" id="moduleId"/>
	 			<div class="row">
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Module Name</label>
						<div class="col-sm-10">
							<form:input path="moduleName" type="text" class="form-control"
						                    id="moduleName" placeholder="Enter module name" />
							<form:errors path="moduleName" class="text-danger" />
						</div>
					</div>
				</div>
				<div class="row">
				  	<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Icon</label>
						<div class="col-sm-10">
							<form:input path="icon" type="text" class="form-control"
		                                id="icon" placeholder="Enter Icon" />
							<form:errors path="icon" class="text-danger" />
						</div>
				 	</div>
				</div>
	
				<br/>
				<br/>
	
	  		</form:form>
			<!--/.FORM-->
	 			<div class="row">
				 	<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<button type="submit" class="btn btn-primary" form="updateModuleToDbForm">Update</button>
					        <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
					    </div>
					</div>
				</div>
			</div>
	        <!-- /.box-body -->
		</div>
		<!-- /.BOX -->