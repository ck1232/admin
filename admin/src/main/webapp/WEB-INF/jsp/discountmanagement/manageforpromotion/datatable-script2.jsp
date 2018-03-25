<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
	var rows_selected = [];
    $('#datatable2').DataTable({
        "paging": true,
        "responsive" : true,
        "lengthChange": false,
        "searching": false,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "sScrollX": "100%",
        "sScrollXInner": "110%",
        "ajax":{
            "url":'<tiles:getAsString name="data-list2" />',
            "data":{
  				<tiles:insertAttribute name="datatable-data2" />
              	}
         },
  	  "columns": [
  	              <tiles:insertAttribute name="column-mapping2" />
  	            ],
        "order": [1, 'asc'],
        'rowCallback': function(row, data, dataIndex){
            // Get row ID
            var rowId = data[0];
            $(row).find('input[type="checkbox"]').prop('value', data.discountId);
            $(row).find('button[name="editBtn"]').prop('value', data.discountId);
            $(row).find('button[name="viewBtn"]').prop('value', data.discountId);
         }
      });
});
</script>
