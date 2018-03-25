<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">User Information</h3>
                    </div>
                    <!--FORM-->
                    
		              <div class="box-body">
		              <form id="backToListButton" method="get" action="<c:url value="/admin/listUser" />"></form>
		              <c:url var = "post_url" value="/admin/updateUserToDb" />
		              <form:form id="updateUserToDbForm" method="post" modelAttribute="userForm" action="${post_url }">
		              		<form:input path="id" type="hidden" id="id"/>
		              		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">User id</label>
							<div class="col-sm-10">
								<form:input path="userid" type="text" class="form-control"
				                                id="userid" placeholder="Enter userid" />
								<form:errors path="userid" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<form:password path="password" class="form-control"
				                                id="password" placeholder="Enter password" />
								<form:errors path="password" class="control-label" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<form:input path="name" type="text" class="form-control"
				                                id="name" placeholder="Enter name" />
								<form:errors path="name" class="control-label" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Email Address</label>
							<div class="col-sm-10">
								<form:input path="emailaddress" class="form-control"
				                                id="emailaddress" placeholder="Enter email address" />
								<form:errors path="emailaddress" class="control-label" />
							</div>						  
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Enabled</label>
							<div class="col-sm-10">
								<div class="checkbox">
								  <label>
				                       <form:checkbox path="enabled" id="enabled" />
								  </label>
								     <form:errors path="enabled" class="control-label" />
								</div>
							</div>
						  </div>
						  </div>
						<br/>
						
						  </form:form>
						  <!--/.FORM-->
						  
						  <div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
								<button type="submit" class="btn btn-primary" form="updateUserToDbForm">Update
			                  </button>
			                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel
			            </button>
			                </div>
						  </div>
		              </div>
		              <!-- /.box-body -->
		            
                </div>
    		