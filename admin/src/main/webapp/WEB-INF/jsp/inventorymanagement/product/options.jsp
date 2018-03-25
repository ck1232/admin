<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="margin">
	<div class="btn-grp">
		<button class="btn btn-primary pull-right" type="button" onclick="addProduct();"><i class="fa fa-user-plus"></i> Add Product</button>
	</div>
</div>

<script>
	function editBatchIntakeProduct(batchIntakeProduct){
		var hashCode = $(batchIntakeProduct).val();
		var data = {"hashCode" : hashCode};
		var editBatchIntakeProductAjax = $.ajax({
	  		  type: "POST",
	  		  url: "editBatchIntakeProduct",
	  		  data: JSON.stringify(data),
	  		  contentType:"application/json; charset=utf-8",
			  beforeSend: function( xhr ) {
				  xhr.setRequestHeader(header, token);
				}
	  		}).done(function(batchIntakePdt) {
				//ensure that a valid batchIntakeProduct is return
		  		if(batchIntakePdt.product.productname != ""){
			  		$('#editName').val(batchIntakePdt.product.productName);
			  		$('#editUnitPrice').val(batchIntakePdt.unitcost);
			  		$('#editQuantity').val(batchIntakePdt.qty);
			  		$('#hashCodeId').val(hashCode);
		  			$('#editOptionDiv').empty();
	  	  			 $.each(batchIntakePdt.subOptionList, function(){
		  	  			console.log(this.optionName + ":" + this.productSuboptionId);
		  	  				$("#editOptionDiv").append('<div class="row"><div class="form-group">'+
							'<label class="col-sm-3 control-label">'+this.optionName+'</label>'+
							'<div class="col-sm-9">'+
								'<input class="form-control col-sm-12" type="text" disabled value="'+this.name+'"/>'+
								'<input type="hidden" value="'+this.productSuboptionId+'">'+
							'</div></div></div>');
		  	  			/* $('#'+this.optionName).selectpicker('val', this.subOptionId);
		  	  			$('#'+this.optionName).selectpicker('refresh'); */
	  	  	  	  	 });
		  	  		$("#editModal").show();
			  	}else{
			  		intakeTable.ajax.reload();
				}
			});
	}

	/* ------------------------delete product------------------------ */

	function deleteBatchIntakeProduct(batchIntakeProduct){
		var hashCode = $(batchIntakeProduct).val();
		var data = {hashCode : hashCode};
		var deleteBatchIntakeProductAjax = $.ajax({
	  		  type: "POST",
	  		  url: "deleteInventoryProduct",
	  		  data: JSON.stringify(data),
	  		  contentType:"application/json; charset=utf-8",
			  beforeSend: function( xhr ) {
				  xhr.setRequestHeader(header, token);

				}
	  		}).done(function() {
				intakeTable.ajax.reload();
			}); 


		
	}



	
</script>