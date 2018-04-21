<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div style="text-align:center;"><input type="checkbox" name="checkboxId"/></div>'
},
{"data": "userName"},
{"data": "name"},
{"data": "emailAddress"},
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "defaultContent": '<button name="viewBtn" class="btn btn-primary" type="submit" form="viewUserForm">View</button>'+
    '<button name="editBtn" class="btn btn-primary" type="submit" form="updateUserForm"><i class="fa fa-pencil"></i> Edit</button>'+
    '<button name="assignRoleBtn" class="btn btn-primary" type="submit" form="datatableForm" formaction="<c:url value="/admin/assignRole" />">Assign Role</button>'
},