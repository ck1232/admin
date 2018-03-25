<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- DataTables -->

<link type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet"/> 
<link type="text/css" href="https://cdn.datatables.net/select/1.2.0/css/select.dataTables.min.css" rel="stylesheet"/> 
<link type="text/css" href="https://cdn.datatables.net/rowreorder/1.1.2/css/rowReorder.dataTables.min.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script> 
<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/select/1.2.0/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/rowreorder/1.1.2/js/dataTables.rowReorder.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
<script src="https://cdn.datatables.net/plug-ins/1.10.16/sorting/datetime-moment.js"></script>
<!-- common -->
<tiles:insertAttribute name="datatable-script"/>

<div class="row">
	<div class="col-xs-12">
    	<div class="box">
            <div class="box-header">
              <h3 class="box-title"><tiles:insertAttribute name = "table-title" /></h3>
              <tiles:insertAttribute name = "options" />
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
            <form:form id="datatableForm" method="post" acceptCharset="UTF-8">
            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <table id="datatable1" class="table table-bordered table-hover display select">
                <thead>
                	<tiles:insertAttribute name="column-header" />
                </thead>
                <tfoot>
                  <tiles:insertAttribute name="column-header" />
                </tfoot>
              </table>
              <tiles:insertAttribute name = "datatable-options" />
              </form:form>
            </div>
            </div>
            <!-- /.box-body -->
	    </div>
	    <!-- /.box -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
      
