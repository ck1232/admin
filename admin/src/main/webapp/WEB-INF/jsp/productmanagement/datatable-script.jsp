<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$(function () {
    $('#datatable1').DataTable({
      "paging": true,
      "responsive" : true,
      "ordering": true,
      "info": true,
      "ajax":{
          "url":'<tiles:getAsString name="data-list" />',
          "data":{
				<tiles:insertAttribute name="datatable-data" />
            	}
       },
	  "columns": [
	              <tiles:insertAttribute name="column-mapping" />
	            ],
      "order": [2, 'asc'],
      'rowCallback': function(row, data, dataIndex){
          // Get row ID
          var rowId = data[0];
          $(row).find('input[type="checkbox"]').prop('value', data.productId);
          $(row).find('button[name="editBtn"]').prop('value', data.productId);
          $(row).find('img[name="img"]').prop('src', "./getProductImage/"+data.productCode);
       }
    });
});
</script>