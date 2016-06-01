<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="../shared/taglib.jsp"%>

<html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>SVA APP demo</title>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="" name="author" />
   <meta name="MobileOptimized" content="320">
   
   <%@ include file="../shared/importCss.jsp"%>
   <!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
   <link href="<c:url value='/css/pages/heatmap.css'/>" rel="stylesheet" type="text/css"/>
   <link href="<c:url value='/plugins/jquery-chosen/chosen.css'/>" rel="stylesheet" type="text/css"/>
   <!-- END PAGE LEVEL PLUGIN STYLES -->
   <style type="text/css">
    .countInfo {
        width: 120px;
        height: 30px;
        background: rgba(255,255,255,0.6); 
        z-index: 10;
        filter: alpha(opacity = 30);
        opacity: 0.5;
        padding: 0 5px;
        padding-top: 5px;
        border-radius: 10px;
        color:#15A4FA;
        border:1px solid #D6D2D2;
        margin-bottom:10px;
    }
   </style>
   
   <link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<body class="page-header-fixed">
   
	<%@ include file="../shared/pageHeader.jsp"%>
   
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
      
		<%@ include file="../shared/sidebarMenu.jsp"%>
      
		<!-- BEGIN PAGE -->
		<div class="page-content">
                    
         <!-- BEGIN PAGE HEADER-->
		<div>
            <div>
               <!-- BEGIN PAGE TITLE & BREADCRUMB
               <h3 class="page-title">
					<spring:message code="scattermap_title" />
               </h3>-->
               <ul class="page-breadcrumb breadcrumb">
                  <li>
                     <i class="icon-home" style="background-image:none"></i>
                     <spring:message code="menu_customer_analyse" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_customer_analyse_scattermap" /></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
		</div>
		<!-- END PAGE HEADER-->
         
		<div class="clearfix"></div>
		<div>
		<form class="demoform">
         	<select id="marketSel" datatype="*"  nullmsg='<spring:message code="map_store_name" />'  data-placeholder="<spring:message code="heatmap_place" />"  style="width:30%" class="chosen-select" >
         		<option value=""></option>
         	</select>         	
         	<select id="floorSel" datatype="*"  nullmsg='<spring:message code="all_floor_choose" />' name="floorSelName" data-placeholder="<spring:message code="heatmap_floor" />"  style="width:30%"  class="chosen-select" >
         		<option value=""></option>
         	</select>
         	<input id="confirm" type="button" value="<spring:message code="common_confirm" />" style="vertical-align:top;padding: 0px 3px 0px 4px;height: 22px">
		</form>
		</div>
		<div id="mainContent" class="row-fluid hide">
			<div class="countInfo hide">
				<spring:message  code="heatmap_count">
				<label id="count" style="float: right;margin-right: 20px;margin-top: 2px;"></label>
				</spring:message>
			</div>
			<div id="divCon" style="text-align:center;height:600px;">
	            <div id="mapContainer">
			    	<div id="heatmap" class="heatmap"></div>
			    </div>
		    </div> 
         </div>
    
      </div>
      <!-- END PAGE -->
   </div>
   <!-- END CONTAINER -->
   <!-- BEGIN FOOTER -->
   <div class="footer">
      <div class="footer-inner">
   <%@ include file="../shared/pageFooter.jsp"%>
      </div>
      <div class="footer-tools">
         <span class="go-top">
         <i class="icon-angle-up"></i>
         </span>
      </div>
   </div>
   <!-- END FOOTER -->
   
   <%@ include file="../shared/importJs.jsp"%>
   <!-- BEGIN PAGE LEVEL SCRIPTS -->
   <script src="<c:url value='/plugins/jquery-chosen/chosen.jquery.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/plugins/heatmap.min.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/app.js'/>" type="text/javascript"></script>
   <script src="<c:url value='/js/scattermap.js'/>" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
   var i18n_info = '<spring:message code="map_info" />';
   var floorNo,origX,origY,bgImg,scale,imgScale,heatmap,timer;
   var pointVal = 20;
   var configObj = {
		   container: document.getElementById("heatmap"),
           maxOpacity: .6,
           radius: 5,
           blur: .10,
           backgroundColor: 'rgba(0, 0, 58, 0.1)'
   };
   
      jQuery(document).ready(function() { 
    	  $(".demoform").Validform({tiptype:3});
    	 App.init();
    	 Scattermap.initDropdown();
    	 Scattermap.bindClickEvent();
    	 //Heatmap.initHeatmap();
      });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>