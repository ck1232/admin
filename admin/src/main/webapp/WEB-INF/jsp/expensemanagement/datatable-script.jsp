<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
$(function () {
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
                  $("thead input")[i-1].value = value;
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
          $(row).find('input[type="checkbox"]').prop('value', data.expenseId);
          $(row).find('button[name="viewBtn"]').prop('value', data.expenseId);
          if(data.status == "PAID"){
        	  $(row).find('div[name="statusDiv"]').css("display","none");
        	  $(row).find('div[name="editPayBtnDiv"]').css("display","none");
          }else{
        	 $(row).find('div[name="statusDiv"]').css("display","");
        	 $(row).find('div[name="editPayBtnDiv"]').css("display","");
        	 $(row).find('button[name="editBtn"]').prop('value', data.expenseId);
        	 $(row).find('button[name="payBtn"]').prop('value', data.expenseId);
          }

          if(data.expensetype == "China Stock Payment"){
        	  $(row).find('div[name="statusDiv"]').css("display","none");
          }else if(data.expensetype == "Stock(China)"){
        	  $(row).find('span[id="payBtnDiv"]').css("display","none");
          }
       }
    });

	initTableSearch();
	
	$('#datatableForm').submit(function(){
		$.each(table.rows('.selected').data(), function(index,value){
			var value1 = value.expenseId;
			//console.log(value1);
			$('<input />').attr('type', 'hidden')
	          .attr('name', "checkboxId")
	          .attr('value', value1)
	          .appendTo('#datatableForm');
		});
		
		return true;
	});
});

function checkboxClicked(checkbox){
	$(checkbox).closest("tr").toggleClass("selected");
};
</script>
