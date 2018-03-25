<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->
	<style>
		.product_image{
			border-width: 1px;
			border-color: black;
			border-style: solid;
			border-radius: 5px;
			height: 250px;
			margin-top: 10px;
			padding: 5px;
			text-align: center;
		}
	</style>
	<script type="text/javascript">
	Dropzone.options.dZUpload = {
    	    init: function() {
    	    	var myDropzone = this;
    	    	//set preload image
    	    	var getPreUploadImageAjax = $.ajax({
          		  type: "POST",
          		  url: "getPreUploadImage",
          		  dataType: 'json',
         			  beforeSend: function( xhr ) {
         				  xhr.setRequestHeader(header, token);
         				}
          		}).done(function(data) {
              		$.each(data, function(i, fileMeta){
              			var image = { name: fileMeta.fileName, size: fileMeta.fileSize};
              			var imageUrl = "<c:out value="${pageContext.request.contextPath}" />/images/product/"+fileMeta.fileName;
              			//console.log(imageUrl);
              			myDropzone.emit("addedfile", image);
              			myDropzone.createThumbnailFromUrl(image, imageUrl);
              			myDropzone.emit("complete", image);
                  	});
	    		});

	    		this.on("removedfile", function(file){
		    		//console.log(file);
		    		$(document).find(file.previewElement).remove();
		    		var data = {"fileName": file.name};
	    			var removePreUploadImageAjax = $.ajax({
	            		  type: "POST",
	            		  url: "removeUploadImage",
	            		  dataType: 'json',
	            		  data: JSON.stringify(data),
	           			  beforeSend: function( xhr ) {
	           				  xhr.setRequestHeader(header, token);
	           				}
	            		}).done(function(data) {
	            			
	  	    			});
		    	});
  			}
	    };
	$(function(){
		$productId = $('#productId').val().trim();
		if($productId.trim() != ""){
			$('#optionBtnDiv').addClass("hidden");
		}
		$(window).keydown(function(event){
		    if(event.keyCode == 13 && $(event.target).is("input")) {
		      event.preventDefault();
		      return false;
		    }
		  });
		
		// instantiate the uploader
		var sortableList = $("#dZUpload");
		var uploadImageOrderList = [];

		function getSortOrder(){
			var listElements = $("#dZUpload").find(".dz-image img");
			//console.log(listElements);
			uploadImageOrderList = [];

			for(var i=0;i<listElements.length;i++){
				var alt = $(listElements[i]).attr("alt");						
				uploadImageOrderList.push(alt);
			}
			return uploadImageOrderList; 
		}
		var sortEventHandler = function(event, ui){
			var uploadImageOrderList = getSortOrder();
			var orderAjax = $.ajax({
		  		  type: "POST",
		  		  url: "sortImage",
		  		  data: JSON.stringify(uploadImageOrderList),
		  		  contentType:"application/json; charset=utf-8",
				  beforeSend: function( xhr ) {
					  xhr.setRequestHeader(header, token);

					}
		  		}).done(function() {});
		};

		sortableList.sortable({
		    stop: sortEventHandler
		});

		// You can also set the event handler on an already existing Sortable widget this way:

		sortableList.on("sortchange", sortEventHandler);
		
	  Dropzone.autoDiscover = false;
	  
	    var dz = $("#dZUpload").dropzone({
	        url: "uploadImage",
	        headers: {
	            header: token
	        },
	        param:{header:token},
	        addRemoveLinks: true,
	        sending:function (file, xhr, formData){
	        	xhr.setRequestHeader(header, token);
			},
	        success: function (file, response) {
	            var imgName = response;
	            file.previewElement.classList.add("dz-success");
	            //console.log("Successfully uploaded :" + imgName);
	        },
	        error: function (file, response) {
	            file.previewElement.classList.add("dz-error");
	        },
	        removedfile: function (file) {
	        	//console.log(file.previewElement);
	        	$(document).find(file.previewElement).remove();
	        	var deleteAjax = $.ajax({
	        		  type: "POST",
	        		  url: "removeUploadImage",
	        		  data: {fileName:file.name},
	        		  dataType: 'json',
        			  beforeSend: function( xhr ) {
        				  xhr.setRequestHeader(header, token);
        				}
	        		}).done(function() {
       				    //alert( "success" );
     				  	});
	        		
		        	//console.log("success");	
				}
		      	
	        
	    });
	    
	    
	    
		CKEDITOR.replace('productInfoEditor');

		$(".dropzone").sortable({
	          items:'.dz-preview',
	          cursor: 'move',
	          opacity: 0.5,
	          containment: '.dropzone',
	          distance: 20,
	          tolerance: 'pointer'
	      });
	});
		
	</script>
	<!-- Our main JS file -->
	
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/product/listProduct" />" accept-charset="UTF-8"></form>
                    <c:url var="post_url" value="/product/product/saveProduct" />
                    <form:form id="productForm" method="post"  modelAttribute="productForm" action="${post_url}" acceptCharset="UTF-8" >
                    	<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<form:input path="productId" type="hidden" class="form-control" id="productId"/>
				              	<!-- basic info -->
				              	<div class="form-group box">
				              		<div class="box-header with-border">
				              			<h3 class="box-title">Basic Info</h3>
								        <div class="box-tools pull-right">
								            <button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
								              <i class="fa fa-minus"></i></button>
								        </div>
				              		</div>
				              		<div class="box-body">
							          <div class="col-sm-12 col-md-12 col-lg-7">
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Name:</label>
											<div class="col-sm-10 input-group">
												<form:input path="productName" type="text" class="form-control"
								                                id="productName" placeholder="Enter product name" cssStyle="width:100%;"/>
												<form:errors path="productName" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              		
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Code:</label>
											<div class="col-sm-10 input-group">
												<form:input readonly="true" path="productCode" class="form-control" 
								                                id="productCode"/>
												<form:errors path="productCode" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              		
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Category:</label>
											<div class="col-sm-10 input-group">
												<form:select path="subCategoryId" type="text" class="form-control col-sm-12" id="subcategory" >
													<form:option value="0">No Category</form:option>
								                	<c:forEach items="${categoryList}" var="category">
								                		<c:if test="${category.isParentBoolean == true}">
								                			<optgroup label="${category.categoryName}">
								                				<c:forEach items="${category.subcategoryList}" var="sub">
								                					<form:option value="${sub.subCategoryId}">${sub.name}</form:option>
								                				</c:forEach>
								                			</optgroup>
								                		</c:if>
								                		<c:if test="${category.isParentBoolean == false}">
								                			<form:option value="${category.subcategoryList.get(0).subCategoryId}">${category.subcategoryList.get(0).name}</form:option>
								                		</c:if>
								                	</c:forEach>
								                </form:select>
												<form:errors path="subCategory" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              		
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Price:</label>
											<div class="col-sm-10 input-group">
												<span class="input-group-addon">$</span>
												<form:input path="unitAmt" type="text" class="form-control"
								                                id="unitAmt" placeholder="" />
								                <span class="input-group-addon">.00</span>
												<form:errors path="unitAmt" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              		
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Weight:</label>
											<div class="col-sm-10 input-group">
												<form:input path="weight" type="text" class="form-control"
								                                id="weight" placeholder="Enter weight" />
								                <span class="input-group-addon">gram</span>
												<form:errors path="weight" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              		
				              		<div class="row">
					              		<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="col-sm-2 control-label">Tags:</label>
											<div class="col-sm-10 input-group">
												<form:select path="tags" type="text" class="form-control" data-role="tagsinput"
								                                id="tags" placeholder="Enter tags" multiple="true">
								                                
								                 	<c:forEach items="${productForm.tags}" var="tag">
								                		<form:option value="${tag.name}">${tag.name}</form:option>
								                	</c:forEach>               
								                </form:select>
												<form:errors path="tags" class="text-danger" />
											</div>
									  	</div>
				              		</div>
				              	</div>
							        </div>
				              	</div>
				              
				              <!-- product description -->
				              <div class="form-group box">
				              	<div class="box-header with-border">
						        	<h3 class="box-title">Product Description</h3>
						          	<div class="box-tools pull-right">
						            	<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
						              		<i class="fa fa-minus"></i>
						              	</button>
						          	</div>
						      	</div>
						      	<div class="box-body">
						      		<form:textarea path="productInfo" id="productInfoEditor" name="productInfo" rows="10" cols="80" />
				              		<form:errors path="productInfo" class="text-danger" />
						      	</div>
				              </div>
				              
				              <!-- product images -->
				              <div class="form-group box">
				              	<div class="box-header with-border">
						        	<h3 class="box-title">Product Images</h3>
						          	<div class="box-tools pull-right">
						            	<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
						              		<i class="fa fa-minus"></i>
						              	</button>
						          	</div>
						      	</div>
						      	<div class="box-body">
						      		<div id="dZUpload" class="dropzone">
										<div class="dz-default dz-message"></div>
									</div>
						      	</div>
				              </div>
				              
				              <!-- product images -->
				              <div class="form-group box">
				              	<div class="box-header with-border">
						        	<h3 class="box-title">Product Option</h3>
						          	<div class="box-tools pull-right">
						            	<button type="button" class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse">
						              		<i class="fa fa-minus"></i>
						              	</button>
						          	</div>
						      	</div>
						      	<div class="box-body">
						      		<tiles:insertAttribute name = "options" />
						      		<tiles:insertAttribute name = "attribute" />
						      	</div>
				              </div>
				             <div class="row">
				              		<button type="submit" class="btn btn-default pull-right" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									<tiles:insertAttribute name = "button" />
				             </div>
		            </form:form>
		            <!--/.FORM-->
    		</div>
    	</div>
    </section>