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
                    	<h3 class="box-title">Expense Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/expense/listExpense" />"></form>
                    <form id="updateExpenseForm" method="post" action="<c:url value="/expense/updateExpense" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="editBtn" value="${expense.expenseId}"/>
                    </form>
                    <!--/.FORM-->
		             	<div class="box-body">
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Expense Date</div>
									<div class="col-sm-5">${expense.expensedateString}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Expense Type</div>
								    <div class="col-sm-5">${expense.expensetype}</div>
								</div>
							</div>			
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Invoice No</div>
									<div class="col-sm-5">${expense.invoiceNo}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Expense Description</div>
								    <div class="col-sm-5">${expense.description}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Expense Amount</div>
								    <div class="col-sm-5">${expense.totalAmt}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Expense Supplier</div>
								    <div class="col-sm-5">${expense.supplier}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Expense Payment Status</div>
								    <div class="col-sm-5">${expense.status}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Remarks</div>
								    <div class="col-sm-5">${expense.remarks}</div>
								</div>
							</div>	
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
		      		<div class="box">
						<div class="box-header with-border">
	                  		<h3 class="box-title">Other Related Paid Expenses</h3>
	                  	</div>
			      		<div class="box-body no-padding">
			              	<table class="table table-striped">
			              		<c:if test="${otherList.size() > 0}">
				                	<tr>
				                		<th style="width: 16%">Expense Date</th>
				                		<th style="width: 16%">Expense Type</th>
				                  		<th style="width: 16%">Description</th>
				                  		<th style="width: 16%">Expense Amount</th>
				                  		<th style="width: 16%">Supplier</th>
						          		<th style="width: 16%">Status</th>
				                	</tr>
				                	<c:forEach items="${otherList}" var="other">
										<tr>
											<td style="width: 16%">${other.expensedateString}</td>
						          			<td style="width: 16%">${other.expensetype}</td>
						          			<td style="width: 16%">${other.description}</td>
						          			<td style="width: 16%">${other.totalAmt}</td>
						          			<td style="width: 16%">${other.supplier}</td>
						          			<td style="width: 16%">${other.status}</td>
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
								<button type="submit" class="btn btn-primary" form ="updateExpenseForm">Edit</button>
			                  	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
							</div>
						</div>
					</div>	
              </div>
    	</div>
    </section>