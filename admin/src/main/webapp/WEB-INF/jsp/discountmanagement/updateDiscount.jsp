<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->

	<div class="box">
		<!--BOX HEADER-->
	    <div class="box-header with-border">
	    	<h3 class="box-title">Discount Information</h3>
	    </div>
	    <!--FORM-->
	    
		<div class="box-body">
			<form id="backToListButton" method="get" action="<c:url value="/product/discount/listDiscount" />"></form>
			<c:url var="post_url" value="/product/discount/updateDiscountToDb" />
			<form:form id="updateDiscountToDbForm" method="post" modelAttribute="discountForm" action="${post_url }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<form:input path="discountId" type="hidden" id="discountId"/>
				<form:input path="deleteInd" type="hidden" id="deleteInd"/>
				<div class="row">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Name</label>
						<div class="col-sm-10">
							<form:input path="discountName" type="text" class="form-control"
		                    				id="discountName" placeholder="Enter discount name" />
							<form:errors path="discountName" class="text-danger" />
						</div>
		 			</div>
	 			</div>
	 			<div class="row">
				  	<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Type</label>
						<div class="col-sm-10">
							<form:select path="discountType" class="selectpicker" id="discountType" name ="discountType">
								<c:forEach items="${discTypeList}" var="discType">
							    	<option value = "${discType}" <c:if test="${discType == discountForm.discountType }">selected</c:if>>
							    		${discType}
							    	</option>
							    </c:forEach>
							</form:select>
						</div>
				  	</div>
				</div>
				<div class="row">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Value</label>
						<div class="col-sm-10">
							<form:input path="discountValue" class="form-control"
						                    id="discountValue" placeholder="Enter discount value" />
							<form:errors path="discountValue" class="text-danger" />
						</div>	
					</div>
				</div>
				<div class="row">
				  	<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-2 control-label">Apply Type</label>
						<div class="col-sm-10">
							<form:select path="applyType" class="selectpicker" id="applyType" name ="applyType">
								<c:forEach items="${applyTypeList}" var="appType">
							    	<option value = "${appType}" <c:if test="${appType == discountForm.applyType }">selected</c:if>>
							    		${appType}
							    	</option>
							    </c:forEach>
							</form:select>
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
							<button type="submit" class="btn btn-primary" form="updateDiscountToDbForm">Update</button>
					        <button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
					    </div>
					</div>
				</div>
			</div>
	        <!-- /.box-body -->
		</div>
		<!-- /.BOX -->
		