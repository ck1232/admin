<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Module Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/admin/listModule" />"></form>
                    <c:url var = "post_url" value="/admin/createModule" />
                    <form:form id="createModuleForm" method="post" modelAttribute="moduleForm" action="${post_url}">
		              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              <div class="box-body">
			              	<div class="row">
							  <div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Module Name</label>
								<div class="col-sm-10">
									<form:input path="moduleName" type="text" class="form-control"
					                                id="moduleName" placeholder="Enter Module Name" />
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
							<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<button type="submit" class="btn btn-primary" form ="createModuleForm">Add</button>
					                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
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