<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

	<div class="box">
		<!--BOX HEADER-->
	    <div class="box-header with-border">
	    	<h3 class="box-title">Storage Location Information</h3>
	    </div>
	    <!--FORM-->
	    
		<div class="box-body">
			<form id="backToListButton" method="get" action="<c:url value="/storagelocation/listStorageLocation" />"></form>
			<c:url var="post_url" value="/storagelocation/updateStorageLocationToDb" />
			<form:form id="updateStorageLocationToDbForm" method="post" modelAttribute="storageLocationForm" action="${post_url }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<form:input path="locationid" type="hidden" id="id"/>
				<div class="row">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Location Name</label>
						<div class="col-sm-10">
							<form:input path="locationname" type="text" class="form-control"
		                    				id="locationname" placeholder="Enter location name" />
							<form:errors path="locationname" class="text-danger" />
						</div>
		 			</div>
	 			</div>
	 			<div class="row">
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Address</label>
						<div class="col-sm-10">
							<form:input path="address" type="text" class="form-control"
						                    id="address" placeholder="Enter address" />
							<form:errors path="address" class="text-danger" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Postal Code</label>
						<div class="col-sm-10">
							<form:input path="postalcode" type="number" class="form-control"
						                    id="postalcode" placeholder="Enter postal code" />
							<form:errors path="postalcode" class="text-danger" />
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
							<button type="submit" class="btn btn-primary" form="updateStorageLocationToDbForm">Update</button>
					        <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
					    </div>
					</div>
				</div>
			</div>
	        <!-- /.box-body -->
		</div>
		<!-- /.BOX -->