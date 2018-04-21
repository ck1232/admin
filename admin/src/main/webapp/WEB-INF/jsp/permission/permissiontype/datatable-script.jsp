<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$(function () {
	
	var rows_selected = [];
	var selectedRoleId = 0;
    table = $('#datatable1').DataTable({
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
      "order": [1, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          // Get row ID
          var rowId = data[0];
          $(row).find('button[name="editBtn"]').prop('value', data.typeId);
          $(row).find('button[name="editTypeSeqBtn"]').click(function(){editPermissionTypeSeq(data)});
          $(row).find('input[type="checkbox"]').prop('value', data.typeId);
       }
    });
    initTableSearch();
    $('#saveTypeSeqBtn').on('click', function() {
       	$('#saveTypeSeqBtn').submit();
    });
});

function editPermissionTypeSeq(data) {
	$('#permissionTypeid').prop('value', data.typeId);
	$('#permissionTypeDiv').html(data.permissionType);
	$("#seqno").prop('value', data.seqNum);
    $('#editModal').modal('show');

}

</script>
