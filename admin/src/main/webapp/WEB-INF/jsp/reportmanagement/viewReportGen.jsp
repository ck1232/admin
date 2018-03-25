<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<c:url var="post_url" value="/report/generateExpenseControlReport" />
    			<form:form id="generateExpenseControlForm" method="post" modelAttribute="expenseControlForm" action="${post_url}">
	    			<!--BOX 1-->
	                <div class="box">
	                	<!--BOX HEADER-->
	                    <div class="box-header with-border">
	                    	<h3 class="box-title">Report Details</h3>
	                    </div>
	                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			            <div class="box-body">
			            	<div class="row">
			            		<div class="form-group">
				            		<div class="col-lg-2">Date as of</div>
				            		<div class="col-lg-4">
										<form:input path="startdateString" type="text" class="form-control" 
				                  			  		id="startdateString" placeholder="Press to select date"/>
				                  		<form:errors path="startdateString" class="text-danger" />
									</div>
								</div>
							</div>
							<div class="row">
			            		<div class="form-group">
				            		<div class="col-lg-2">End Date</div>
				            		<div class="col-lg-4">
										<form:input path="enddateString" type="text" class="form-control" 
				                  			  		id="enddateString" placeholder="Press to select date"/>
				                  		<form:errors path="enddateString" class="text-danger" />
									</div>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
			      	</div>
			      	<!-- /.BOX 1 -->
			      	<!--BOX 2-->
	                <div class="box">
	                	<!--BOX HEADER-->
	                    <div class="box-header with-border">
	                    	<h3 class="box-title">Report Type</h3>
	                    </div>
			            <div class="box-body">
			            	<div class="row">
			            		<div class="form-group">
			            			<c:forEach items="${reportList}" var="report">
			            				<div class="col-sm-3"><label><form:checkbox path="type" value="${report}" /></label> ${report}</div>
			            			</c:forEach>
			            		</div>
							</div>
							<div class="row">
								<div class="col-sm-3">
									<form:errors path="type" class="text-danger" />
								</div>
							</div>
						</div>
						<!-- /.box-body -->
			      	</div>
			      	<!-- /.BOX 2 -->
		      	</form:form>
		      	<!--/.FORM-->
		    	<div class="col-sm-2">
					<button type="submit" class="btn btn-primary" form ="generateExpenseControlForm">Generate</button>
				</div>
        	</div>
    	</div>
    </section>
    
    <script>
      $( function() {
    	  $('#startdateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });
		   
	 } );

      $( function() {
    	  $('#enddateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });
		   
	 } );

      

    </script>