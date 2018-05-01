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
                    	<c:if test="${invoice.type == 'invoice'}"><h3 class="box-title">Invoice Information</h3></c:if>
                    	<c:if test="${invoice.type == 'grant'}"><h3 class="box-title">Grant Information</h3></c:if>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/invoice/listInvoice" />"></form>
  
                    <!--/.FORM-->
		             	<div class="box-body">
		             		<c:if test="${invoice.type == 'invoice'}">
								<div class="row">
							  		<div class="form-group">
										<div class="col-sm-2">Invoice id</div>
										<div class="col-sm-5">${invoice.invoiceId}</div>
									</div>
								</div>
							</c:if>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Messenger</div>
								    <div class="col-sm-5">${invoice.messenger}</div>
								</div>
							</div>			
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Total Price</div>
									<div class="col-sm-5">${invoice.totalAmtString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <c:if test="${invoice.type == 'invoice'}"><div class="col-sm-2">Invoice Date</div></c:if>
								    <c:if test="${invoice.type == 'grant'}"><div class="col-sm-2">Grant Date</div></c:if>
								    <div class="col-sm-5">${invoice.invoicedateString}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Status</div>
								    <div class="col-sm-5">${invoice.status}</div>
								</div>
							</div>	
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
		      		<c:if test="${invoice.type == 'invoice'}">
			      		<div class="box">
							<div class="box-header with-border">
		                  		<h3 class="box-title">Other Related Paid Invoices </h3>
		                  	</div>
				      		<div class="box-body no-padding">
				              	<table class="table table-striped">
				              		<c:if test="${otherList.size() > 0}">
					                	<tr>
					                		<c:if test="${invoice.type == 'invoice'}"><th style="width: 16%">Invoice Id</th></c:if>
					                		<th style="width: 16%">Messenger</th>
					                  		<th style="width: 16%">Total Price</th>
					                  		<th style="width: 16%">Date</th>
							          		<th style="width: 16%">Status</th>
					                	</tr>
					                	<c:forEach items="${otherList}" var="other">
											<tr>
												<td style="width: 16%">${other.invoiceId}</td>
							          			<td style="width: 16%">${other.messenger}</td>
							          			<td style="width: 16%">${other.totalAmtString}</td>
							          			<td style="width: 16%">${other.invoicedateString}</td>
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
					</c:if>
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