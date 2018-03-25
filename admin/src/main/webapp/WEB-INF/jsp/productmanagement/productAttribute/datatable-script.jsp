<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script>
var attributeTable= null;
$(function () {
	
	attributeTable = $('#attributeDatatable').DataTable({
    	"paging": false,
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
      'rowCallback': function(row, data, dataIndex){
          $(row).find('input[type="checkbox"]').prop('value', data.sku);
          if(data.hideByDefault ==true){
        	  $(row).find('input[type="checkbox"]').prop('disabled', true);
          }else{
        	  $(row).find('input[type="checkbox"]').prop('disabled', false);
        	  if(data.displayInd == true){
            	  $(row).find('input[type="checkbox"]').prop('checked', true);
              }else{
            	  $(row).find('input[type="checkbox"]').prop('checked', false);
              }
          }
          
       },
       "createdRow": function ( row, data, index ) {
           $str = "";
           $subOptionList = data.subOptionList;
           $.each($subOptionList, function(index, value){
        	   /* console.log(value.name); */
        	   if(value.displayInd == 'N'){
        		   $str = $str+"<strike>"+value.name+"</strike>";
        	   }else{
        		   $str = $str+value.name;
        	   }
        	   $str = $str + ",";
           });
           
           if($str.length > 0){
        	   $str = $str.substr(0,$str.length-1);
           }
           
           $('td', row).eq(2).html($str);
       }
    });
    
    /* $('#datatable1').on( 'row-reorder', function ( e, diff, edit ) {
        var seqArray = [];
        for ( var i=0, ien=diff.length ; i<ien ; i++ ) {
            var rowData = table.row( diff[i].node ).data();
 			var seq = {
 				optionName : rowData[1],
 				sequence : diff[i].newData
 		 			};
 			seqArray.push(seq);
        }

        var sortAjax = $.ajax({
    		  type: "POST",
    		  url: "sortOption",
    		  data: JSON.stringify(seqArray),
    		  contentType:"application/json; charset=utf-8",
  		  beforeSend: function( xhr ) {
  			  xhr.setRequestHeader(header, token);

  			}
    		}).done(function() {});
    }); */

});
</script>
