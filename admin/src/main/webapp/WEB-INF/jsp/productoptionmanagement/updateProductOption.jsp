<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->

                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Product Option Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/option/listProductOption" />"></form>
                    <c:url var="post_url" value="/product/option/updateProductOptionToDb" />
                    <form:form id="updateProductOptionToDbForm" method="post" modelAttribute="productOptionForm" action="${post_url}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <form:input path="deleteInd" type="hidden" id="deleteInd"/>
                    <form:input path="productOptionId" type="hidden" id="productOptionId"/>
		              <div class="box-body">
		              		
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Name</label>
							<div class="col-sm-10">
								<form:input path="name" type="text" class="form-control"
				                                id="name" placeholder="Enter product option name" />
								<form:errors path="name" class="text-danger" />
							</div>
						  </div>
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-sm-2 control-label">Display</label>
							<div class="col-sm-10">
								<div class="checkbox">
							  		<label><form:checkbox path="displayInd" id="displayInd" value="Y"/></label>
							     	<form:errors path="displayInd" class="text-danger" />
								</div>
							</div>
						  </div>
						  
						<br/>
						<br/>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-10">
			                  <button type="submit" class="btn btn-primary" form="updateProductOptionToDbForm">Update
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