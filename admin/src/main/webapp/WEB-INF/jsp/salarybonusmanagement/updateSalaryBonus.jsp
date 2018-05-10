<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Salary / Bonus Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/salarybonus/listSalaryBonus" />"></form>
                    <c:url var="post_url" value="/salarybonus/updateSalaryBonusToDb" />
                    <form:form id="updateSalaryBonusToDbForm" method="post" modelAttribute="salaryBonusForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input type="hidden" path="id" id="id"/>
                    <form:input type="hidden" path="employeeVO.employeeId" id="employeeId"/>
                    <form:input type="hidden" path="employeeVO.name" id="name"/>
                    <form:input type="hidden" path="type" id="type"/>
		              <div class="box-body">
		              <div class="row">
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Employee Name</label>
							<div class="col-sm-5">${salaryBonusForm.employeeVO.name}</div>
						  </div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Date</label>
							<div class="col-sm-5">
								<form:input path="dateString" type="text" class="form-control" 
		                  			  		id="dateString" placeholder="Press to select date"/>
		                  		<form:errors path="dateString" class="text-danger" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Type</label>
							<div class="col-sm-5">${salaryBonusForm.type}</div>
						</div>
					</div>
					<c:if test="${salaryBonusForm.type == 'Salary'}">
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Basic Salary</label>
								<div class="col-sm-5">
									<form:input path="basicSalaryAmt" type="text" class="form-control" 
				                  				id="basicSalaryAmt" placeholder="Enter basic salary"/>
				                  	<form:errors path="basicSalaryAmt" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Overtime Amount</label>
								<div class="col-sm-5">
									<form:input path="overTimeAmt" type="text" class="form-control" 
				                  				id="overTimeAmt" placeholder="Enter overtime amount"/>
				                  	<form:errors path="overTimeAmt" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Overtime Hours</label>
								<div class="col-sm-5">
									<form:input path="overTimeHours" type="text" class="form-control" 
				                  				id="overTimeHours" placeholder="Enter overtime hours"/>
				                  	<form:errors path="overTimeHours" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Overtime Remarks</label>
								<div class="col-sm-5">
									<form:input path="overTimeRemarks" type="text" class="form-control" 
				                  				id="overTimeRemarks" placeholder="Enter overtime remarks"/>
				                  	<form:errors path="overTimeRemarks" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Allowance</label>
								<div class="col-sm-5">
									<form:input path="allowance" type="text" class="form-control"
					                            id="allowance" placeholder="Enter allowance" />
									<form:errors path="allowance" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Medical</label>
								<div class="col-sm-5">
									<form:input path="medical" type="text" class="form-control"
					                            id="medical" placeholder="Enter allowance" />
									<form:errors path="medical" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Leave Balance</label>
								<div class="col-sm-5">
									<form:input path="leaveBalance" type="text" class="form-control"
					                            id="leaveBalance" placeholder="Enter leave balance" />
									<form:errors path="leaveBalance" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Leave Taken</label>
								<div class="col-sm-5">
									<form:input path="leaveTaken" type="text" class="form-control"
					                            id="leaveTaken" placeholder="Enter leave taken" />
									<form:errors path="leaveTaken" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Unpaid Leave Amount</label>
								<div class="col-sm-5">
									<form:input path="unpaidLeaveAmt" type="text" class="form-control"
					                                id="unpaidLeaveAmt" placeholder="Enter unpaid leave amount" />
									<form:errors path="unpaidLeaveAmt" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Unpaid Leave Remarks</label>
								<div class="col-sm-5">
									<form:input path="unpaidLeaveRemarks" type="text" class="form-control" 
			                  			  		id="unpaidLeaveRemarks" placeholder="Enter unpaid leave remarks"/>
			                  		<form:errors path="unpaidLeaveRemarks" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">CDAC Amount</label>
								<div class="col-sm-5">
									<form:input path="cdacAmt" type="text" class="form-control" 
			                  			  		id="cdacAmt" placeholder="Enter cdac amount"/>
			                  		<form:errors path="cdacAmt" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">SDL Amount</label>
								<div class="col-sm-5">
									<form:input path="sdlAmt" type="text" class="form-control" 
			                  			  		id="sdlAmt" placeholder="Enter sdl amount"/>
			                  		<form:errors path="sdlAmt" class="text-danger" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Foreign Worker Levy</label>
								<div class="col-sm-5">
									<form:input path="fwLevy" type="text" class="form-control" 
			                  			  		id="fwLevy" placeholder="Enter sdl amount"/>
			                  		<form:errors path="fwLevy" class="text-danger" />
								</div>
							</div>
						</div>	
					</c:if> 
					<c:if test="${salaryBonusForm.type == 'Bonus'}"> 
			            <div class="row">
				            <div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-sm-2 control-label">Bonus Amount</label>
									<div class="col-sm-5">
				                  		<form:input path="bonusAmt" type="text" class="form-control" 
				                  			  id="bonusAmt" placeholder="Enter bonus amount"/>
				                  		<form:errors path="bonusAmt" class="text-danger" />
				                	</div>
				              </div>
			            </div>
			        </c:if>
			        <div class="row">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Employee CPF</label>
								<div class="col-sm-5">
			                  		<form:input path="employeeCpf" type="text" class="form-control" 
			                  			  id="employeeCpf" placeholder="Enter employee cpf"/>
			                  		<form:errors path="employeeCpf" class="text-danger" />
			                	</div>
			              </div>
		            </div>
		            <div class="row">
			            <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Employer CPF</label>
								<div class="col-sm-5">
			                  		<form:input path="employerCpf" type="text" class="form-control" 
			                  			  id="employerCpf" placeholder="Enter employer cpf"/>
			                  		<form:errors path="employerCpf" class="text-danger" />
			                	</div>
			              </div>
		            </div>
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
			                  <button id="updateSalaryBonusBtn" type="submit" class="btn btn-primary" form="updateSalaryBonusToDbForm">Update
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
    		
    <script>
      $( function() {
    	  $('#dateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });
		   
	 } );

      

    </script>