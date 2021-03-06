<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
	var rows_selected = [];
	table = $('#datatable1').DataTable({
	  "dom": '<"top"<f><ip>>rt<"bottom"ip><"clear">',
      "paging": true,
      "pageLength": 30,
      "responsive" : true,
      "lengthChange": false,
      "searching": true,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "sScrollX": "100%",
      "sScrollXInner": "110%",
      "ajax":{
          "url":'<tiles:getAsString name="data-list" />',
          "data":{
				<tiles:insertAttribute name="datatable-data" />
            	}
       },
	  "columns": [
	              <tiles:insertAttribute name="column-mapping" />
	            ],
      "order": [0, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          // Get row ID
          var rowId = data[0];
          $(row).find('button[name="editBtn"]').prop('value', data.submoduleId);
          $(row).find('button[name="loadEditPermissionTypeBtn"]').prop('value', data.submoduleId);
       }
    });

	initTableSearch();
});
</script>
