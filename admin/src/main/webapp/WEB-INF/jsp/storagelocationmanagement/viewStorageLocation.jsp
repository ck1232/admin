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
                    	<h3 class="box-title">Storage Location Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/storagelocation/listStorageLocation" />"></form>
                    <form id="updateStorageLocationForm" method="post" action="<c:url value="/storagelocation/updateStorageLocation" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="editBtn" value="${location.locationid}"/>
                    </form>
                    <!--/.FORM-->
		             	<div class="box-body">
			              	<div class="row">
			              		<div class="form-group">
				              		<div class="col-sm-2 control-label">Location name</div>
									<div class="col-sm-10">${location.locationname}</div>
								</div>
			              	</div>
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Address</div>
									<div class="col-sm-10">${location.address}</div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Postal Code</div>
								    <div class="col-sm-10">${location.postalcode}</div>
								</div>
							</div>			
							<br/>
							<br/>
		            	
			            	<div class="row">
			            		<div class="form-group">
				            		<div class="col-sm-2"></div>
									<div class="col-sm-10">
										<button type="submit" class="btn btn-primary" form ="updateStorageLocationForm">Edit</button>
					                  	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
								</div>
							</div>	
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
              </div>
    	</div>
    </section>