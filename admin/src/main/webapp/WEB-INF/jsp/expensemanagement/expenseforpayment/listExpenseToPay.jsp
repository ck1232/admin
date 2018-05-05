<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->
  	<div class="row">
  		<div class="col-md-12">
  			<!--BOX-->
              <div class="box">
              	<!--BOX HEADER-->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Selected Expense(s)</h3>
                  </div>
             	<div class="box-body">
					<div class="row">
				  		<div class="form-group">
							<div class="col-sm-2">Expense Date</div>
							<div class="col-sm-2">Expense Type</div>
							<div class="col-sm-2">Description</div>
							<div class="col-sm-2">Expense Amount</div>
							<div class="col-sm-2">Supplier</div>
							<div class="col-sm-2">Status</div>
						</div>
					</div>
					<c:forEach var="expense" items="${expenseList}">
						<div class="row">
					  		<div class="form-group">
							    <div class="col-sm-2">${expense.expensedateString}</div>
							    <div class="col-sm-2">${expense.expensetype}</div>
							    <div class="col-sm-2">${expense.description}</div>
							    <div class="col-sm-2">${expense.totalAmtString}</div>
							    <div class="col-sm-2">${expense.supplier}</div>
							    <div class="col-sm-2">${expense.status}</div>
							</div>
						</div>
					</c:forEach>	
					<br/>
					<hr>
					<div class="row">
				  		<div class="form-group">
							<div class="col-sm-6">Total Amount to be Paid:</div>
							<div class="col-sm-6">${totalamount}</div>
						</div>
					</div>	
				</div>
				<!-- /.box-body -->
      		</div>
      		<!-- /.BOX -->
            </div>
  	</div>