<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Promotion Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/promotion/listPromotion" />"></form>
                    <c:url var="post_url" value="/product/promotion/updatePromotionToDb" />
                    <form:form id="updatePromotionToDbForm" method="post" modelAttribute="promotionForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input path="promotionStartDate" type="hidden" id="promotionStartDate"/>
                    <form:input path="promotionEndDate" type="hidden" id="promotionEndDate"/>
                    <form:input path="deleteInd" type="hidden" id="deleteInd"/>
		              <div class="box-body">
		              		<form:input path="promotionId" type="hidden" id="promotionId"/>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<form:input path="promotionName" type="text" class="form-control"
				                                id="promotionName" placeholder="Enter promotion name" />
								<form:errors path="promotionName" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Message</label>
							<div class="col-sm-10">
								<form:input path="promotionMessage" type="text" class="form-control"
				                                id="promotionMessage" placeholder="Enter promotion message" />
								<form:errors path="promotionMessage" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Promotion Period</label>
							<div class="col-sm-10">
								<form:input path="promotionperiod" type="text" class="form-control"
				                                id="promotionperiod" placeholder="Press to select date" />
								<form:errors path="promotionperiod" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Active</label>
							<div class="col-sm-10">
								<div class="checkbox">
							  		<label><form:checkbox path="isActiveBoolean" id="isActiveBoolean" /></label>
							     	<form:errors path="isActiveBoolean" class="text-danger" />
								</div>
							</div>
						  </div>
						  
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
			                  <button id="updatePromotionBtn" type="submit" class="btn btn-primary" form="updatePromotionToDbForm">Update
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

	    $("#updatePromotionBtn").click(function(){
	    	if (document.getElementById("promotionStartDate").value == '') {			
				$("#promotionStartDate").datepicker("setDate", new Date(year, month, day));
		    }
		   if (document.getElementById("promotionEndDate").value == '') {			
				$("#promotionEndDate").datepicker("setDate", new Date(year, month, day));
		    }
		}); 
		   
	 } );

      

    </script>