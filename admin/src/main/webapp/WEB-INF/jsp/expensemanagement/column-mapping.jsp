<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div name="statusDiv" style="text-align:center;"><input type="checkbox" name="checkboxId" onchange="checkboxClicked(this);"/></div>'
},
{	"data": "expenseDate",
	"targets": 1,
	"render": function (data) {
                 return (moment(data).format("D MMMM YYYY"));
             }
},
{"data": "expensetype"},
{"data": "description"},
{"data": "totalAmtString"},
{"data": "supplier"},
{"data": "status"},
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "width":		  "200px",
    "defaultContent": '<div class="outer">'+
    '<div class="inner"><button name="viewBtn" class="btn btn-primary" type="submit" form="viewExpenseForm">View</button></div>'+
    '<div name="payBtnDiv" class="inner"><button name="editBtn" class="btn btn-primary" type="submit" form="updateExpenseForm"><i class="fa fa-pencil"></i> Edit</button>' +
    '<button name="payBtn" class="btn btn-primary" type="submit" form="payExpenseForm">Pay</button></div>'+
    '</div>'
},