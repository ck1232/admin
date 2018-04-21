<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Submodule Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/admin/listPermission" />"></form>
		              <div class="box-body">
		              		<input type="hidden" id="id" value="${submodule.submoduleId}"/>
							<div class="col-sm-2">Name</div>
							<div class="col-sm-10">${submodule.name}</div>
							
						<br/><br/>
						<tiles:insertAttribute name = "datatable" />
		            	<!--/.FORM-->
		            		<div class="col-sm-2"></div>
							<div class="col-sm-10">
			                  <button name="doneBtn" type="submit" class="btn btn-primary" form="backToListButton"><i class="fa fa-remove"></i> Done
			            	</button>
							</div>
		              </div>
		              <!-- /.box-body -->
                </div>
             </div>
    	</div>
    </section>
    
    <div id="editModal" class="modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">Edit Permission Type Sequence</h3>
			</div>
			<form id="updatePermissionTypeSeqToDbForm" method="post" action="<c:url value="/admin/savePermissionTypeSeqToDb" />">
			<input type="hidden" id="permissionTypeid" name="permissionTypeid"/>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="modal-body">
				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 control-label">Permission type</label>
						<div class="col-sm-9" id="permissionTypeDiv"></div>
					  </div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 control-label">Sequence</label>
						<div class="col-sm-9">
							<input id="seqno" name="seqno" type="text" class="form-control"  />
						</div>
					  </div>
				</div>
			</div>
			</form>
			<div class="modal-footer">
				<button id="saveTypeSeqBtn" class="btn btn-primary" type="button" data-dismiss="modal" onclick="$('#updatePermissionTypeSeqToDbForm').submit();">Save changes</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->