<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

					<div class="box">
	                	<!--BOX HEADER-->
	                    <div class="box-header with-border">
	                    	<h3 class="box-title">Payment Details</h3>
	                    </div>
				            <div class="box-body no-padding">
				              	<table class="table table-striped">
				              		<c:if test="${paymentList.size() > 0}">
					                	<tr>
					                		<th style="width: 16%">Date Paid</th>
					                		<th style="width: 16%">Payment Mode</th>
					                  		<th style="width: 16%">Amount</th>
					                  		<th style="width: 16%">Cheque No</th>
					                  		<th style="width: 16%">Bounced</th>
							          		<th style="width: 16%">Bounce Date</th>
					                	</tr>
					                	<c:forEach items="${paymentList}" var="payment">
											<tr>
												<td style="width: 16%">${payment.paymentDateString}</td>
							          			<td style="width: 16%">${payment.paymentModeString}</td>
							          			<td style="width: 16%">${payment.paymentAmtString}</td>
							          			<td style="width: 16%">${payment.chequeVO.chequeNum}</td>
							          			<td style="width: 16%">
							          				<c:if test="${payment.chequeVO.bounceChequeInd == 'N'}">No</c:if>
							          				<c:if test="${payment.chequeVO.bounceChequeInd == 'Y'}">Yes</c:if>
							          			</td>
							          			<c:if test="${payment.chequeVO.bounceChequeInd == 'Y'}">
							          				<td style="width: 16%">${payment.chequeVO.bounceDateString}</td>
												</c:if>
						          			</tr>
							        	</c:forEach>
						        	</c:if>
						        	<c:if test="${paymentList.size() == 0}">
					        			<tr>
					        				<td colspan="6">No records found.</td>
					        			</tr>
				        			</c:if>
							        
				              	</table>
				            </div>
				     		<!-- /.box-body -->
		      		<!-- /.BOX -->
              		</div>