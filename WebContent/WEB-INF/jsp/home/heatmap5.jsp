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
		width: 200px;
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
	.legend-area {
		background: #DCFAFF; padding: 10px; outline: black solid 2px; right: 0px; bottom: 0px; line-height: 1em; position: absolute;
	}
	#min {
		float: left;
	}
	#max {
		float: right;
	}
	.tip {
		background: rgba(0, 0, 0, 0.8); padding: 5px; left: 0px; top: 0px; color: white; line-height: 18px; font-size: 14px; display: none; position: absolute;
	}
	.demo-wrapper {
		position: relative;
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
					<spring:message code="heatmap_title" />
               </h3>-->
               <ul class="page-breadcrumb breadcrumb">
                  <li>
                     <i class="icon-home" style="background-image:none"></i>
                     <spring:message code="menu_customer_analyse" />
                     <i class="icon-angle-right"></i>
                  </li>
                  <li><spring:message code="menu_customer_analyse_heatmap5" /></li>
               </ul>
               <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
         </div>
         <!-- END PAGE HEADER-->
         
         <div class="clearfix"></div>
         <div class="row-fluid">
         <form class="demoform">
         	<select id="marketSel" datatype="*"  nullmsg='<spring:message code="map_store_name" />'  data-placeholder="<spring:message code="heatmap_place" />"  style="width:30%" class="chosen-select" >
         		<option value=""></option>
         	</select>         	
         	<select id="floorSel" datatype="*"  nullmsg='<spring:message code="all_floor_choose" />' data-placeholder="<spring:message code="heatmap_floor" />"  style="width:30%"  class="chosen-select" >
         		<option value=""></option>
         	</select>
         	<select id="periodSel" data-placeholder="<spring:message code="heatmap_period" />"  style="width:30%"  class="chosen-select" >
         		<option value=""></option>
         	</select>
         	<input id="confirm" type="button" value="<spring:message code="common_confirm" />" style="vertical-align:top;padding: 0px 3px 0px 4px;height: 22px">
         	<div class="pull-right">
		        <select id="densitySel" data-placeholder="<spring:message code="heatmap_density" />"  class="chosen-select" >
		      		<option value=""></option>
		      		<option value="1">1</option>
		      		<option value="2">2</option>
		      		<option value="3">3</option>
		      		<option value="4">4</option>
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
		      	</select>
		      	<select id="radiusSel" data-placeholder="<spring:message code="heatmap_diffusance" />"  class="chosen-select" >
		      		<option value=""></option>
		      		<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="150">150</option>
					<option value="200">200</option>
		      	</select>
	      	</div>
	    </form> 	
		</div>
		<div id="mainContent" class="row-fluid hide">
         	<div class="countInfo hide">
				<spring:message  code="heatmap_count">
				<label id="count" style="float: right;margin-right: 10px;margin-top: 2px;"></label>
				</spring:message>
			</div>
			<div id="divCon" style="text-align:center;height:600px;">
	            <div id="mapContainer" class="demo-wrapper">
			    	<div id="heatmap" class="heatmap"></div>
					<div id="legend" class="legend-area hide">
				       <div  style="width: 100%;">
			                <div id="min" style="background-color: rgba(0,0,255,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
			                <div id ="minup" style="background-color: rgba(73,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
			                <div id ="max" style="background-color: rgba(251,255,0,1);width: 90px;float:left;text-align: center;padding: 4px 0"></div>
			                <div id="maxup" style="background-color: rgba(255,40,0,1);width: 100px;float:left;text-align: center;padding: 4px 0"></div>
                     </div>
					</div>
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
   <script src="<c:url value='/js/heatmap5.js'/>" type="text/javascript"></script>  
   <!-- END PAGE LEVEL SCRIPTS -->  

   <script type="text/javascript">
   var i18n_info = '<spring:message code="map_info" />';
   var i18n_heatmap_min = '<spring:message code="heatmap_picture_min" />';
   var i18n_heatmap_minup = '<spring:message code="heatmap_picture_minup" />';
   var i18n_heatmap_max = '<spring:message code="heatmap_picture_max" />';
   var i18n_heatmap_maxup = '<spring:message code="heatmap_picture_maxup" />';
   var i18n_heatmap_min1 = '<spring:message code="heatmap_heat_pictrue1" />';
   var i18n_heatmap_minup2 = '<spring:message code="heatmap_heat_pictrue2" />';
   var i18n_heatmap_max3 = '<spring:message code="heatmap_heat_pictrue3" />';
   var i18n_heatmap_maxup4 = '<spring:message code="heatmap_heat_pictrue4" />';
   var i18n_heatmap_maxup3 = '<spring:message code="heatmap_heat_pictrue3up" />';
   var floorNo,period,origX,origY,bgImg,scale,coordinate,imgHeight,imgWidth,imgScale,heatmap,timer;
   var pointVal = 1;
   var configObj = {
		   container: document.getElementById("heatmap"),
           maxOpacity: .6,
           radius: 20,
           blur: .90,
           backgroundColor: 'rgba(0, 0, 58, 0.1)'
   };
   
	var legendCanvas = document.createElement('canvas');
	legendCanvas.width = 100;
	legendCanvas.height = 10;
	var min = document.querySelector('#min');
	var max = document.querySelector('#max');
	var gradientImg = document.querySelector('#gradient');

	var legendCtx = legendCanvas.getContext('2d');
	var gradientCfg = {};

	var demoWrapper = document.querySelector('.demo-wrapper');
   
      jQuery(document).ready(function() {  
          var validForm = $(".demoform").Validform({tiptype:3});
    	 App.init();
    	 Heatmap5.initDropdown();
    	 Heatmap5.bindClickEvent();
    	 //Heatmap.initHeatmap();
      });
   </script>
   <!-- END JAVASCRIPTS -->
</body>
</html>