<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Content Wrapper. Contains page content -->
  	<div class="row">
  		<div class="col-md-12">
  			<!--BOX-->
              <div class="box">
              	<!--BOX HEADER-->
                  <div class="box-header with-border">
                  	<h3 class="box-title">Selected Bonus</h3>
                  </div>
             	<div class="box-body">
					<div class="row">
				  		<div class="form-group">
							<div class="col-sm-2">Bonus Year</div>
							<div class="col-sm-2">Employee Name</div>
							<div class="col-sm-2">Bonus Amount</div>
							<div class="col-sm-2">Status</div>
						</div>
					</div>
					<c:forEach var="bonus" items="${bonusList}">
						<div class="row">
					  		<div class="form-group">
							    <div class="col-sm-2">${bonus.dateString}</div>
							    <div class="col-sm-2">${bonus.employeeVO.name}</div>
							    <div class="col-sm-2">${bonus.takehomeAmtString}</div>
							    <div class="col-sm-2">${bonus.status}</div>
							</div>
						</div>
					</c:forEach>		
				</div>
				<!-- /.box-body -->
      		</div>
      		<!-- /.BOX -->
            </div>
  	</div>