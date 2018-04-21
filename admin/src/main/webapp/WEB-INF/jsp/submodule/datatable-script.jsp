<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
	table = $('#datatable1').DataTable({
	  "dom": '<"top"<f><ip>>rt<"bottom"ip><"clear">',
      "paging": true,
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
      "order": [1, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          $(row).find('input[type="checkbox"]').prop('value', data.submoduleId);
          if(data.checked == "Y"){
          	$(row).find('input[type="checkbox"]').prop('checked', true);
          }else{
        	  $(row).find('input[type="checkbox"]').prop('checked', false);
          }
          $(row).find('button[name="editBtn"]').prop('value', data.submoduleId);
          $(row).find('button[name="viewBtn"]').prop('value', data.submoduleId);
          $(row).find('div[name="iconDiv"]').html(data.icon+" <i class='fa "+data.icon+"'></i>");
          
       }
    });

	initTableSearch();
});
</script>
