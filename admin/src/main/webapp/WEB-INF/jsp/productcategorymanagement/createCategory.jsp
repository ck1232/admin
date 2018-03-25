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
                    	<h3 class="box-title">Product Category Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/category/listProductCategory" />"></form>
                    <c:url var="post_url" value="/product/category/createProductCategory" />
                    <form:form id="createCategoryForm" method="post" modelAttribute="categoryForm" action="${post_url }">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		             	<div class="box-body">
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
							<br/>
							<br/>
		            	
		            		<div class="row">
								<div class="form-group">
									<label class="col-sm-2 control-label"></label>
										<div class="col-sm-10">
											<button type="submit" class="btn btn-primary" form ="createCategoryForm">Add</button>
				                  			<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
										</div>
								</div>
							</div>
		              	</div>
		        	</form:form>
		            <!--/.FORM-->
                </div>
                <!-- /.box-body -->
    		</div>
    	</div>
    </section>