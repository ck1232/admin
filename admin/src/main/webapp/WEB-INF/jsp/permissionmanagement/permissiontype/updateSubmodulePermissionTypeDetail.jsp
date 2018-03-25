<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Permission Type Information</h3>
                    </div>
                    <!--FORM-->
                    
		              <div class="box-body">
		              <form id="backToListButton" method="get" action="<c:url value="/admin/updatePermissionType/${submodulepermissiontypeForm.submoduleId}" />"></form>
		              <c:url var = "post_url" value="/admin/updatePermissionTypeDetailToDb" />
		              <form:form id="updatePermissionTypeDetailToDbForm" method="post" modelAttribute="submodulepermissiontypeForm" action="${post_url }">
		              		<form:input path="typeId" type="hidden" id="typeId"/>
		              		<form:input path="submoduleId" type="hidden" id="submoduleId"/>
		              		<form:input path="seqNum" type="hidden" id="seqNum"/>
		              		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              		
		              		<div class="row">
							 	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Permission Type</label>
									<div class="col-sm-10">
										<form:input path="permissionType" type="text" class="form-control"
					                                id="permissionType" placeholder="Enter permission type" />
										<form:errors path="permissionType" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Url</label>
									<div class="col-sm-10">
										<form:input path="url" type="text" class="form-control"
				                                id="url" placeholder="Enter url" />
										<form:errors path="url" class="text-danger" />
									</div>
						  		</div>
						  	</div>
						<br/>
						<br/>
						  </form:form>
						  <!--/.FORM-->
						  
						  <div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
								<button type="submit" class="btn btn-primary" form="updatePermissionTypeDetailToDbForm">Update
			                  </button>
			                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel
			            </button>
			                </div>
						  </div>
		              </div>
		              <!-- /.box-body -->
		            
                </div>
    		