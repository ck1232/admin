<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	var supplierList = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('supplier'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  prefetch: '<c:url context="${context}" value="/expense/getSupplierList" />'
		}); 

	 $( function() {
		$('#addSupplierDiv .typeahead').typeahead(null, {
			 name: 'supplierList',
			  display: 'supplier',
			source: supplierList
		});
	 } );
	
</script>


<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Expense Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/expense/listExpense" />"></form>
                    <c:url var="post_url" value="/expense/createExpense" />
                    <form:form id="createExpenseForm" method="post" modelAttribute="expenseForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			              <div class="box-body">
			              	<div class="row">
						            <div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Expense Date*</label>
										<div class="col-sm-5">
					                  		<form:input path="expensedateString" type="text" class="form-control" 
					                  			  id="expensedateString" placeholder="Press to select date"/>
					                  		<form:errors path="expensedateString" class="text-danger" />
					                	</div>
						              </div>
					            </div>
				              	<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Type*</label>
										<div class="col-sm-5">
						                    <form:select path="expenseTypeId" class="form-control" id="expenseTypeId">
												<form:option value="" label="--- Select ---"/>
						   						<form:options items="${expenseTypeList}" />
											</form:select>            
											<form:errors path="expenseTypeId" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
								  	<div id="invoicediv" class="form-group ${status.error ? 'has-error' : ''}" style="display:none">
								  		<label class="col-sm-2 control-label">Invoice no*</label>
										<div class="col-sm-5">
											<form:input path="invoiceNo" type="text" class="form-control"
						                                id="invoiceNo" placeholder="Enter invoice no" />
											<form:errors path="invoiceNo" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">		  
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Description</label>
										<div class="col-sm-5">
											<form:input path="description" type="text" class="form-control"
						                                id="description" placeholder="Enter description" />
											<form:errors path="description" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Amount*</label>
										<div class="col-sm-5">
											<div class="input-group">
												<span class="input-group-addon amount">$</span>
												<form:input path="totalAmt" type="text" class="form-control"
						                                id="totalAmt" placeholder="Enter amount" />
											</div>
											<form:errors path="totalAmt" class="text-danger" />
											
										</div>
								  	</div>
								</div>
								<div class="row">		  
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Supplier</label>
										<div class="col-sm-5" id="addSupplierDiv">
											<form:input path="supplier" type="text" class="form-control typeahead"
						                                id="supplierId" placeholder="Enter supplier" />
											<form:errors path="supplier" class="text-danger" />
										</div>
								  	</div>
								</div>
								<div class="row">		  
								  	<div class="form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 control-label">Remarks</label>
										<div class="col-sm-5">
											<form:input path="remarks" type="text" class="form-control"
						                                id="remarks" placeholder="Enter remarks" />
											<form:errors path="remarks" class="text-danger" />
										</div>
								  	</div>
								</div>
							<br/>
							<br/>
								<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
									<div class="col-sm-10">
										<button id="addExpenseBtn" type="submit" class="btn btn-primary" form ="createExpenseForm">Add</button>
										<button id="addExpenseAndPayBtn" type="submit" class="btn btn-primary" form ="createExpenseForm" formaction="<c:url value="/expense/createExpenseAndPay" />">Add and Pay</button>
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
    	  $("#expenseTypeId").change(function(){
    	  	if($("#expenseTypeId option:selected").text() == "Stock(China)"){
    	  		$('.amount').text("¥");
    	  	}else{
    	  		$('.amount').text("$");
    	  	}
    		//alert($("#expenseTypeId option:selected").text());
    	  });
    	  
    	  $('#expensedateString').datepicker({
	    		format: 'dd/mm/yyyy',
		      	autoclose: true
		    });


		  $('#expenseTypeId').change(function(event) {
			  loadExpenseType();
		    });	

		  loadExpenseType();
		   
	 } );

  	 function loadExpenseType(){
  		var expensetypeid = $("select#expenseTypeId").val();
		if(expensetypeid != "1") {
	      	$("#invoicediv").css("display","none");
		}else{
		   	$("#invoicediv").css("display","");
		}
  	 }

      

    </script>
    