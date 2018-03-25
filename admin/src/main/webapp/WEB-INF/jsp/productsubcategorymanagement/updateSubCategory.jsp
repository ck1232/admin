<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">SubCategory Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="post" action="<c:url value="/product/subcategory/listSubCategory" />">
                    	<input type="hidden" name="editBtn" value="${subcategoryForm.categoryId}"/>
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <c:url var = "post_url" value="/product/subcategory/updateSubCategoryToDb" />
                    <form:form id="updateSubCategoryToDbForm" method="post" modelAttribute="subcategoryForm" action="${post_url }">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              <div class="box-body">
		              		<form:input path="subCategoryId" type="hidden" id="subCategoryId"/>
		              		<form:input path="categoryId" type="hidden" id="categoryId"/>
		              		<form:input path="deleteInd" type="hidden" id="deleteInd"/>
		              		<div class="row">
							  	<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="col-sm-2 control-label">SubCategory Name</label>
									<div class="col-sm-10">
										<form:input path="name" type="text" class="form-control"
					                                id="name" placeholder="Enter submodule name" />
										<form:errors path="name" class="text-danger" />
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
			                  			<button type="submit" class="btn btn-primary" form="updateSubCategoryToDbForm">Update</button>
			                  			<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
						  		</div>
						  	</div>
		              </div>
		              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
    		