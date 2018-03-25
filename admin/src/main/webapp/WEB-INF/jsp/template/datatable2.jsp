<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- DataTables -->
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
<link type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" rel="stylesheet"/> 
<link type="text/css" href="https://cdn.datatables.net/select/1.2.0/css/select.dataTables.min.css" rel="stylesheet"/> 
<script type="text/javascript" src="https://cdn.datatables.net/select/1.2.0/js/dataTables.select.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<!-- common -->
<tiles:insertAttribute name="datatable-script2"/>

<div class="row">
	<div class="col-xs-12">
    	<div class="box">
            <div class="box-header">
              <h3 class="box-title"><tiles:insertAttribute name = "table-title2" /></h3>
              <tiles:insertAttribute name = "options2" />
            </div>
            <!-- /.box-header -->
            <div class="box-body">
            <form:form id="datatableForm2" method="post">
            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <table id="datatable2" class="table table-bordered table-hover">
                <thead>
                	<tiles:insertAttribute name="column-header2" />
                </thead>
                <tfoot>
                  <tiles:insertAttribute name="column-header2" />
                </tfoot>
              </table>
              <tiles:insertAttribute name = "datatable-options2" />
              </form:form>
            </div>
            <!-- /.box-body -->
	    </div>
	    <!-- /.box -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->
      
