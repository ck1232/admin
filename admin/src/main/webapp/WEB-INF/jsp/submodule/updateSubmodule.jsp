<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Submodule Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="post" action="<c:url value="/admin/listSubmodule" />">
                    	<input type="hidden" name="editBtn" value="${submodule.parentId}"/>
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <c:url var = "post_url" value="/admin/updateSubmoduleToDb" />
                    <form:form id="updateSubmoduleToDbForm" method="post" modelAttribute="submodule" action="${post_url }">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              <div class="box-body">
		              		<form:input path="submoduleId" type="hidden" id="submoduleId"/>
		              		<form:input path="parentId" type="hidden" id="parentId"/>
		              		<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Submodule Name</label>
									<div class="col-sm-10">
										<form:input path="name" type="text" class="form-control"
					                                id="name" placeholder="Enter submodule name" />
										<form:errors path="name" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Icon</label>
									<div class="col-sm-10">
										<form:input path="icon" type="text" class="form-control"
					                                id="icon" placeholder="Enter icon" />
										<form:errors path="icon" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
						  		<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Url</label>
									<div class="col-sm-10">
										<form:input path="url" type="text" class="form-control"
				                                id="url" placeholder="Enter submodule name" />
										<form:errors path="url" class="text-danger" />
									</div>
						  		</div>
						  	</div>
							<br/>
							<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
			                  			<button type="submit" class="btn btn-primary" form="updateSubmoduleToDbForm">Update</button>
			                  			<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
						  		</div>
						  	</div>
		              </div>
		              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
    		