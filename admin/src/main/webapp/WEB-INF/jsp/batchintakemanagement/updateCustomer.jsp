<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Basic Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/customer/viewCustomer/${customerForm.customerid}" />"></form>
                    <c:url var="post_url" value="/customer/updateCustomerToDb" />
                    <form:form id="updateCustomerToDbForm" method="post" modelAttribute="customerForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input path="customerid" type="hidden" id="customerid"/>
		              <div class="box-body">
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<form:input path="name" type="text" class="form-control"
				                                id="name" placeholder="Enter customer name" />
								<form:errors path="name" class="text-danger" />
							</div>
						  </div>
						   <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Date of Birth</label>
							<div class="col-sm-10">
								<input type="text" class="form-control"
				                                id="dob" placeholder="Press to select date"
				                                value="<fmt:formatDate value='${customerForm.dob}' type='date' pattern='dd/MM/yyyy'/>" />
								<form:errors path="dob" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Email Address</label>
							<div class="col-sm-10">
								<form:input path="emailaddress" type="text" class="form-control"
				                                id="emailaddress" placeholder="Enter email address" />
								<form:errors path="emailaddress" class="text-danger" />
							</div>
						  </div>
						 
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Active</label>
							<div class="col-sm-10">
								<div class="checkbox">
							  		<label><form:checkbox path="isactive" id="isactive" /></label>
							     	<form:errors path="isactive" class="text-danger" />
								</div>
							</div>
						  </div>
						  
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
			                  <button type="submit" class="btn btn-primary" form="updateCustomerToDbForm">Update
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
    	$('#dob').datepicker({
    		dateFormat: 'DD/MM/YYYY',
	      	autoclose: true
	    });
		   
	 } );

      

    </script>