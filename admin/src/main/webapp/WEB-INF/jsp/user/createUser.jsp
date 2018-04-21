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
                    	<h3 class="box-title">Basic Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/admin/listUser" />"></form>
                    <c:url var="post_url" value="/admin/createUser" />
                    <form:form id="createUserForm" method="post" modelAttribute="userForm" action="${post_url }">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		             	<div class="box-body">
		              		<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">User Name</label>
									<div class="col-sm-10">
										<form:input path="userName" type="text" class="form-control"
						                                id="userName" placeholder="Enter username" />
										<form:errors path="userName" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Password</label>
									<div class="col-sm-10">
										<form:password path="password" class="form-control"
					                                id="password" placeholder="Enter password" />
										<form:errors path="password" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Name</label>
									<div class="col-sm-10">
										<form:input path="name" type="text" class="form-control"
					                                id="name" placeholder="Enter name" />
										<form:errors path="name" class="text-danger" />
									</div>
							  	</div>
							</div>
							<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Email Address</label>
									<div class="col-sm-10">
										<form:input path="emailAddress" class="form-control"
					                                id="emailAddress" placeholder="Enter email address" />
										<form:errors path="emailAddress" class="text-danger" />
									</div>	
							  	</div>	
							</div>
							<div class="row">		  
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Enabled</label>
									<div class="col-sm-10">
										<div class="checkbox">
									  		<label><form:checkbox path="enabledBoolean" id="enabled" /></label>
									     	<form:errors path="enabledBoolean" class="text-danger" />
										</div>
									</div>
							  	</div>
							</div>
							<br/>
							<br/>
		            	
		            		<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<button type="submit" class="btn btn-primary" form ="createUserForm">Add</button>
				                  			<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
										</div>
								</div>
							</div>
		              	</div>
		        	</form:form>
		            <!--/.FORM-->
                </div>
                <!-- /.box-body -->
    		</div>
    	</div>
    </section>