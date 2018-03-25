<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

	<div class="box">
		<!--BOX HEADER-->
	    <div class="box-header with-border">
	    	<h3 class="box-title">Product Category Information</h3>
	    </div>
	    <!--FORM-->
	    
		<div class="box-body">
			<form id="backToListButton" method="get" action="<c:url value="/product/category/listProductCategory" />"></form>
			<c:url var="post_url" value="/product/category/updateProductCategory" />
			<form:form id="updateProductCategoryToDbForm" method="post" modelAttribute="categoryForm" action="${post_url }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<form:input path="categoryId" type="hidden" id="categoryId"/>
				<form:input path="deleteInd" type="hidden" id="deleteInd"/>
	 			<div class="row">
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Name</label>
						<div class="col-sm-10">
							<form:input path="categoryName" type="text" class="form-control"
						                    id="categoryName" placeholder="Enter name" />
							<form:errors path="categoryName" class="text-danger" />
						</div>
					</div>
				</div>
				<div class="row">		  
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Parent Category</label>
						<div class="col-sm-10">
							<div class="checkbox">
						  		<label><form:checkbox path="isParentBoolean" id="isParentBoolean" /></label>
		   						<form:errors path="isParentBoolean" class="text-danger" />
							</div>
						</div>
		  			</div>
		  		</div>
				<div class="row">		  
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Allow Display</label>
						<div class="col-sm-10">
							<div class="checkbox">
						  		<label><form:checkbox path="displayIndBoolean" id="displayIndBoolean" /></label>
		   						<form:errors path="displayIndBoolean" class="text-danger" />
							</div>
						</div>
		  			</div>
		  		</div>
		  		<div class="row">		  
		 			<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Image</label>
						<div class="col-sm-10">
							<tiles:insertAttribute name="upload"/>
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
							<button type="submit" class="btn btn-primary" form="updateProductCategoryToDbForm">Update</button>
					        <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
					    </div>
					</div>
				</div>
			</div>
	        <!-- /.box-body -->
		</div>
		<!-- /.BOX -->