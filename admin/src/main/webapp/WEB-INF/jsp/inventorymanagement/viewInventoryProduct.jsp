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
                    	<h3 class="box-title">Product Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/inventory/listInventoryProduct" />"></form>
                    <!--/.FORM-->
		             	<div class="box-body">
			              	<div class="row">
			              		<div class="form-group">
				              		<div class="col-sm-2 control-label">Product name</div>
									<div class="col-sm-10">${product.productName}</div>
								</div>
			              	</div>
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Weight</div>
									<div class="col-sm-10">${product.weight}</div>
								</div>
							</div>
							<c:forEach items="${product.optionList}" var="option">
								<div class="row">
							  		<div class="form-group">
									    <div class="col-sm-2">${option.name}</div>
									    <div class="col-sm-10">${option.subOptionListComma}</div>
									</div>
								</div>
							</c:forEach>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Description</div>
								    <div class="col-sm-10">${product.productInfo}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Unit Price</div>
								    <div class="col-sm-10">${product.unitAmt}</div>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
              </div>
    	</div>
    	<tiles:insertAttribute name = "datatable1" />
    	<div class="row">
          	<div class="form-group">
           		<div class="col-sm-2"></div>
				<div class="col-sm-10">
                  	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
				</div>
			</div>
		</div>	
    </section>