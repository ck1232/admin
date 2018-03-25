<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div style="text-align:center;"><input type="checkbox" name="checkboxId"/></div>'
},
{"data": "discountName"},
{"data": "discountType"},
{"data": "discountValue"},
{"data": "applyType"},
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "defaultContent": '<button name="viewBtn" class="btn btn-primary" type="submit" form="viewDiscountForm">View</button>'+
    '<button name="editBtn" class="btn btn-primary" type="submit" form="updateDiscountForm"><i class="fa fa-pencil"></i> Edit</button>'+
    '<button name="manageProductBtn" class="btn btn-primary" type="submit" form="datatableForm" formaction="<c:url value="/product/discount/manageProductDiscount" />"><i class="fa fa-pencil"></i> Manage Products</button>'
},