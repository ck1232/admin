<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
	var rows_selected = [];
	$.fn.dataTable.moment( 'D MMMM YYYY' );
	table = $('#datatable1').DataTable({
	  "dom": '<"top"<f><ip>>rt<"bottom"ip><"clear">',
      "paging": true,
      "bStateSave": true,
      "fnInitComplete": function(oSettings, json) {
          var cols = oSettings.aoPreSearchCols;
          for (var i = 0; i < cols.length; i++) {
              var value = cols[i].sSearch;
              if (value.length > 0) {
                  $("thead input")[i].value = value;
              }
          }
      },
      "stateDuration": -1,
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
      "order": [1, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          // Get row ID
          var rowId = data[0];
          $(row).find('button[name="viewBtn"]').prop('value', data.chequeId);
          if(data.bounceChequeInd == 'Y'){
        	  $(row).find('div[name="editBtnDiv"]').css("display","none");
        	  $(row).find('div[name="bounceBtnDiv"]').css("display","none");
          }else{
        	 $(row).find('div[name="editBtnDiv"]').css("display","");
        	 $(row).find('div[name="bounceBtnDiv"]').css("display","");
        	 $(row).find('button[name="editBtn"]').prop('value', data.chequeId);
             $(row).find('button[name="bounceBtn"]').prop('value', data.chequeId);
          }
       }
    });

	initTableSearch();
});
</script>
