<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Permission Type Information</h3>
                    </div>
                    <!--FORM-->
                    
                    <form id="backToListButton" method="get" action="<c:url value="/admin/updatePermissionType/${submodule.submoduleId}" />/"></form>
                    <c:url var="post_url" value="/admin/savePermissionTypeToDb" />
                    <form:form id="createPermissionTypeForm" method="post" modelAttribute="submodulepermissiontypeForm" action="${post_url }">
		              <div class="box-body">
		              <form:input path="submoduleId" type="hidden" id="submoduleId"/>
		              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              	<div class="row">
							<label class="col-sm-2">Submodule</label>
							<div class="col-sm-10">${submodule.name}</div>
						 </div>
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
						  <div class="row">
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Sequence No</label>
							<div class="col-sm-10">
								<form:input path="seqNum" type="text" class="form-control"
				                                id="seqNum" placeholder="Enter sequence no" />
								<form:errors path="seqNum" class="text-danger" />
							</div>
						  </div>
						  </div>
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
								<button type="submit" class="btn btn-primary" form ="createPermissionTypeForm">Add
			                  </button>
			                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel
			            </button>
							</div>
						  </div>
		              </div>
		              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
    		</div>
    	</div>
    </section>