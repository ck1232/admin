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
                    	<h3 class="box-title">Promotion Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/promotion/listPromotion" />"></form>
                    <c:url var="post_url" value="/product/promotion/createPromotion" />
                    <form:form id="createPromotionForm" method="post" modelAttribute="promotionForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input path="promotionStartDate" type="hidden" id="promotionStartDate"/>
                    <form:input path="promotionEndDate" type="hidden" id="promotionEndDate"/>
			              <div class="box-body">
				              	<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Name</label>
										<div class="col-sm-10">
											<form:input path="promotionName" type="text" class="form-control"
						                                id="promotionName" placeholder="Enter promotion name" />
											<form:errors path="promotionName" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Message</label>
										<div class="col-sm-10">
											<form:input path="promotionMessage" type="text" class="form-control"
						                                id="promotionMessage" placeholder="Enter promotion message" />
											<form:errors path="promotionMessage" class="text-danger" />
										</div>
								  	</div>
								</div>
					            <div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Promotion Period</label>
											<div class="col-sm-10">
						                  		<form:input path="promotionperiod" type="text" class="form-control" 
						                  			  id="promotionperiod" placeholder="Press to select date"/>
						                  		<form:errors path="promotionperiod" class="text-danger" />
						                	</div>
						              </div>
					            </div>
								<div class="row">		  
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">Active</label>
									<div class="col-sm-10">
										<div class="checkbox">
									  		<label><form:checkbox path="isActiveBoolean" isActiveBoolean="isActive" /></label>
									     	<form:errors path="isActiveBoolean" class="text-danger" />
										</div>
									</div>
							  	</div>
							</div>
							<br/>
							<br/>
								<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<button id="addPromotionBtn" type="submit" class="btn btn-primary" form ="createPromotionForm">Add</button>
					                  <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
								</div>
								</div>
			              </div>
			              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
    		</div>
    	</div>
    </section>
    
    <script>
      $( function() {
    	//Date range picker with time picker
    	$('#promotionperiod').daterangepicker(
   	    	{
   	    		timePicker: true,
   	         	timePickerIncrement: 1,
   	         	locale: {
   	             format: 'DD/MM/YYYY h:mm A'
   	 			}
           	}, 
   	    	function(start, end, label) {
       	    	$("#promotionStartDate").prop("value", start.format('YYYY-MM-DD h:mm'));
       	    	$("#promotionEndDate").prop("value", end.format('YYYY-MM-DD h:mm'));
        });

	   	var date = new Date();
		var day = date.getUTCDate();
		var month = date.getUTCMonth();
		var year = date.getUTCFullYear();
	   if (document.getElementById("promotionStartDate").value == '') {			
			$("#promotionStartDate").datepicker("setDate", new Date(year, month, day));
	    }
	   if (document.getElementById("promotionEndDate").value == '') {			
			$("#promotionEndDate").datepicker("setDate", new Date(year, month, day));
	    }

	    $("#addPromotionBtn").click(function(){
	    	if (document.getElementById("promotionStartDate").value == '') {			
				$("#promotionStartDate").datepicker("setDate", new Date(year, month, day));
		    }
		   if (document.getElementById("promotionEndDate").value == '') {			
				$("#promotionEndDate").datepicker("setDate", new Date(year, month, day));
		    }
		}); 
		   
	 } );

      

    </script>
    