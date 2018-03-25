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
                  	<h3 class="box-title">Selected Salary / Bonus</h3>
                  </div>
             	<div class="box-body">
					<div class="row">
				  		<div class="form-group">
							<div class="col-sm-2">Month</div>
							<div class="col-sm-2">Employee Name</div>
							<div class="col-sm-2">Amount</div>
							<div class="col-sm-2">Status</div>
						</div>
					</div>
					<c:forEach var="salary" items="${salaryList}">
						<div class="row">
					  		<div class="form-group">
							    <div class="col-sm-2">${salary.dateString}</div>
							    <div class="col-sm-2">${salary.name}</div>
							    
							    <div class="col-sm-2"><c:choose><c:when test="${salary.takehomeAmt != null}">${salary.takehomeAmtString}</c:when><c:otherwise>${salary.bonusAmtString}</c:otherwise></c:choose></div>
							    <div class="col-sm-2">${salary.status}</div>
							</div>
						</div>
					</c:forEach>
					
					<br/>
					<hr>
					<div class="row">
				  		<div class="form-group">
							<div class="col-sm-4">Total Amount to be Paid:</div>
							<div class="col-sm-6">${totalamount}</div>
						</div>
					</div>			
				</div>
				<!-- /.box-body -->
      		</div>
      		<!-- /.BOX -->
            </div>
  	</div>