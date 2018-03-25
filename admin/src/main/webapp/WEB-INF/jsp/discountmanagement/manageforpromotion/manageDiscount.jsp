<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Content Wrapper. Contains page content -->
    <section class="content">
    	<div class="row">
    		<div class="col-md-12">
    			<!--BOX-->
                <div class="box">
                	<!--BOX HEADER-->
                    <div class="box-header with-border">
                    	<h3 class="box-title">Promotion Information</h3>
                    </div>
                    <!--FORM-->
                    <form id="backToListButton" method="get" action="<c:url value="/product/promotion/listPromotion" />"></form>
		              <div class="box-body">
		              		<input type="hidden" id="id" value="${promotion.promotionId}"/>
							<div class="col-sm-2">Name</div>
							<div class="col-sm-10">${promotion.promotionName}</div>
		            	<!--/.FORM-->
		              </div>
		              <!-- /.box-body -->
                </div>
             </div>
    	</div>
    	<tiles:insertAttribute name = "datatable1" />
		
		<tiles:insertAttribute name = "datatable2" />
		<div class="row">
	    	<div class="col-sm-2"></div>
			<div class="col-sm-10">
	               <button name="doneBtn" type="submit" class="btn btn-primary" form="backToListButton"><i class="fa fa-remove"></i> Done
	         	</button>
			</div>
		</div>
    </section>