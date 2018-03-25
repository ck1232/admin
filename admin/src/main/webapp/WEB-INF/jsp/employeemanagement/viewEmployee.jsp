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
                    <form id="paySalaryBonusForm" method="post" action="<c:url value="/salarybonus/paySalaryBonus" />">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
                    <form id="backToListButton" method="get" action="<c:url value="/employee/listEmployee" />"></form>
                    <form id="updateEmployeeForm" method="post" action="<c:url value="/employee/updateEmployee" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="editBtn" value="${employee.employeeId}"/>
                    </form>
                    <!--/.FORM-->
		             	<div class="box-body">
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Employee Name</div>
									<div class="col-sm-5">${employee.name}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Employment Type</div>
								    <div class="col-sm-5">${employee.employeeTypeString}</div>
								</div>
							</div>			
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Date of Birth</div>
									<div class="col-sm-5">${employee.dobString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Nationality</div>
								    <div class="col-sm-5">${employee.nationality}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Basic Salary</div>
								    <div class="col-sm-5">${employee.basicSalaryString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Start Date</div>
								    <div class="col-sm-5">${employee.employmentStartDateString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">End Date</div>
								    <div class="col-sm-5">${employee.employmentEndDateString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">CDAC Indicator</div>
								    <div class="col-sm-5">${employee.cdacInd}</div>
								</div>
							</div>	
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
              </div>
    	</div>
    	<div class="box">
			<div class="box-header with-border">
                  	<h3 class="box-title">All Salary</h3>
                  </div>
            	<div class="box-body no-padding">
            		<table class="table table-striped">
            			<c:if test="${salaryList.size() > 0}">
		           			<tr>
		                		<th style="width: 16%">Month</th>
		                		<th style="width: 16%">Salary Amount</th>
		                  		<th style="width: 16%">Status</th>
		                	</tr>
		                	<c:forEach items="${salaryList}" var="salary">
								<tr>
									<td>${salary.dateString}</td>
								    <td>${salary.takehomeAmtString}</td>
								    <td>
								    	<c:if test="${salary.status != 'PAID'}"><button type="submit" name="payBtn" value="${salary.id},${salary.type}" class="btn btn-primary" form ="paySalaryBonusForm">Pay</button></c:if>
								    	<c:if test="${salary.status == 'PAID'}">${salary.status}</c:if>
								    </td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${salaryList.size() == 0}">
							<tr>
								<td>No salary available</td>
							</tr>
						</c:if>
	           		</table>
			</div>
		</div>
		<div class="box">
			<div class="box-header with-border">
                  	<h3 class="box-title">All Bonus</h3>
                  </div>
            	<div class="box-body no-padding">
            		<table class="table table-striped">
            			<c:if test="${salaryList.size() > 0}">
		           			<tr>
		                		<th style="width: 16%">Year</th>
		                		<th style="width: 16%">Bonus Amount</th>
		                  		<th style="width: 16%">Status</th>
		                	</tr>
		                	<c:forEach items="${bonusList}" var="bonus">
								<tr>
									<td>${bonus.dateString}</td>
								    <td>${bonus.bonusAmtString}</td>
								    <td>
								    	<c:if test="${bonus.status != 'PAID'}"><button type="submit" name="payBtn" value="${bonus.id},${bonus.type}" class="btn btn-primary" form ="paySalaryBonusForm">Pay</button></c:if>
								    	<c:if test="${bonus.status == 'PAID'}">${bonus.status}</c:if>
								    </td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${bonusList.size() == 0}">
							<tr>
								<td>No bonus available</td>
							</tr>
						</c:if>
	           		</table>
			</div>
		</div>
    	<div class="row">
       		<div class="form-group">
	        	<div class="col-sm-2"></div>
				<div class="col-sm-10">
					<button type="submit" class="btn btn-primary" form ="updateEmployeeForm">Edit</button>
	                 	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
				</div>
			</div>
		</div>	
    </section>