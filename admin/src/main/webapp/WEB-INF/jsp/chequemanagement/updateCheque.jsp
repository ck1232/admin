<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Cheque Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/cheque/listCheque" />"></form>
                    <c:url var="post_url" value="/cheque/updateChequeToDb" />
                    <form:form id="updateChequeToDbForm" method="post" modelAttribute="chequeForm" action="${post_url}">
                    <input type="hidden" name="firstDate" value="${firstDate}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input type="hidden" path="chequeId" id="chequeId"/>
		              <div class="box-body">
		              <div class="row">
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Cheque No</label>
							<div class="col-sm-5">
								<form:input path="chequeNum" type="text" class="form-control"
				                                id="chequeNum" placeholder="Enter cheque no" />
								<form:errors path="chequeNum" class="text-danger" />
							</div>
						  </div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Cheque Amount</label>
							<div class="col-sm-5">
								<form:input path="chequeAmt" type="text" class="form-control" disabled="true"
		                  			  		id="chequeAmt" placeholder=""/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Cheque Date</label>
							<div class="col-sm-5">
								<form:input path="chequeDateString" type="text" class="form-control" 
		                  			  		id="chequeDateString" placeholder="Press to select date"/>
		                  		<form:errors path="chequeDateString" class="text-danger" />
							</div>
							<div class="col-sm-3">Cheque Date cannot be before ${firstDate}</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Debit Date</label>
							<div class="col-sm-5">
								<form:input path="debitDateString" type="text" class="form-control" disabled="true"
		                  			  		id="debitDateString"/>
		                  		<form:errors path="debitDateString" class="text-danger" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Remarks</label>
							<div class="col-sm-5">
								<form:input path="remarks" type="text" class="form-control" disabled="true"
		                  			  		id="remarks" placeholder=""/>
							</div>
						</div>
					</div>
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
			                  <button id="updateChequeBtn" type="submit" class="btn btn-primary" form="updateChequeToDbForm">Update
			                  </button>
			                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel
			            </button>
							</div>
						  </div>
		              </div>
		              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
    		
    <script>
      $( function() {
    	  $('#chequeDateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });
    	  $('#debitDateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });

	 } );

      

    </script>