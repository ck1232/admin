<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>  
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title><tiles:insertAttribute name="title" ignore="true" /></title>  
		<c:set var="context" value="/admin" scope="application" />
        <link type="text/css" href="<c:url context="${context}" value="/development/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet"/>        
		<link type="text/css" href="<c:url context="${context}" value="/development/themes/cruze/theme.css" />" rel="stylesheet" >
			<!-- Admin LTE -->
			<!-- Tell the browser to be responsive to screen width -->
		  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		  <!-- Bootstrap 3.3.6 -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/bootstrap/css/bootstrap.min.css"/>">
		  <!-- Font Awesome -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/font-awesome/css/font-awesome.min.css" />">
		  <!-- Ionicons -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/ionicons/css/ionicons.min.css" />">
		  <!-- Theme style -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/dist/css/AdminLTE.min.css"/>">
		  <!-- AdminLTE Skins. Choose a skin from the css/skins
		       folder instead of downloading all of them to reduce the load. -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/dist/css/skins/_all-skins.min.css"/>">
		  <!-- iCheck -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/iCheck/flat/blue.css"/>">
		  <!-- Morris chart -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/morris/morris.css"/>">
		  <!-- jvectormap -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/jvectormap/jquery-jvectormap-1.2.2.css"/>">
		  <!-- Date Picker -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/datepicker/datepicker3.css"/>">
		  <!-- Daterange picker -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/daterangepicker/daterangepicker.css"/>">
		   <!-- Bootstrap time Picker -->
 		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/timepicker/bootstrap-timepicker.min.css"/>">
		  <!-- bootstrap wysihtml5 - text editor -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"/>">
		  <!-- datatable -->
		  <%-- <link rel="stylesheet" href="<c:url context="${context}" value="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />"> --%>
		  
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/css/custom.css"/>">
		  <!-- typeahead -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/typeahead/typeahead.css"/>">
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/dropzone/dropzone.css" />">
		  <!-- tags -->
		  <link rel="stylesheet" href="<c:url context="${context}" value="/development/tags/bootstrap-tagsinput.css"/>">
		  <!-- REQUIRED JS SCRIPTS -->
		
		
		<!-- jQuery 2.2.3 -->
		<script src="<c:url context="${context}" value="/development/plugins/jQuery/jquery-2.2.3.min.js"/>"></script>
		
		<!--  JQuery-UI -->
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.js"></script>
		<!-- jQuery UI Touch Punch -->
		<script src="<c:url context="${context}" value="/development/jquery-ui-touch-punch/js/jquery.ui.touch-punch.js" />" ></script>
		<!-- Bootstrap 3.3.6 -->
		<script src="<c:url context="${context}" value="/development/bootstrap/js/bootstrap.min.js"/>"></script>
		<!-- AdminLTE App -->
		<script src="<c:url context="${context}" value="/development/dist/js/app.min.js"/>"></script>
		<!-- Bootstrap select -->
		<link rel="stylesheet" href="<c:url context="${context}" value="/development/bootstrap-select/css/bootstrap-select.min.css" />">
		<script src="<c:url context="${context}" value="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.js" />"></script>
		<!-- bootstrap datepicker -->
		<script src="<c:url context="${context}" value="/development/plugins/datepicker/bootstrap-datepicker.js"/>"></script>
		<!-- Select2 -->
		<script src="<c:url context="${context}" value="/development/plugins/select2/select2.full.min.js" />"></script>
		<!-- InputMask -->
		<script src="<c:url context="${context}" value="/development/plugins/input-mask/jquery.inputmask.js" />"></script>
		<script src="<c:url context="${context}" value="/development/plugins/input-mask/jquery.inputmask.date.extensions.js" />"></script>
		<script src="<c:url context="${context}" value="/development/plugins/input-mask/jquery.inputmask.extensions.js" />"></script>
		<!-- date-range-picker -->
		<script src="<c:url context="${context}" value="/development/moment/js/moment.min.js" />"></script>
		<script src="<c:url context="${context}" value="/development/plugins/daterangepicker/daterangepicker.js" />"></script>
		<!-- bootstrap datepicker -->
		<script src="<c:url context="${context}" value="/development/plugins/datepicker/bootstrap-datepicker.js" />"></script>
		<!-- bootstrap color picker -->
		<script src="<c:url context="${context}" value="/development/plugins/colorpicker/bootstrap-colorpicker.min.js" />"></script>
		<!-- CK Editor -->
		<script src="<c:url context="${context}" value="/development/ckeditor/ckeditor.js" />"></script>
		<!-- upload -->
        <script src="<c:url context="${context}" value="/development/dropzone/dropzone.js" />"></script>
        <!-- typeahead -->
        <script src="<c:url context="${context}" value="/development/typeahead/typeahead.bundle.js" />"></script>
        <!-- tags -->
        <script src="<c:url context="${context}" value="/development/tags/bootstrap-tagsinput.js" />"></script>
		<!-- REQUIRED JS SCRIPTS -->
		<!-- datatable plugin for date sorting -->
		
		<script>
			var token = $("meta[name='_csrf']").attr("content");
	    	var header = $("meta[name='_csrf_header']").attr("content");
	    	$(function(){
	    		$('#datatable1 thead th').each( function () {
	    	        var title = $(this).text();
	    	        if(title != "" && title != "Action"){
	    	        	$(this).append( '<br><input style="width:98%" type="text" placeholder="" />' );
	    	        }
	    	    } );

	    		$("#signoutId").on('click', function() {
					  //console.log("clear");
						//table.state.clear();
						//table.destroy();
	    				sessionStorage.clear();
					});
		    });
			function initTableSearch(){
				// Apply the search
			    table.columns().every( function () {
			        var that = this;
			 		console.log("init table search called");
			        $( 'input', this.header() ).on( 'keyup change', function () {
			            if ( that.search() !== this.value ) {
			                that.search( this.value ).draw();
			            }
			        } );
			    } );
			}
			
		</script>
</head>  
<body class="hold-transition skin-blue sidebar-mini" style="height: auto;">
<div class="wrapper">
	<tiles:insertAttribute name="header" /> 
	<tiles:insertAttribute name="menu" /> 
	<tiles:insertAttribute name="body" />  
	<tiles:insertAttribute name="footer" />  
</div>
</body>  
</html> 