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
                    	<h3 class="box-title">Salary / Bonus Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/salarybonus/listSalaryBonus" />"></form>
                    <c:url var="post_url" value="/salarybonus/createSalaryBonus" />
                    <form:form id="createSalaryBonusForm" method="post" modelAttribute="salaryBonusForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			              <div class="box-body">
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Employee Name*</label>
										<div class="col-sm-5">
						                    <form:select path="employeeId" class="form-control" id="employeeId">
												<form:option value="" label="--- Select ---"/>
						   						<form:options items="${employeeList}" />
											</form:select>            
											<form:errors path="employeeId" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Date*</label>
											<div class="col-sm-5">
						                  		<form:input path="dateString" type="text" class="form-control" 
						                  			  id="dateString" placeholder="Press to select date"/>
						                  		<form:errors path="dateString" class="text-danger" />
						                	</div>
						              </div>
					            </div>
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Type*</label>
										<div class="col-sm-5">
						                    <form:select path="type" class="form-control" id="type">
												<form:option value="" label="--- Select ---"/>
						   						<form:options items="${typeList}" />
											</form:select>            
											<form:errors path="type" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div id="salarydiv" style="display:none">
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Basic Salary*</label>
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
							                                id="overTimeAmt" placeholder="Enter overtime amount" />
												<form:errors path="overTimeAmt" class="text-danger" />
											</div>
									  	</div>
									</div>
									<div class="row">
									  	<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Overtime Hours</label>
											<div class="col-sm-5">
												<form:input path="overTimeHours" type="text" class="form-control"
							                                id="overTimeHours" placeholder="Enter overtime hours" />
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
							                  			  id="allowance" placeholder="Enter allowance"/>
							                  		<form:errors path="allowance" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Medical</label>
												<div class="col-sm-5">
							                  		<form:input path="medical" type="text" class="form-control" 
							                  			  id="medical" placeholder="Enter medical"/>
							                  		<form:errors path="medical" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Leave Balance</label>
												<div class="col-sm-5">
							                  		<form:input path="leaveBalance" type="text" class="form-control" 
							                  			  id="leaveBalance" placeholder="Enter leave balance"/>
							                  		<form:errors path="leaveBalance" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Leave Taken</label>
												<div class="col-sm-5">
							                  		<form:input path="leaveTaken" type="text" class="form-control" 
							                  			  id="leaveTaken" placeholder="Enter leave taken"/>
							                  		<form:errors path="leaveTaken" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Unpaid Leave Amount</label>
												<div class="col-sm-5">
							                  		<form:input path="unpaidLeaveAmt" type="text" class="form-control" 
							                  			  id="unpaidLeaveAmt" placeholder="Enter unpaid leave amount"/>
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
							                  			  id="fwLevy" placeholder="Enter foreigner worker levy"/>
							                  		<form:errors path="fwLevy" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						        </div>
						        <div id="bonusdiv" style="display:none">
						            <div class="row">
							            <div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Bonus Amount*</label>
												<div class="col-sm-5">
							                  		<form:input path="bonusAmt" type="text" class="form-control" 
							                  			  id="bonusAmt" placeholder="Enter bonus amount"/>
							                  		<form:errors path="bonusAmt" class="text-danger" />
							                	</div>
							              </div>
						            </div>
						        </div>
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
								<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<button id="addSalaryBonusBtn" type="submit" class="btn btn-primary" form ="createSalaryBonusForm">Add</button>
										<button id="addSalaryBonusAndPayBtn" type="submit" class="btn btn-primary" form ="createSalaryBonusForm" formaction="<c:url value="/salarybonus/createSalaryBonusAndPay" />">Add and Pay</button>
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
    	  $('#dateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });

    	  $('#type').change(function(event) {
    		  loadTypeDiv();
		    });	

    	  loadTypeDiv();
		   
	 } );

      function loadTypeDiv(){
    		var typeid = $("select#type").val();
  		if(typeid == "SALARY") {
  	      	$("#salarydiv").css("display","");
  	      	$("#bonusdiv").css("display","none");
  		}else if(typeid == "BONUS"){
  			$("#salarydiv").css("display","none");
  	      	$("#bonusdiv").css("display","");
  		}else{
  			$("#salarydiv").css("display","none");
  	      	$("#bonusdiv").css("display","none");
  	  	}
   	}

      

    </script>
    