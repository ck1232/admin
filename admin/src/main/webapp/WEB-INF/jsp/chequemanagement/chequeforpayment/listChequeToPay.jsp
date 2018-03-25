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
                  	<h3 class="box-title">Bounced Cheque</h3>
                  </div>
                  <div class="box-body no-padding">
            		<table class="table table-striped">
            			<tr>
	                		<th style="width: 16%">Cheque No</th>
	                		<th style="width: 16%">Cheque Amount</th>
	                  		<th style="width: 16%">Cheque Date</th>
	                  		<th style="width: 16%">Remarks</th>
	                	</tr>
						<tr>
							<td style="width: 16%">${cheque.chequeNum}</td>
						    <td style="width: 16%">${cheque.chequeAmt}</td>
						    <td style="width: 16%">${cheque.chequeDateString}</td>
						    <td style="width: 16%">${cheque.remarks}</td>
						</tr>
            		</table>
				</div>
				<!-- /.box-body -->
      		</div>
      		<!-- /.BOX -->
            </div>
  	</div>