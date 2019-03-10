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
                    	<h3 class="box-title">Cheque Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/cheque/listCheque" />"></form>
                    <form id="updateChequeForm" method="post" action="<c:url value="/cheque/updateCheque" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="editBtn" value="${cheque.chequeId}"/>
                    </form>
                    <!--/.FORM-->
		             	<div class="box-body">
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Cheque No</div>
									<div class="col-sm-5">${cheque.chequeNum}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Cheque Amount</div>
								    <div class="col-sm-5">$${cheque.chequeAmt}</div>
								</div>
							</div>			
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Cheque Date</div>
									<div class="col-sm-5">${cheque.chequeDateString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Debit Date</div>
								    <div class="col-sm-5">${cheque.debitDate}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Remarks</div>
								    <div class="col-sm-5">${cheque.remarks}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Bounced</div>
								    <div class="col-sm-5">${cheque.bounceChequeInd}</div>
								</div>
							</div>	
							<c:if test="${cheque.bounceChequeInd == 'Y'}">
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">Bounce Date</div>
									    <div class="col-sm-5">${cheque.bounceDateString}</div>
									</div>
								</div>
							</c:if>
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
              </div>
    	</div>
    	<c:if test="${expenseList != null}">
	    	<div class="box">
				<div class="box-header with-border">
                  	<h3 class="box-title">All Paid Expense</h3>
                  </div>
            	<div class="box-body no-padding">
            		<table class="table table-striped">
            			<tr>
	                		<th style="width: 16%">Expense Date</th>
	                		<th style="width: 16%">Expense Type</th>
	                  		<th style="width: 16%">Description</th>
	                  		<th style="width: 16%">Expense Amount</th>
	                  		<th style="width: 16%">Supplier</th>
	                  		<th style="width: 16%">Status</th>
	                	</tr>
	                	<c:forEach items="${expenseList}" var="expense">
							<tr>
								<td>${expense.expensedateString}</td>
							    <td>${expense.expenseType}</td>
							    <td>${expense.description}</td>
							    <td>${expense.totalAmtString}</td>
							    <td>${expense.supplier}</td>
							    <td>${expense.status}</td>
							</tr>
						</c:forEach>
            		</table>
				</div>
			</div>
		</c:if>
		<c:if test="${salaryList != null}">
	    	<div class="box">
				<div class="box-header with-border">
                  	<h3 class="box-title">All Paid Salary</h3>
                </div>
                <div class="box-body no-padding">
	           		<table class="table table-striped">
	           			<tr>
	                		<th style="width: 16%">Salary Month</th>
	                		<th style="width: 16%">Employee Name</th>
	                  		<th style="width: 16%">Salary Amount</th>
	                  		<th style="width: 16%">Status</th>
	                	</tr>
	                	<c:forEach items="${salaryList}" var="salary">
							<tr>
								<td>${salary.dateString}</td>
							    <td>${salary.employeeVO.name}</td>
							    <td>${salary.takehomeAmtString}</td>
							    <td>${salary.status}</td>
							</tr>
						</c:forEach>
	           		</table>
				</div>
			</div>
		</c:if>
		<c:if test="${bonusList != null}">
	    	<div class="box">
				<div class="box-header with-border">
                  	<h3 class="box-title">All Paid Bonus</h3>
                </div>
                <div class="box-body no-padding">
	           		<table class="table table-striped">
	           			<tr>
	                		<th style="width: 16%">Bonus Month</th>
	                		<th style="width: 16%">Employee Name</th>
	                  		<th style="width: 16%">Bonus Amount</th>
	                  		<th style="width: 16%">Status</th>
	                	</tr>
	                	<c:forEach items="${bonusList}" var="bonus">
							<tr>
								<td>${bonus.dateString}</td>
							    <td>${bonus.employeeVO.name}</td>
							    <td>${bonus.bonusAmtString}</td>
							    <td>${bonus.status}</td>
							</tr>
						</c:forEach>
	           		</table>
				</div> 
			</div>
		</c:if>
		<c:if test="${invoiceList != null}">
	    	<div class="box">
				<div class="box-header with-border">
                  	<h3 class="box-title">All Paid invoice</h3>
                </div>
                <div class="box-body no-padding">
	           		<table class="table table-striped">
	           			<tr>
	                		<th style="width: 16%">Invoice Id</th>
	                		<th style="width: 33%">Messenger</th>
	                  		<th style="width: 16%">Total Price</th>
	                  		<th style="width: 16%">Invoice Date</th>
	                  		<th style="width: 16%">Status</th>
	                	</tr>
	                	<c:forEach items="${invoiceList}" var="invoice">
							<tr>
								<td>${invoice.invoiceId}</td>
							    <td>${invoice.messenger}</td>
							    <td>${invoice.totalAmtString}</td>
							    <td>${invoice.invoicedateString}</td>
							    <td>${invoice.status}</td>
							</tr>
						</c:forEach>
	           		</table>
				</div>
			</div>
		</c:if>
    	<div class="row">
       		<div class="form-group">
	        	<div class="col-sm-2"></div>
				<div class="col-sm-10">
					<c:if test="${cheque.bounceChequeInd != 'Y'}"><button type="submit" class="btn btn-primary" form ="updateChequeForm">Edit</button></c:if>
	            	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
				</div>
			</div>
		</div>	
    </section>