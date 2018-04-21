<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div name="statusDiv" style="text-align:center;"><input type="checkbox" name="checkboxId1" onchange="checkboxClicked(this);"/></div>'
},
{"data": "invoiceId"},
{"data": "messenger"},
{"data": "totalAmtString"},
{"data": "status"},
{	"data": "invoiceDate",
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
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "width":		  "100px",
    "defaultContent": '<div class ="outer">'+
    '<div class="inner"><button name="viewBtn" class="btn btn-primary" type="submit" form="viewInvoiceForm">View</button></div>'+
    '<div name="editBtnDiv" class="inner"><button name="editBtn" class="btn btn-primary" type="submit" form="updateGrantForm">Edit</button></div>'+
    '<div name="payBtnDiv" class="inner"><button name="payBtn" class="btn btn-primary" type="submit" form="payInvoiceForm">Pay</button></div>'+
    '</div>'
},