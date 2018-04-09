<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->
<form id="backToListButton" method="get" action="<c:url value="/admin/listUser" />"></form>
	<section class="content">
		<c:url var="post_url" value="/admin/saveRoleToUser" />
		<form:form id="saveRoleToUserForm" method="post" modelAttribute="user" action="${post_url }">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                    <div class="box-header with-border">
                    	<h3 class="box-title">Selected User</h3>
                    </div>
	              	<div class="box-body">
	              		<form:input path="userId" type="hidden" id="userId"/>
	              		<div class="row">
							<div class="col-sm-2">Username</div>
							<div class="col-sm-10">${user.userName}</div>
						</div>
				  	</div>
				</div>
				<!--BOX-->
			  	<div class="box">
					<div class="box-header with-border">
                    	<h3 class="box-title">All Roles</h3>
                    </div>
	             	<div class="box-body">
	             		<c:if test="${roleList != null}">
							<c:forEach items="${roleList}" var="role">
								<div class="row">
									<div class="col-sm-2"><input type="checkbox" name="userRole" value="${role.roleId}" <c:if test="${role.checked == 'Y'}">checked</c:if>></div>
									<div class="col-sm-10">${role.roleName}</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${roleList == null or empty roleList}">
							<div class="row">
								<div class="col-sm-12">No roles available</div>
							</div>
						</c:if>
						<br/><br/>
	            		<div class="row">
		            		<div class="col-sm-2"></div>
							<div class="col-sm-10">
								<button name="saveRoleBtn" class="btn btn-primary" type="submit" form="saveRoleToUserForm">Save</button>
							  	<button class="btn btn-default" type="submit" form="backToListButton"><i class="fa fa-remove"></i> Cancel</button>
							</div>
						</div>
					</div>
				</div>
				<!--BOX-->
			  	<div class="box">
					<div class="box-header with-border">
                    	<h3 class="box-title">All Submodules</h3>
                    </div>
	             	<div class="box-body">
	             		<c:if test="${submoduleList != null}">
							<c:forEach items="${submoduleList}" var="submodule">
								<div class="row">
									<div class="col-sm-5">${submodule.submoduleName}</div>
									<div class="col-sm-5">${submodule.permissionName}</div>
									<div class="col-sm-5"></div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${submoduleList == null or empty submoduleList}">
							<div class="row">
								<div class="col-sm-12">No submodules available</div>
							</div>
						</c:if>
					</div>
				</div>
            </div>
    	</div>
    	</form:form>
    </section>