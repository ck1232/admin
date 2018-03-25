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
                    <!--/.FORM-->
		             	<div class="box-body">
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Employee Name</div>
									<div class="col-sm-5">${salarybonus.name}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Date</div>
								    <div class="col-sm-5">${salarybonus.dateString}</div>
								</div>
							</div>
							<c:if test="${salarybonus.type == 'Salary'}">
								<div class="row">
							  		<div class="form-group">	  
										<div class="col-sm-2">Basic Salary</div>
										<div class="col-sm-5">${salarybonus.basicSalaryAmt}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Overtime Amount</div>
									    <div class="col-sm-5">${salarybonus.overTimeAmt}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Overtime Hours</div>
									    <div class="col-sm-5">${salarybonus.overTimeHours}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Overtime Remarks</div>
									    <div class="col-sm-5">${salarybonus.overTimeRemarks}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Allowance</div>
									    <div class="col-sm-5">${salarybonus.allowance}</div>
									</div>
								</div>
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Medical</div>
									    <div class="col-sm-5">${salarybonus.medical}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Leave Balance</div>
									    <div class="col-sm-5">${salarybonus.leaveBalance}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Leave Taken</div>
									    <div class="col-sm-5">${salarybonus.leaveTaken}</div>
									</div>
								</div>
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Unpaid Leave Amount</div>
									    <div class="col-sm-5">${salarybonus.unpaidLeaveAmt}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Unpaid Leave Remarks</div>
									    <div class="col-sm-5">${salarybonus.unpaidLeaveRemarks}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">CDAC Amount</div>
									    <div class="col-sm-5">${salarybonus.cdacAmt}</div>
									</div>
								</div>		
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">SDL Amount</div>
									    <div class="col-sm-5">${salarybonus.sdlAmt}</div>
									</div>
								</div>	
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Foreign Worker Levy</div>
									    <div class="col-sm-5">${salarybonus.fwLevy}</div>
									</div>
								</div>	
							</c:if>
							<c:if test="${salarybonus.type == 'Bonus'}">
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Bonus Amount</div>
									    <div class="col-sm-5">${salarybonus.bonusAmt}</div>
									</div>
								</div>	
							</c:if>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Employee CPF</div>
								    <div class="col-sm-5">${salarybonus.employeeCpf}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Employer CPF</div>
								    <div class="col-sm-5">${salarybonus.employerCpf}</div>
								</div>
							</div>				
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
		      		<div class="box">
						<div class="box-header with-border">
	                  		<h3 class="box-title">Other Related Paid Salary / Bonus</h3>
	                  	</div>
			      		<div class="box-body no-padding">
			              	<table class="table table-striped">
			              		<c:if test="${otherList.size() > 0}">
				                	<tr>
				                		<th style="width: 14%">Employee Name</th>
				                		<th style="width: 14%">Date</th>
				                  		<th style="width: 14%">Type</th>
				                  		<th style="width: 14%">Gross Amount</th>
				                  		<th style="width: 14%">Take Home Amount</th>
				                  		<th style="width: 14%">Bonus Amount</th>
						          		<th style="width: 14%">Status</th>
				                	</tr>
				                	<c:forEach items="${otherList}" var="other">
										<tr>
											<td style="width: 14%">${other.name}</td>
						          			<td style="width: 14%">${other.dateString}</td>
						          			<td style="width: 14%">${other.type}</td>
						          			<td style="width: 14%">${other.grossAmt}</td>
						          			<td style="width: 14%">${other.takehomeAmt}</td>
						          			<td style="width: 14%">${other.bonusAmt}</td>
						          			<td style="width: 14%">${other.status}</td>
					          			</tr>
						        	</c:forEach>
					        	</c:if>
					        	<c:if test="${otherList.size() == 0}">
				        			<tr>
				        				<td colspan="6">No records found.</td>
				        			</tr>
			        			</c:if>
						        
			              	</table>
					    </div>
					</div>
		      		<tiles:insertAttribute name="paymentdetail"/>
		      		
		      		<div class="row">
	            		<div class="form-group">
		            		<div class="col-sm-2"></div>
							<div class="col-sm-10">
			                  	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
							</div>
						</div>
					</div>	
              </div>
    	</div>
    </section>