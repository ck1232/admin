<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div style="text-align:center;"><input type="checkbox" name="checkboxId"/></div>'
},
{
    "className":      'details-control',
    "orderable":      false,
    "data":           null,
    "defaultContent": ''
},
{"data": "moduleName"},
{
    "className":      '',
    "orderable":      false,
    "data":           "",
    "defaultContent": '<div name="iconDiv"></div>'
},
{
    "className":      '',
    "orderable":      false,
    "data":           null,
    "defaultContent": '<button name="editBtn" class="btn btn-primary" type="submit" form="updateModuleForm"><i class="fa fa-pencil"></i> Edit</button>'+
    '<button name="editBtn" class="btn btn-primary" type="submit" form="datatableForm" formaction="<c:url value="/admin/listSubmodule" />"><i class="fa fa-pencil"></i> Manage Submodules</button>'
},