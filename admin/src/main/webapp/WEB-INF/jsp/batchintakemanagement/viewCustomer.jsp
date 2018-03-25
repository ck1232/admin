<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Basic Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/customer/listCustomer" />"></form>
                    <form id="updateCustomerForm" method="post" action="<c:url value="/customer/updateCustomer" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="editBtn" value="${customer.customerid}"/>
                    </form>
                    <form id="deleteAddressForm" method="post" action="<c:url value="/customer/deleteAddress" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <form id="setAddressDefaultForm" method="post" action="<c:url value="/customer/setAddressDefault" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                    <form id="isactiveCustomerForm" method="post" action="<c:url value="/customer/activateOrDeactivateCustomer" />">
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    	<input type="hidden" name="customerId" value="${customer.customerid}"/>
                    </form>
                    <!--/.FORM-->
		             	<div class="box-body">
			              	<div class="row">
			              		<div class="form-group">
				              		<div class="col-sm-2 control-label">Name</div>
									<div class="col-sm-10">${customer.name}</div>
								</div>
			              	</div>
							<div class="row">
						  		<div class="form-group">
									<div class="col-sm-2">Date of Birth</div>
									<div class="col-sm-10"><fmt:formatDate value='${customer.dob}' type='date' pattern='dd/MM/yyyy'/></div>
								</div>
							</div>
							<div class="row">
						  		<div class="form-group">
								    <div class="col-sm-2">Contact No.</div>
								    <div class="col-sm-10">${contactNo}</div>
								</div>
							</div>			
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Email Address</div>
									<div class="col-sm-10">${customer.emailaddress}</div>
								</div>
							</div>	
							<div class="row">
						  		<div class="form-group">	  
									<div class="col-sm-2">Active</div>
									<div class="col-sm-10">${customer.isactive}</div>
								</div>
							</div>	
							<br/>
							<br/>
		            	
			            	<div class="row">
			            		<div class="form-group">
				            		<div class="col-sm-2"></div>
									<div class="col-sm-10">
										<button type="submit" class="btn btn-primary" form ="updateCustomerForm">Edit</button>
					                  	<button type="submit" class="btn btn-primary" form ="isactiveCustomerForm">
					                  		<c:if test="${customer.isactive == true}">Deactivate</c:if>
					                  		<c:if test="${customer.isactive == false}">Activate</c:if>
				                  		</button>
					                  	<button type="submit" class="btn btn-default" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
									</div>
								</div>
							</div>	
						</div>
						<!-- /.box-body -->
		      		</div>
		      		<!-- /.BOX -->
		      		
		      		<div class="box">
	                	<!--BOX HEADER-->
	                    <div class="box-header with-border">
	                    	<h3 class="box-title">Address Details</h3>
	                    </div>
				            <div class="box-body no-padding">
				              	<table class="table table-striped">
				              		<c:if test="${addressList.size() < 10}">
					              		<tr>
					              			<td colspan="6"><button class="btn btn-primary pull-right" type="submit" onclick="addAddress('${customer.customerid}')"><i class="fa fa-user-plus"></i> Add</button></td>
					              		</tr>
				              		</c:if>
				              		<c:if test="${addressList.size() > 0}">
					                	<tr>
					                		<th style="width: 20%">Recipient</th>
					                  		<th style="width: 32%">Address</th>
					                  		<th style="width: 12%">Postal Code</th>
					                  		<th style="width: 12%">Contact No.</th>
					                  		<th style="width: 12%">Action</th>
					                  		<th style="width: 12%"></th>
					                	</tr>
					                	<c:forEach items="${addressList}" var="address">
											<tr>
												<td>${address.recipientname}</td>
							          			<td>${address.address}</td>
							          			<td>${address.postalcode}</td>
							          			<td>${address.contactnumber}</td>
							          			<td>
							          				<button name="editBtn" value="${address.addressid}" class="btn btn-primary" onclick="editAddress('${address.addressid}','${address.recipientname}','${address.address}','${address.country}','${address.postalcode}','${address.contactnumber}')">Edit</button>
							          				<button name="deleteBtn" value="${address.addressid}" class="btn btn-primary" form ="deleteAddressForm">Delete</button>
							          			</td>
							          			<td>
							          				<c:if test="${address.defaultind == 'N'}">
							          					<button name="setDefaultBtn" value="${address.addressid}" class="btn btn-primary" type="submit" form="setAddressDefaultForm">Default</button>
							          				</c:if>
							          				<c:if test="${address.defaultind == 'Y'}">
							          					Default
							          				</c:if>
							          			</td>
						          			</tr>
							        	</c:forEach>
						        	</c:if>
						        	<c:if test="${addressList.size() == 0}">
					        			<tr>
					        				<td colspan="6">No records found.</td>
					        			</tr>
				        			</c:if>
							        
				              	</table>
				            </div>
				     		<!-- /.box-body -->
		      		<!-- /.BOX -->
              		</div>
              	</div>
    	</div>
    </section>
    
<div id="addModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">Add Address</h3>
			</div>
			<form id="createCustomerAddressForm" method="post" action="<c:url value="/customer/createCustomerAddress" />">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" id="customerid1" name="customerid"/>
				<div class="modal-body">
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Recipient</label>
							<div class="col-sm-9">
								<input id="recipientname1" name="recipientname" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Address</label>
							<div class="col-sm-9">
								<input id="address1" name="address" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Country</label>
							<div class="col-sm-9">
								<input id="country1" name="country" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Postal Code</label>
							<div class="col-sm-9">
								<input id="postalcode1" name="postalcode" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Contact No.</label>
							<div class="col-sm-9">
								<input id="contactnumber1" name="contactnumber" type="text" class="form-control"  />
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
				<button id="addAddressBtn" class="btn btn-primary" type="button" data-dismiss="modal" onclick="$('#createCustomerAddressForm').submit();">Save changes</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
    
<div id="editModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">Edit Address</h3>
			</div>
			<form id="updateCustomerAddressToDbForm" method="post" action="<c:url value="/customer/saveCustomerAddressToDb" />">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" id="addressid" name="addressid" value="${addressForm.addressid}"/>
				<div class="modal-body">
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Recipient</label>
							<div class="col-sm-9">
								<input id="recipientname" name="recipientname" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Address</label>
							<div class="col-sm-9">
								<input id="address" name="address" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Country</label>
							<div class="col-sm-9">
								<input id="country" name="country" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Postal Code</label>
							<div class="col-sm-9">
								<input id="postalcode" name="postalcode" type="text" class="form-control"  />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-sm-3 control-label">Contact No.</label>
							<div class="col-sm-9">
								<input id="contactnumber" name="contactnumber" type="text" class="form-control"  />
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
				<button id="saveAddressBtn" class="btn btn-primary" type="button" data-dismiss="modal" onclick="$('#updateCustomerAddressToDbForm').submit();">Save changes</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
function addAddress(customerid) {
	$('#customerid1').prop('value', customerid);
    $('#addModal').modal('show');

}

function editAddress(id, recipientname, address, country, postalcode, contactnumber) {
	$('#addressid').prop('value', id);
	$("#recipientname").prop('value', recipientname);
	$("#address").prop('value', address);
	$("#country").prop('value', country);
	$("#postalcode").prop('value', postalcode);
	$("#contactnumber").prop('value', contactnumber);
    $('#editModal').modal('show');

}
</script>