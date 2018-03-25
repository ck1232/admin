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
               		<c:if test="${invoiceList[0].type == 'invoice'}"><h3 class="box-title">Selected Invoice(s)</h3></c:if>
               		<c:if test="${invoiceList[0].type == 'grant'}"><h3 class="box-title">Selected Grant(s)</h3></c:if>
                  </div>
             	<div class="box-body">
					<div class="row">
				  		<div class="form-group">
							<c:if test="${invoiceList[0].type == 'invoice'}"><div class="col-sm-2">Invoice Id</div></c:if>
							<div class="col-sm-4">Messenger</div>
							<c:if test="${invoiceList[0].type == 'invoice'}"><div class="col-sm-2">Invoice Amount</div></c:if>
							<c:if test="${invoiceList[0].type == 'grant'}"><div class="col-sm-2">Grant Amount</div></c:if>
							<c:if test="${invoiceList[0].type == 'invoice'}"><div class="col-sm-2">Invoice Date</div></c:if>
							<c:if test="${invoiceList[0].type == 'grant'}"><div class="col-sm-2">Grant Date</div></c:if>
							<div class="col-sm-2">Status</div>
						</div>
					</div>
					<c:forEach var="invoice" items="${invoiceList}">
						<div class="row">
					  		<div class="form-group">
							    <c:if test="${invoice.type == 'invoice'}"><div class="col-sm-2">${invoice.invoiceId}</div></c:if>
							    <div class="col-sm-4">${invoice.messenger}</div>
							    <div class="col-sm-2">${invoice.totalAmtString}</div>
							    <div class="col-sm-2">${invoice.invoicedateString}</div>
							    <div class="col-sm-2">${invoice.status}</div>
							</div>
						</div>
					</c:forEach>	
					<br/>
					<hr>
					<div class="row">
				  		<div class="form-group">
							<c:if test="${invoiceList[0].type == 'invoice'}"><div class="col-sm-6">Total Amount to be Paid:</div></c:if>
							<c:if test="${invoiceList[0].type == 'grant'}"><div class="col-sm-4">Total Amount to be Paid:</div></c:if>
							<div class="col-sm-6">${totalamount}</div>
						</div>
					</div>		
				</div>
				<!-- /.box-body -->
      		</div>
      		<!-- /.BOX -->
            </div>
  	</div>