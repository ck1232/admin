<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	var messengerList = new Bloodhound({
		  datumTokenizer: Bloodhound.tokenizers.obj.whitespace('messenger'),
		  queryTokenizer: Bloodhound.tokenizers.whitespace,
		  prefetch: '<c:url context="/JJ" value="/invoice/getMessengerList" />'
		}); 

	 $( function() {
		$('#messengerDiv .typeahead').typeahead(null, {
			 name: 'messengerList',
			  display: 'messenger',
			source: messengerList
		});
	 } );
	
</script>


<div class="box box-default">
	<div class="box-header with-border">
	  <h3 class="box-title">Export Invoice</h3>
	  
	  <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
      </div>
	</div>
	<c:url var="post_url" value="/invoice/downloadExcel" />
	<form:form id = "exportForm" method = "post" modelAttribute = "exportForm" action="${post_url}">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="box-body">
		    <div class="row">
		      <div class="col-md-4">
		      	 <div class="form-group" id="messengerDiv">
	                <label>Messenger</label>
	                <form:input path="messenger" type="text" class="form-control typeahead"
						      id="messenger" placeholder="" />
					<form:errors path="messenger" class="text-danger" />
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Invoice Status</label>
	                <form:select path="status" class="form-control" id="status">
						<form:option value="ALL" label="All"/>
   						<form:options items="${statusList}" />
					</form:select>
					<form:errors path="status" class="text-danger" />
	              </div>
		      </div>
		    </div>
		    <div class="row">
		      <div class="col-md-4">
		      	 <div class="form-group">
	                <label>Invoice Date From</label>
	                <form:input path="invoicedatefrom" type="text" class="form-control"
						      id="invoicedatefrom"  />
					<form:errors path="invoicedatefrom" class="text-danger" />
	              </div>
		      </div>
		      <div class="col-md-4">
		      	 <div class="form-group">
		      	 	<label>Invoice Date To</label>
	                <form:input path="invoicedateto" type="text" class="form-control"
						      id="invoicedateto"  />
					<form:errors path="invoicedateto" class="text-danger" />
	              </div>
		      </div>
		   </div>
	    </div>
    </form:form>
    <div class="margin">
		<div class="btn-grp">
			<button class="btn btn-primary pull-right" type="submit" form="exportForm">Export</button>
		</div>
	</div>
	<br>
	<br>
</div>

<script> 	
    $( function() {
    	$('#invoicedatefrom').datepicker(
    	{
    		dateFormat: 'dd/MM/yyyy',
	      	autoclose: true
	    });
    	$('#invoicedateto').datepicker(
    	{
    		dateFormat: 'dd/MM/yyyy',
	      	autoclose: true
	    });
    });
</script>