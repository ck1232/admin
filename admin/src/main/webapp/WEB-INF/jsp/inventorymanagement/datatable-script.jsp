<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
	var rows_selected = [];
	table = $('#datatable1').DataTable({
      "paging": true,
      "pageLength": 10,
      "responsive" : true,
      "lengthChange": false,
      "searching": false,
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
      "order": [1, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          // Get row ID
          var rowId = data[0];
          $(row).find('input[type="checkbox"]').prop('value', data.productId);
          $(row).find('button[name="editBtn"]').prop('value', data.productId);
          $(row).find('button[name="viewBtn"]').prop('value', data.productId);
       }
    });

	initTableSearch();
});
</script>
