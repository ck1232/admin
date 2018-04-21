<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Submodule Information</h3>
                    </div>
                    <!--FORM-->
                    
                    <form id="backToListButton" method="get" action="<c:url value="/admin/listModule" />"></form>
                    <c:url var = "post_url" value="/admin/updateModuleToDb" />
                    <form:form id="updateModuleToDbForm" method="post" modelAttribute="module" action="${post_url}">
		              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              <div class="box-body">
		              		<form:input path="moduleId" type="hidden" id="moduleId"/>
							<div class="col-sm-2">Name</div>
							<div class="col-sm-10">${module.moduleName}</div>
							
						    <div class="col-sm-2">Icon</div>
						    <div class="col-sm-10">${module.icon} <i class="fa ${module.icon}"></i></div>
						<br/><br/>
						
						<tiles:insertAttribute name = "datatable" />
						
		            	<!--/.FORM-->
		            		<div class="col-sm-2"></div>
							<div class="col-sm-10">
			                  <button type="submit" class="btn btn-primary" form="backToListButton"><i class="fa fa-remove"></i> Done
			            	</button>
							</div>
		              </div>
		              <!-- /.box-body -->
		            </form:form>
		            <!--/.FORM-->
                </div>
             </div>
    	</div>
    </section>