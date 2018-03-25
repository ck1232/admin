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
                    	<h3 class="box-title">Employee Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/employee/listEmployee" />"></form>
                    <c:url var="post_url" value="/employee/createEmployee" />
                    <form:form id="createEmployeeForm" method="post" modelAttribute="employeeForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			              <div class="box-body">
				              	<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Name*</label>
										<div class="col-sm-5">
											<form:input path="name" type="text" class="form-control"
						                                id="name" placeholder="Enter employee name" />
											<form:errors path="name" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Employment Type*</label>
										<div class="col-sm-5">
						                    <form:select path="employeeType" class="form-control" id="employeeType">
												<form:option value="" label="--- Select ---"/>
						   						<form:options items="${employmentTypeList}" />
											</form:select>            
											<form:errors path="employeeType" class="text-danger" />
										</div>
								  	</div>
								</div>
					            <div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Date of Birth</label>
											<div class="col-sm-5">
						                  		<form:input path="dobString" type="text" class="form-control" 
						                  			  id="dobString" placeholder="Press to select date"/>
						                  		<form:errors path="dobString" class="text-danger" />
						                	</div>
						              </div>
					            </div>
					            <div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Nationality*</label>
										<div class="col-sm-5">
											<form:input path="nationality" type="text" class="form-control"
						                                id="nationality" placeholder="Enter employee nationality" />
											<form:errors path="nationality" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Basic Salary*</label>
										<div class="col-sm-5">
											<form:input path="basicSalary" type="text" class="form-control"
						                                id="basicSalary" placeholder="Enter employee basic salary" />
											<form:errors path="basicSalary" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Employee Start Date</label>
											<div class="col-sm-5">
						                  		<form:input path="employmentStartDateString" type="text" class="form-control" 
						                  			  id="employmentStartDateString" placeholder="Press to select date"/>
						                  		<form:errors path="employmentStartDateString" class="text-danger" />
						                	</div>
						              </div>
					            </div>
					            <div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Employee End Date</label>
											<div class="col-sm-5">
						                  		<form:input path="employmentEndDateString" type="text" class="form-control" 
						                  			  id="employmentEndDateString" placeholder="Press to select date"/>
						                  		<form:errors path="employmentEndDateString" class="text-danger" />
						                	</div>
						              </div>
					            </div>
								<div class="row">		  
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">CDAC Indicator</label>
										<div class="col-sm-5">
											<div class="checkbox">
										  		<label><form:checkbox path="cdacIndBoolean" id="cdacIndBoolean" /></label>
										     	<form:errors path="cdacIndBoolean" class="text-danger" />
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
										<button id="addEmployeeBtn" type="submit" class="btn btn-primary" form ="createEmployeeForm">Add</button>
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
    
    <script>
      $( function() {
    	  $('#dobString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });

    	  $('#employmentStartDateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });

    	  $('#employmentEndDateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });
		   
	 } );

      

    </script>
    