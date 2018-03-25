<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 

<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
	  	<!-- Content Header (Page header) -->
	  	<section class="content-header">
		  	<h1>
		      	<tiles:getAsString name="heading" />
		      	<small><tiles:getAsString name="heading-description" /></small>
		    </h1>
	  	</section>
	
	  	<!-- Main content -->
	  	<section class="content">
	  		<tiles:insertAttribute name="message" />
			<tiles:insertAttribute name="content" />
	    	<!-- Your Page Content Here -->
	
	  	</section>
	  	<!-- /.content -->
  	</div>
<!-- /.content-wrapper -->