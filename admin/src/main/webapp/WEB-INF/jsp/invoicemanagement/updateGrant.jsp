<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Content Wrapper. Contains page content -->
	<div class="box">
		<!--BOX HEADER-->
	    <div class="box-header with-border">
	    	<h3 class="box-title">Grant Information</h3>
	    </div>
	    <!--FORM-->
	    
		<div class="box-body">
			<form id="backToListButton" method="get" action="<c:url value="/invoice/listInvoice" />"></form>
			<c:url var="post_url" value="/invoice/updateGrantToDb" />
			<form:form id="updateGrantToDbForm" method="post" modelAttribute="grantForm" action="${post_url }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<form:input path="grantId" type="hidden" id="grantId"/>
				<div class="row">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Organisation*</label>
						<div class="col-sm-5">
	                  		<form:input path="messenger" type="text" class="form-control" 
	                  			  id="messenger" placeholder="Enter organisation"/>
	                  		<form:errors path="messenger" class="text-danger" />
	                	</div>
		 			</div>
	 			</div>
				<div class="row">
				  	<div class="form-group ${status.error ? 'has-error' : ''}">
				  		<label class="col-sm-2 control-label">Grant Date*</label>
						<div class="col-sm-5">
							<form:input path="invoicedateString" type="text" class="form-control"
		                                id="invoicedateString" placeholder="Press to select date" />
							<form:errors path="invoicedateString" class="text-danger" />
						</div>
				  	</div>
				</div>
				<div class="row">		  
				  	<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Total Amount*</label>
						<div class="col-sm-5">
							<form:input path="totalAmt" type="text" class="form-control"
		                                id="totalAmt" placeholder="Enter total amount" />
							<form:errors path="totalAmt" class="text-danger" />
						</div>
				  	</div>
				</div>
				<br/>
				<br/>
	
	  		</form:form>
			<!--/.FORM-->
	 			<div class="row">
				 	<div class="form-group">
						<label class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<button id="updateGrantBtn" type="submit" class="btn btn-primary" form="updateGrantToDbForm">Update</button>
					        <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
					    </div>
					</div>
				</div>
			</div>
	        <!-- /.box-body -->
		</div>
		<!-- /.BOX -->
		
	<script>
		$( function() {
    	  $('#invoicedateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });

	 	} );
    </script>
		