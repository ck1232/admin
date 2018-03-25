<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{"data": "chequeNum"},
{	"data": "chequeDate",
	"render": function (data) {
                 var date = new Date(data);
        		var month = date.getMonth();
        		var monthNames = [
							    "January", "February", "March",
							    "April", "May", "June", "July",
							    "August", "September", "October",
							    "November", "December"
							  ];
        		return date.getDate() + " " + monthNames[month] + " " + date.getFullYear();
             }
},
{"data": "chequeAmtString"},
{"data": "bounceChequeInd"},
{"data": "paidFor"},
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "width":		  "200px",
    "defaultContent": '<div class="outer">'+
    '<div class="inner"><button name="viewBtn" class="btn btn-primary" type="submit" form="viewChequeForm">View</button></div>' +
    '<div name="editBtnDiv" class="inner"><button name="editBtn" class="btn btn-primary" type="submit" form="updateChequeForm"><i class="fa fa-pencil"></i> Edit</button></div>' +
    '<div name="bounceBtnDiv" class="inner"><button name="bounceBtn" class="btn btn-primary" type="submit" form="bounceChequeForm">Bounce</button></div>'+
    '</div>'
},