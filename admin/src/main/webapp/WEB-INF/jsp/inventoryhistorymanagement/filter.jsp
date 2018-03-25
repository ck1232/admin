<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="box box-default">
	<div class="box-header with-border">
	  <h3 class="box-title">Filter</h3>
	  
	  <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
      </div>
	</div>
	<c:url var="post_url" value="/inventoryhistory/searchInventoryHistoryList" />
	<form:form id = "searchListForm" method = "post" modelAttribute = "inventoryHistoryForm" action="${post_url}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="box-body">
		    <div class="row">
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Product Name</label>
	                <form:input path="productname" type="text" class="form-control"
						      id="productname" placeholder="" />
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Product Code</label>
	                <form:input path="itemcode" type="text" class="form-control"
						      id="itemcode" placeholder="" />
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Location</label>
	                <form:select path="location" class="form-control" id="location">
						<form:option value="NONE" label="--- Select ---"/>
   						<form:options items="${locationList}" />
					</form:select>
	              </div>
		      </div>
		    </div>
		    <div class="row">
		      <div class="col-md-4">
		      	<div class="form-group">
	                <label>Mode</label>
					<form:select path="mode" class="form-control" id="mode">
						<form:option value="NONE" label="--- Select ---"/>
   						<form:options items="${modeList}" />
					</form:select>
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Created Date From</label>
	                <form:input path="createddatefrom" type="text" class="form-control"
						      id="createddatefrom"  />
					<form:errors path="createddatefrom" class="text-danger" />
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
		      	 	<label>Created Date To</label>
	                <form:input path="createddateto" type="text" class="form-control"
						      id="createddateto"  />
					<form:errors path="createddateto" class="text-danger" />
	              </div>
		      </div>
		   </div>
		   <div class="row">
		     <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Created By</label>
	                <form:input path="createdby" type="text" class="form-control"
						      id="createdby" placeholder="" />
	              </div>
		      </div>
		   </div>
	    </div>
    </form:form>
    <div class="margin">
		<div class="btn-grp">
			<button class="btn btn-primary pull-right" type="submit" form="searchListForm"><i class="fa fa-search"></i> Search</button>
		</div>
	</div>
	<br>
	<br>
</div>

<script> 	
    $( function() {
    	$('#createddatefrom').datepicker(
    	{
    		dateFormat: 'dd/MM/yyyy',
	      	autoclose: true
	    });
    	$('#createddateto').datepicker(
    	{
    		dateFormat: 'dd/MM/yyyy',
	      	autoclose: true
	    });
    });
</script>