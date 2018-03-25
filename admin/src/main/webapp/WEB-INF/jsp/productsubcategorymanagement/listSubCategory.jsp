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
                    	<h3 class="box-title">SubCategory Information</h3>
                    </div>
                    <!--FORM-->
                    
                    <form id="backToListButton" method="get" action="<c:url value="/product/category/listProductCategory" />"></form>
                    <c:url var = "post_url" value="/product/subcategory/updateSubCategoryToDb" />
                    <form:form id="updateSubCategoryToDbForm" method="post" modelAttribute="category" action="${post_url}">
		              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		              <div class="box-body">
		              		<form:input path="categoryId" type="hidden" id="categoryId"/>
							<div class="col-sm-2">Category Name</div>
							<div class="col-sm-10">${category.categoryName}</div>
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